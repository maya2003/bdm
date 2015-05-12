/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

#include <stdbool.h>
#include <unistd.h>
#include <stdio.h>

extern bool Bdm_serialOpen(int *fd);

/*
 */
int main(void)
{
  int fd;
  char buffer[4] = "AAAA";

  Bdm_serialOpen(&fd);

  printf("fd: %d\n", fd);
  write(fd, "toto", 4);
  sleep(1);
  read(fd, buffer, 4);

  printf("\"%4s\"", buffer);

  return 0;
}

