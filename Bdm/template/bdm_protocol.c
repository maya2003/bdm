/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

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
bool Bdm_protocolSendFrame(Bdm_ProtocolContext *context, const u8 *data, u8 size)
{
// TODO: swap
  context->frameContext.header.protocolSignature = context->frameContext.configuration->protocolSignature;
  context->frameContext.header.command           = 5;
  context->frameContext.header.size              = size;

  /* compute CRC */
  context->frameContext.footer.crc = Bdm_crc16_init();
  context->frameContext.footer.crc = Bdm_crc16_update(context->frameContext.footer.crc, context->frameContext.header.data, sizeof(Bdm_FrameHeader));
  context->frameContext.footer.crc = Bdm_crc16_update(context->frameContext.footer.crc, data, size);
  context->frameContext.footer.crc = Bdm_crc16_update(context->frameContext.footer.crc, context->frameContext.footer.data, sizeof(Bdm_FrameFooter) - sizeof(Bdm_crc16_t));

//TODO PB PB PB concurency between Tx and Rx on frame content!
  Bdm_transparencySendStx(context);
  Bdm_transparencySendData(context, context->frameContext.header.data, sizeof(Bdm_FrameHeader));
  Bdm_transparencySendData(context, data, size);
  Bdm_transparencySendData(context, context->frameContext.footer.data, sizeof(Bdm_FrameFooter));
  Bdm_transparencySendEtx(context);

  return true;
}

/*
 */
bool Bdm_protocolStartOfFrameReceived(Bdm_ProtocolContext *context)
{
  if(BDM_FS_WAIT_START == context->frameContext.state)
  {
    context->frameContext.size  = 0;
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

    /* check CRC */
    crc = Bdm_crc16_init();
    crc = Bdm_crc16_update(crc, context->frameContext.header.data, sizeof(Bdm_FrameHeader));
    crc = Bdm_crc16_update(crc, context->frameContext.data,        context->frameContext.header.size);
    crc = Bdm_crc16_update(crc, context->frameContext.footer.data, sizeof(Bdm_FrameFooter) - sizeof(Bdm_crc16_t));

    printf("\n\nComputed CRC: %04X\n", crc);
    printf("Received CRC: %04X\n\n", context->frameContext.footer.crc);

    /* check command */

    /* check command/size */

    /* Call callback */

    puts("memory:");
    printf("  header:"); for(i = 0; i < sizeof(Bdm_FrameHeader); i++)           { printf("%02X", context->frameContext.header.data[i]); if(i+1 < sizeof(Bdm_FrameHeader))           printf(":"); } puts("");
    printf("  data:");   for(i = 0; i < context->frameContext.header.size; i++) { printf("%02X", context->frameContext.data[i]);        if(i+1 < context->frameContext.header.size) printf(":"); } puts("");
    printf("  footer:"); for(i = 0; i < sizeof(Bdm_FrameFooter); i++)           { printf("%02X", context->frameContext.footer.data[i]); if(i+1 < sizeof(Bdm_FrameFooter))           printf(":"); } puts("");

    context->frameContext.state = BDM_FS_WAIT_START;
  }

  return true;
}

/*
 */
bool Bdm_protocolOctetReceived(Bdm_ProtocolContext *context, u8 octet)
{
  switch(context->frameContext.state)
  {
    case BDM_FS_WAIT_HEADER:
    {
      context->frameContext.header.data[context->frameContext.size++] = octet;

      if(sizeof(Bdm_FrameHeader) == context->frameContext.size)
      {
        context->frameContext.size  = 0;
        context->frameContext.state = BDM_FS_WAIT_DATA;
      }

      break;
    }

    case BDM_FS_WAIT_DATA:
    {
      context->frameContext.data[context->frameContext.size++] = octet;

      if(context->frameContext.header.size == context->frameContext.size)
      {
        context->frameContext.size  = 0;
        context->frameContext.state = BDM_FS_WAIT_FOOTER;
      }

      break;
    }

    case BDM_FS_WAIT_FOOTER:
    {
      context->frameContext.footer.data[context->frameContext.size++] = octet;

      if(sizeof(Bdm_FrameFooter) == context->frameContext.size)
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

