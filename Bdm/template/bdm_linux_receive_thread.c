/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

// TODO: manage partial writes
// TODO: manage reading full frame at once
// TODO: make thread cancelable
// TODO TODO: define error management when hardware failure...

#include <pthread.h>
#include <unistd.h>
#include <stdio.h>

#include "bdm.h"

static void *Bdm_linuxReceiveThread(void *context_);

/*
 */
bool Bdm_linuxReceiveThreadStart(Bdm_ProtocolContext *context)
{
  int result;
  pthread_t thread;

  result = pthread_create(&thread, NULL, Bdm_linuxReceiveThread, context);

  if(0 == result)
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
bool Bdm_write(const Bdm_ProtocolContext *context, const u8 *data, size_t size)
{
  ssize_t result;

  result = write(context->fd, data, size);

  if((ssize_t)size == result)
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
static void *Bdm_linuxReceiveThread(void *context_)
{
  Bdm_ProtocolContext *context = (Bdm_ProtocolContext *)context_;
  ssize_t result;
  u8 data;

  while(1)
  {
    result = read(context->fd, &data, sizeof(u8));

    if(sizeof(u8) == result)
    {
      Bdm_transparencyOctetReceived(context, data);
    }
    else
    {
      perror("read()");
    }
  }

  return NULL;
}

