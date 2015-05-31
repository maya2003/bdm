/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

#include <unistd.h>
#include <pthread.h>
#include <stdio.h>

#include "protocol.h"

// TODO: manage partial writes
// TODO: manage reading full frame at once
// TODO: make thread cancelable
// TODO: define error management when hardware failure...

static void *Bdm_receiveThread(void *param);

int fd;


/*
 */
bool Bdm_receiveStart(void)
{
  int result;
  pthread_t thread;

  result = pthread_create(&thread, NULL, Bdm_receiveThread, NULL);

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
static void *Bdm_receiveThread(void *param __attribute__ ((unused)))
{
  ssize_t result;
  u8 data;

  while(1)
  {
    result = read(fd, &data, sizeof(data));

    if(sizeof(data) == result)
    {
      BdmTransparency_octetReceived(NULL, data); //!!
    }
    else
    {
      perror("read()");
    }
  }

  return NULL;
}

/*
 */
bool Bdm_write(const u8 *data, size_t size)
{
  ssize_t result;

  result = write(fd, data, size);

  if((ssize_t)size == result)
  {
    return true;
  }
  else
  {
    return false;
  }
}

