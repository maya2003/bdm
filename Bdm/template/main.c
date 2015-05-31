/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

#include <stdio.h>

#include "protocol.h"

/*
 */
int main(void)
{
  Bdm_FrameContext context;
  u8 frame[] = {BDM_STX, BDM_STX, BDM_STX, 0, 1, 2, 3, 4, BDM_DLE, 1, BDM_DLE, BDM_STX, BDM_DLE, BDM_ETX, BDM_DLE, BDM_DLE, 5, 7, BDM_DLE};

  BdmProtocol_initContext(&context);

  puts("memory:");
  Bdm_dumper(frame, sizeof(frame));
  puts("\n");

  puts("network:");
  BdmProtocol_sendFrame(&context, frame, sizeof(frame));

  return 0;
}

/*
 */
void Bdm_dumper(const u8 *data, u8 size)
{
  int i;

  for(i = 0; i < size; i++)
  {
    if(BDM_STX == data[i])
    {
      printf("<STX>");
    }
    else if(BDM_ETX == data[i])
    {
      printf("<ETX>");
    }
    else if(BDM_DLE == data[i])
    {
      printf("<DLE>");
    }
    else if(data[i] < ' ')
    {
      printf("<%02X>", data[i]);
    }
    else
    {
      printf("%c", data[i]);
    }
  }
}

