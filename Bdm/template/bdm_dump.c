/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

#include <stdio.h>

#include "bdm.h"

/*
 */
void Bdm_dumpMemory(const u8 *data, size_t size)
{
  size_t i;

  if(0 == size)
  {
    puts("(empty)");
  }
  else
  {
    for(i = 0; i < size; i++)
    {
      if(BDM_STX == data[i])
      {
        printf("<STX-02>");
      }
      else if(BDM_ETX == data[i])
      {
        printf("<ETX-03>");
      }
      else if(BDM_DLE == data[i])
      {
        printf("<DLE-10>");
      }
      else if((data[i] < BDM_SPACE) || (data[i] >= BDM_DEL))
      {
        printf("<%02X>", data[i]);
      }
      else
      {
        printf("<%02X>", data[i]);
      }
    }

    puts("");
  }
}

/*
 */
void Bdm_dumpFrame(const Bdm_ProtocolContext *context)
{
  size_t i;

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
}

