/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

#include "bdm_serial.h"

#include <stdio.h>
#include <unistd.h>

#define BDM_TEST_SERIAL_DEVICE "/dev/ttyUSB0"

/*
 */
int main(void)
{
  static Bdm_SerialConfiguration serialConfiguration =
  {
    .speed       = 9600,
    .dataBits    = BDM_8_DATA_BITS,
    .parity      = BDM_PARITY_EVEN,
    .stopBits    = BDM_1_STOP_BIT,
    .flowControl = BDM_FLOW_CONTROL_NONE
  };
  int fd;
  char nl[2];
#if 0
  char buff[5] = { 0 };

  fd = Bdm_serialOpen("/dev/ttyUSB0", &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);

  printf("fd: %d\n", fd);

  write(fd, "toto\n", 5);

  sleep(1);

  read(fd, buff, 5);

  puts(buff);

  fgets(nl, sizeof(nl), stdin);

  puts("The end.");
#endif

  u32                   speed;
  Bdm_SerialDataBits    dataBits;
  Bdm_SerialParity      parity;
  Bdm_SerialStopBits    stopBits;


  /* default configuration */
  speed = 9600;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);


  /* standard Linux speeds */

  /* B0 */
  speed = 0;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B50 */
  speed = 50;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B75 */
  speed = 75;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B110 */
  speed = 110;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B134 */
  speed = 134;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B150 */
  speed = 150;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B200 */
  speed = 200;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B300 */
  speed = 300;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B600 */
  speed = 600;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B1200 */
  speed = 1200;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B1800 */
  speed = 1800;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B2400 */
  speed = 2400;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B4800 */
  speed = 4800;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B9600 */
  speed = 9600;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B19200 */
  speed = 19200;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B38400 */
  speed = 38400;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B57600 */
  speed = 57600;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B115200 */
  speed = 115200;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* B230400 */
  speed = 230400;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* 3872 (custom speed) */
  speed = 3872;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);


  /* default configuration */
  speed = 9600;
  printf(".speed: %u\n", speed);
  serialConfiguration.speed = speed;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);


  /* BDM_5_DATA_BITS */
  dataBits = BDM_5_DATA_BITS;
  printf(".dataBits: %u\n", dataBits);
  serialConfiguration.dataBits = dataBits;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* BDM_6_DATA_BITS */
  dataBits = BDM_6_DATA_BITS;
  printf(".dataBits: %u\n", dataBits);
  serialConfiguration.dataBits = dataBits;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* BDM_7_DATA_BITS */
  dataBits = BDM_7_DATA_BITS;
  printf(".dataBits: %u\n", dataBits);
  serialConfiguration.dataBits = dataBits;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* BDM_8_DATA_BITS */
  dataBits = BDM_8_DATA_BITS;
  printf(".dataBits: %u\n", dataBits);
  serialConfiguration.dataBits = dataBits;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* BDM_9_DATA_BITS */
  dataBits = BDM_9_DATA_BITS;
  printf(".dataBits: %u\n", dataBits);
  serialConfiguration.dataBits = dataBits;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* 0 */
  dataBits = 0;
  printf(".dataBits: %u\n", dataBits);
  serialConfiguration.dataBits = dataBits;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);


  /* default configuration */
  dataBits = BDM_8_DATA_BITS;
  printf(".dataBits: %u\n", dataBits);
  serialConfiguration.dataBits = dataBits;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);


  /* none */
  parity = BDM_PARITY_NONE;
  printf(".parity: %u\n", parity);
  serialConfiguration.parity = parity;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* odd */
  parity = BDM_PARITY_ODD;
  printf(".parity: %u\n", parity);
  serialConfiguration.parity = parity;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* even */
  parity = BDM_PARITY_EVEN;
  printf(".parity: %u\n", parity);
  serialConfiguration.parity = parity;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* mark */
  parity = BDM_PARITY_MARK;
  printf(".parity: %u\n", parity);
  serialConfiguration.parity = parity;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* space */
  parity = BDM_PARITY_SPACE;
  printf(".parity: %u\n", parity);
  serialConfiguration.parity = parity;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);


  /* default configuration */
  parity = BDM_PARITY_EVEN;
  printf(".parity: %u\n", parity);
  serialConfiguration.parity = parity;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);


  /* 2 */
  stopBits = BDM_2_STOP_BITS;
  printf(".stopBits: %u\n", stopBits);
  serialConfiguration.stopBits = stopBits;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* 0 */
  stopBits = 0;
  printf(".stopBits: %u\n", stopBits);
  serialConfiguration.stopBits = stopBits;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);

  /* 1 */
  stopBits = BDM_1_STOP_BIT;
  printf(".stopBits: %u\n", stopBits);
  serialConfiguration.stopBits = stopBits;
  fd = Bdm_serialOpen(BDM_TEST_SERIAL_DEVICE, &serialConfiguration, BDM_PIN_UNSET, BDM_PIN_UNSET);
  printf("result: %d\n\n", fd);
  fgets(nl, sizeof(nl), stdin);
  puts("");
  close(fd);


//O_TTY_INIT

  return 0;
}

