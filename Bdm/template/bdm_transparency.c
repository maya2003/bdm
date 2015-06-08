/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

#include <stdio.h>

#include "bdm.h"

/*
 */
bool Bdm_transparencyInit(Bdm_ProtocolContext *context)
{
  context->frameContext.transparencyContext.state = BDM_TS_WAIT_STX;
  return true;
}

/*
 */
bool Bdm_transparencySendStx(const Bdm_ProtocolContext *context)
{
  Bdm_startFrame((Bdm_ProtocolContext *)context, context->frameContext.transparencyContext.configuration->frameStart);
  return true;
}

/*
 */
bool Bdm_transparencySendEtx(const Bdm_ProtocolContext *context)
{
  Bdm_sendFrame((Bdm_ProtocolContext *)context, context->frameContext.transparencyContext.configuration->frameEnd);
  return true;
}

/*
 */
bool Bdm_transparencySendData(const Bdm_ProtocolContext *context, const u8 *data, size_t size)
{
  size_t i;

  for(i = 0; i < size; i++)
  {
    if(context->frameContext.transparencyContext.configuration->frameStart == data[i])
    {
      Bdm_appendData((Bdm_ProtocolContext *)context, context->frameContext.transparencyContext.configuration->escapeCharacter);
      Bdm_appendData((Bdm_ProtocolContext *)context, context->frameContext.transparencyContext.configuration->frameStart);
    }
    else if(context->frameContext.transparencyContext.configuration->frameEnd == data[i])
    {
      Bdm_appendData((Bdm_ProtocolContext *)context, context->frameContext.transparencyContext.configuration->escapeCharacter);
      Bdm_appendData((Bdm_ProtocolContext *)context, context->frameContext.transparencyContext.configuration->frameEnd);
    }
    else if(context->frameContext.transparencyContext.configuration->escapeCharacter == data[i])
    {
      Bdm_appendData((Bdm_ProtocolContext *)context, context->frameContext.transparencyContext.configuration->escapeCharacter);
      Bdm_appendData((Bdm_ProtocolContext *)context, context->frameContext.transparencyContext.configuration->escapeCharacter);
    }
    else
    {
      Bdm_appendData((Bdm_ProtocolContext *)context, data[i]);
    }
  }

  return true;
}

/*
 */
bool Bdm_transparencyOctetReceived(Bdm_ProtocolContext *context, u8 octet)
{
  //Bdm_dump(&octet, sizeof(octet));

  switch(context->frameContext.transparencyContext.state)
  {
    case BDM_TS_WAIT_STX:
    {
      if(context->frameContext.transparencyContext.configuration->frameStart == octet)
      {
        /* STX received */
        Bdm_protocolStartOfFrameReceived(context);
        context->frameContext.transparencyContext.state = BDM_TS_WAIT_DATA;
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
      if(context->frameContext.transparencyContext.configuration->frameStart == octet)
      {
        /* Sequence forbidden! Restart current frame. */
        Bdm_protocolStartOfFrameReceived(context);
        context->frameContext.transparencyContext.state = BDM_TS_WAIT_DATA;
      }
      else if(context->frameContext.transparencyContext.configuration->frameEnd == octet)
      {
        /* End of frame found. Wait next frame */
        Bdm_protocolEndOfFrameReceived(context);
        context->frameContext.transparencyContext.state = BDM_TS_WAIT_STX;
      }
      else if(context->frameContext.transparencyContext.configuration->escapeCharacter == octet)
      {
        /* Start of escape sequence */
        context->frameContext.transparencyContext.state = BDM_TS_WAIT_ESCAPED_DATA;
      }
      else
      {
        /* Data received */
        Bdm_protocolOctetReceived(context, octet);
      }

      break;
    }

    case BDM_TS_WAIT_ESCAPED_DATA:
    {
      if(context->frameContext.transparencyContext.configuration->frameStart == octet)
      {
        /* Escaped data */
        Bdm_protocolOctetReceived(context, octet);
        context->frameContext.transparencyContext.state = BDM_TS_WAIT_DATA;
      }
      else if(context->frameContext.transparencyContext.configuration->frameEnd == octet)
      {
        /* Escaped data */
        Bdm_protocolOctetReceived(context, octet);
        context->frameContext.transparencyContext.state = BDM_TS_WAIT_DATA;
      }
      else if(context->frameContext.transparencyContext.configuration->escapeCharacter == octet)
      {
        /* Escaped data */
        Bdm_protocolOctetReceived(context, octet);
        context->frameContext.transparencyContext.state = BDM_TS_WAIT_DATA;
      }
      else
      {
        /* Invalid escape sequence! */
        puts("Invalid escape sequence!");
        Bdm_protocolInit(context);
        context->frameContext.transparencyContext.state = BDM_TS_WAIT_STX;
      }

      break;
    }

    default:
    {
      /* Internal error! */
      puts("Internal error!");
      Bdm_protocolInit(context);
      context->frameContext.transparencyContext.state = BDM_TS_WAIT_STX;
      break;
    }
  }

  return true;
}

