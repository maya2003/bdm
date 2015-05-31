/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

#include <stdio.h>

#include "protocol.h"

Bdm_TransparencyContext context2 =
{
  state:      BDM_TS_WAIT_STX,
  frameStart: BDM_STX,
  frameEnd:   BDM_ETX,
  escape:     BDM_DLE
};

Bdm_FrameContext context3;

/*
 */
bool BdmTransparency_initContext(Bdm_TransparencyContext *context)
{
  context->state = BDM_TS_WAIT_STX;
  return true;
}

/*
 */
bool BdmTransparency_sendStx(const Bdm_TransparencyContext *context)
{
  BdmTransparency_octetReceived(&context2, context->frameStart);
  return true;
}

/*
 */
bool BdmTransparency_sendEtx(const Bdm_TransparencyContext *context)
{
  BdmTransparency_octetReceived(&context2, context->frameEnd);
  return true;
}


/*
 */
bool BdmTransparency_sendData(const Bdm_TransparencyContext *context, const u8 *data, u16 size)
{
  int i;

  for(i = 0; i < size; i++)
  {
    if(context->frameStart == data[i])
    {
      BdmTransparency_octetReceived(&context2, context->escape);
      BdmTransparency_octetReceived(&context2, context->frameStart);
    }
    else if(context->frameEnd == data[i])
    {
      BdmTransparency_octetReceived(&context2, context->escape);
      BdmTransparency_octetReceived(&context2, context->frameEnd);
    }
    else if(context->escape == data[i])
    {
      BdmTransparency_octetReceived(&context2, context->escape);
      BdmTransparency_octetReceived(&context2, context->escape);
    }
    else
    {
      BdmTransparency_octetReceived(&context2, data[i]);
    }
  }

  return true;
}

/*
 */
bool BdmTransparency_octetReceived(Bdm_TransparencyContext *context, u8 octet)
{
  Bdm_dumper(&octet, sizeof(octet));

  switch(context->state)
  {
    case BDM_TS_WAIT_STX:
    {
      if(context->frameStart == octet)
      {
        /* STX received */
        BdmProtocol_startOfFrameReceived(&context3);
        context->state = BDM_TS_WAIT_DATA;
      }
      else
      {
        /* Discard junk data before start of frame. */
        printf("(%02X)", octet);
      }

      break;
    }

    case BDM_TS_WAIT_DATA:
    {
      if(context->frameStart == octet)
      {
        /* Sequence forbidden! Restart current frame. */
        BdmProtocol_startOfFrameReceived(&context3);
        context->state = BDM_TS_WAIT_DATA;
      }
      else if(context->frameEnd == octet)
      {
        /* End of frame found. Wait next frame */
        BdmProtocol_endOfFrameReceived(&context3);
        context->state = BDM_TS_WAIT_STX;
      }
      else if(context->escape == octet)
      {
        /* Start of escape sequence */
        context->state = BDM_TS_WAIT_ESCAPED_DATA;
      }
      else
      {
        /* Data received */
        BdmProtocol_octetReceived(&context3, octet);
      }

      break;
    }

    case BDM_TS_WAIT_ESCAPED_DATA:
    {
      if(context->frameStart == octet)
      {
        /* Escaped data */
        BdmProtocol_octetReceived(&context3, octet);
        context->state = BDM_TS_WAIT_DATA;
      }
      else if(context->frameEnd == octet)
      {
        /* Escaped data */
        BdmProtocol_octetReceived(&context3, octet);
        context->state = BDM_TS_WAIT_DATA;
      }
      else if(context->escape == octet)
      {
        /* Escaped data */
        BdmProtocol_octetReceived(&context3, octet);
        context->state = BDM_TS_WAIT_DATA;
      }
      else
      {
        /* Invalid escape sequence! */
        puts("Invalid escape sequence!");
        BdmProtocol_initContext(&context3);
        context->state = BDM_TS_WAIT_STX;
      }

      break;
    }

    default:
    {
      /* Internal error! */
      puts("Internal error!");
      BdmProtocol_initContext(&context3);
      context->state = BDM_TS_WAIT_STX;
      break;
    }
  }

  return true;
}

