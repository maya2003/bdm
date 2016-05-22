/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

#include "bdm_serial.h"

#include <stdio.h>
#include <unistd.h>


/*
 */
int main(void)
{
  static const Bdm_SerialConfiguration serialConfiguration =
  {
    .speed       = 9600,
    .dataBits    = BDM_8_DATA_BITS,
    .parity      = BDM_PARITY_EVEN,
    .stopBits    = BDM_1_STOP_BIT,
    .flowControl = BDM_FLOW_CONTROL_NONE
  };
  int fd;
  char nl[2];
  char buff[5] = { 0 };

  fd = Bdm_serialOpen("/dev/ttyUSB0", &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);

  printf("fd: %d\n", fd);

  write(fd, "toto\n", 5);

  sleep(1);

  read(fd, buff, 5);

  puts(buff);

  fgets(nl, sizeof(nl), stdin);

  puts("The end.");

  return 0;
}

