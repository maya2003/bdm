/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

#include <stdio.h>

#include "protocol.h"

Bdm_TransparencyContext transparencyContext =
{
  state:      BDM_TS_WAIT_STX,
  frameStart: BDM_STX,
  frameEnd:   BDM_ETX,
  escape:     BDM_DLE
};


/*
 */
bool BdmProtocol_initContext(Bdm_FrameContext *context)
{
  context->state  = BDM_FS_WAIT_START;

  return true;
}

/*
 */
bool BdmProtocol_sendFrame(Bdm_FrameContext *context, const u8 *data, u8 size)
{
  context->header.protocolSignature = 0x4621;
  context->header.command           = 5;
  context->header.size              = size;

  context->footer.crc               = 0x0908;

  BdmTransparency_sendStx(&transparencyContext);
  BdmTransparency_sendData(&transparencyContext, context->header.data, sizeof(Bdm_FrameHeader));
  BdmTransparency_sendData(&transparencyContext, data, size);
  BdmTransparency_sendData(&transparencyContext, context->footer.data, sizeof(Bdm_FrameFooter));
  BdmTransparency_sendEtx(&transparencyContext);

  return true;
}

/*
 */
bool BdmProtocol_startOfFrameReceived(Bdm_FrameContext *context)
{
  if(BDM_FS_WAIT_START == context->state)
  {
    context->length = 0;
    context->state = BDM_FS_WAIT_HEADER;
  }

  return true;
}

/*
 */
bool BdmProtocol_endOfFrameReceived(Bdm_FrameContext *context)
{
  unsigned i;

  if(BDM_FS_WAIT_END == context->state)
  {
    /* end of frame */

    /* check protocolSignature */

    /* check CRC */

    /* check command */

    /* check command/size */

    /* Call callback */

    puts("\n\nmemory:");
    printf("  header:"); for(i = 0; i < sizeof(Bdm_FrameHeader); i++) { printf("%02X", context->header.data[i]); if(i+1 < sizeof(Bdm_FrameHeader)) printf(":"); } puts("");
    printf("  data:");   for(i = 0; i < context->header.size; i++)    { printf("%02X", context->data[i]);        if(i+1 < context->header.size   ) printf(":"); } puts("");
    printf("  footer:"); for(i = 0; i < sizeof(Bdm_FrameFooter); i++) { printf("%02X", context->footer.data[i]); if(i+1 < sizeof(Bdm_FrameFooter)) printf(":"); } puts("");

    context->state = BDM_FS_WAIT_START;
  }

  return true;
}

/*
 */
bool BdmProtocol_octetReceived(Bdm_FrameContext *context, u8 octet)
{
  switch(context->state)
  {
    case BDM_FS_WAIT_HEADER:
    {
      context->header.data[context->length++] = octet;

      if(sizeof(Bdm_FrameHeader) == context->length)
      {
        context->length = 0;
        context->state  = BDM_FS_WAIT_DATA;
      }

      break;
    }

    case BDM_FS_WAIT_DATA:
    {
      context->data[context->length++] = octet;

      if(context->header.size == context->length)
      {
        context->length = 0;
        context->state  = BDM_FS_WAIT_FOOTER;
      }

      break;
    }

    case BDM_FS_WAIT_FOOTER:
    {
      context->footer.data[context->length++] = octet;

      if(sizeof(Bdm_FrameFooter) == context->length)
      {
        context->state = BDM_FS_WAIT_END;
      }
      break;
    }

    case BDM_FS_WAIT_END:
    {
      puts("Error!");
      context->state = BDM_FS_WAIT_START;
      break;
    }

    default:
    {
      puts("Error!");
      context->state = BDM_FS_WAIT_START;
      break;
    }
  }

  return true;
}

