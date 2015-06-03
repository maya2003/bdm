/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

/* - build physical frame and send them at once */
/* - receive physical frames at once */

// TODO: manage errors
// TODO: write full frames
// TODO: manage partial writes
// TODO: declare inline
// TODO: avoid passing the start and end octets as parameter

#include <unistd.h>

#include "bdm.h"

/*
 */
bool Bdm_startFrame(const Bdm_ProtocolContext *context, u8 data)
{
  ssize_t result;

  result = write(context->fd, &data, sizeof(data));

  if(sizeof(data) == result)
  {
    return true;
  }
  else
  {
    return false;
  }
}

/*
 */
bool Bdm_appendData(const Bdm_ProtocolContext *context, u8 data)
{
  ssize_t result;

  result = write(context->fd, &data, sizeof(data));

  if(sizeof(data) == result)
  {
    return true;
  }
  else
  {
    return false;
  }
}

/*
 */
bool Bdm_sendFrame(const Bdm_ProtocolContext *context, u8 data)
{
  ssize_t result;

  result = write(context->fd, &data, sizeof(data));

  if(sizeof(data) == result)
  {
    return true;
  }
  else
  {
    return false;
  }
}

