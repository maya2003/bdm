/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

#include "bdm.h"

/*
 */
bool Bdm_getFrameSize(size_t *size, u8 id)
{
  switch(id)
  {
    case 1:
    {
      *size = 20;
      break;
    }

    case 2:
    {
      *size = 0;
      break;
    }

    default:
    {
      return false;
    }
  }

  return true;
}

