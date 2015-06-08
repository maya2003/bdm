/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

// TODO Manage case when we send less or more data than frame size!!

#include <stdio.h>

#include "bdm.h"
#include "bdm_crc.h"

/*
 */
bool Bdm_protocolInit(Bdm_ProtocolContext *context)
{
  context->frameContext.state  = BDM_FS_WAIT_START;

  return true;
}

/*
 */
bool Bdm_protocolSendFrame(Bdm_ProtocolContext *context, u8 id, const u8 *data, size_t size)
{
  bool result;
  size_t frameSize;

  result = Bdm_getFrameSize(&frameSize, id);
  if(!result)
  {
    return false;
  }

  if(size != frameSize)
  {
    return false;
  }

// TODO: swap
  context->frameContext.txHeader.protocolSignature = context->frameContext.configuration->protocolSignature;
  context->frameContext.txHeader.id                = id;

  /* compute CRC */
  context->frameContext.txFooter.crc = Bdm_crc16_init();
  context->frameContext.txFooter.crc = Bdm_crc16_update(context->frameContext.txFooter.crc, context->frameContext.txHeader.data, sizeof(Bdm_FrameHeader));
  context->frameContext.txFooter.crc = Bdm_crc16_update(context->frameContext.txFooter.crc, data, size);
  context->frameContext.txFooter.crc = Bdm_crc16_update(context->frameContext.txFooter.crc, context->frameContext.txFooter.data, sizeof(Bdm_FrameFooter) - sizeof(Bdm_crc16_t));

  Bdm_transparencySendStx(context);
  Bdm_transparencySendData(context, context->frameContext.txHeader.data, sizeof(Bdm_FrameHeader));
  Bdm_transparencySendData(context, data, size);
  Bdm_transparencySendData(context, context->frameContext.txFooter.data, sizeof(Bdm_FrameFooter));
  Bdm_transparencySendEtx(context);

  return true;
}

/*
 */
bool Bdm_protocolStartOfFrameReceived(Bdm_ProtocolContext *context)
{
  if(BDM_FS_WAIT_START == context->frameContext.state)
  {
    context->frameContext.fieldSize = 0;
    context->frameContext.state = BDM_FS_WAIT_HEADER;
  }

  return true;
}

/*
 */
bool Bdm_protocolEndOfFrameReceived(Bdm_ProtocolContext *context)
{
  Bdm_crc16_t crc;
  unsigned i;

  if(BDM_FS_WAIT_END == context->frameContext.state)
  {
    /* end of frame */

    /* check protocolSignature */
    if(context->frameContext.rxHeader.protocolSignature != context->frameContext.configuration->protocolSignature)
    {
      printf("Wrong protocol signature received! Expected: %04X - Received: %04X\n", context->frameContext.configuration->protocolSignature, context->frameContext.rxHeader.protocolSignature);
      return false;
    }

    /* check CRC */
    crc = Bdm_crc16_init();
    crc = Bdm_crc16_update(crc, context->frameContext.rxHeader.data, sizeof(Bdm_FrameHeader));
    crc = Bdm_crc16_update(crc, context->frameContext.data,        context->frameContext.dataSize);
    crc = Bdm_crc16_update(crc, context->frameContext.rxFooter.data, sizeof(Bdm_FrameFooter) - sizeof(Bdm_crc16_t));

    if(context->frameContext.rxFooter.crc != crc)
    {
      printf("Wrong CRC received! Expected: %04X - Computed: %04X\n", context->frameContext.rxFooter.crc, crc);
      return false;
    }

    /* check command */

    /* check command/size */

    /* dump frame */
    puts("rx:");

    printf("  header: ");
    for(i = 0; i < sizeof(Bdm_FrameHeader); i++)
    {
      printf("%02X", context->frameContext.rxHeader.data[i]);

      if(i+1 < sizeof(Bdm_FrameHeader))
      {
        printf(":");
      }
    }
    puts("");

    printf("  data:   ");
    if(context->frameContext.dataSize)
    {
      for(i = 0; i < context->frameContext.dataSize; i++)
      {
        printf("%02X", context->frameContext.data[i]);

        if(i+1 < context->frameContext.dataSize)
        {
          printf(":");
        }
      }
      printf(" (%ld)\n", context->frameContext.dataSize);
    }
    else
    {
      puts("(none)");
    }

    printf("  footer: ");
    for(i = 0; i < sizeof(Bdm_FrameFooter); i++)
    {
      printf("%02X", context->frameContext.rxFooter.data[i]);

      if(i+1 < sizeof(Bdm_FrameFooter))
      {
        printf(":");
      }
    }
    puts("\n");

    context->frameContext.state = BDM_FS_WAIT_START;
  }

  return true;
}

/*
 */
bool Bdm_protocolOctetReceived(Bdm_ProtocolContext *context, u8 octet)
{
  bool result;

  switch(context->frameContext.state)
  {
    case BDM_FS_WAIT_HEADER:
    {
      context->frameContext.rxHeader.data[context->frameContext.fieldSize++] = octet;

      if(sizeof(Bdm_FrameHeader) == context->frameContext.fieldSize)
      {
        result = Bdm_getFrameSize(&context->frameContext.dataSize, context->frameContext.rxHeader.id);
        if(!result)
        {
          return false;
        }

        if(context->frameContext.dataSize)
        {
          context->frameContext.fieldSize = 0;
          context->frameContext.state = BDM_FS_WAIT_DATA;
        }
        else
        {
          context->frameContext.fieldSize = 0;
          context->frameContext.state = BDM_FS_WAIT_FOOTER;
        }
      }

      break;
    }

    case BDM_FS_WAIT_DATA:
    {
      context->frameContext.data[context->frameContext.fieldSize++] = octet;

      if(context->frameContext.fieldSize == context->frameContext.dataSize)
      {
        context->frameContext.fieldSize = 0;
        context->frameContext.state = BDM_FS_WAIT_FOOTER;
      }

      break;
    }

    case BDM_FS_WAIT_FOOTER:
    {
      context->frameContext.rxFooter.data[context->frameContext.fieldSize++] = octet;

      if(sizeof(Bdm_FrameFooter) == context->frameContext.fieldSize)
      {
        context->frameContext.state = BDM_FS_WAIT_END;
      }
      break;
    }

    case BDM_FS_WAIT_END:
    {
      puts("Error!");
      context->frameContext.state = BDM_FS_WAIT_START;
      break;
    }

    default:
    {
      puts("Error!");
      context->frameContext.state = BDM_FS_WAIT_START;
      break;
    }
  }

  return true;
}

