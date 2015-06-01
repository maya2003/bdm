/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

// TODO: Review man from "Canonical and noncanonical mode".
// TODO: config: device selection
// TODO: config: port type (EIA-232/EIA-422/EIA-485/...)
// TODO: config: speed + special speeds
// TODO: config: dataBits
// TODO: config: parity
// TODO: config: stopBits
// TODO: config: flowControl
// TODO: config: readSize
// TODO: config: timeouts and delays
// TODO: config: initial signalisation states
// TODO: config: terminaison, pull-up, pull-down, and power
// TODO: bus access
// TODO: direction control and signalisation
// TODO: external IO for direction control and signalisation
// TODO: publish IO + inverted logic
/* TODO:
- tcsendbreak()
- tcdrain()
- tcflush()
- tcflow()
- cfmakeraw()
*/

#define _BSD_SOURCE
#include <stdbool.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>
#include <termios.h>
#include <unistd.h>

#include "bdm.h"

/*
 */
bool Bdm_serialOpen(Bdm_ProtocolContext *context)
{
  int result;
  struct termios termios;

  /* Open the serial port */
  context->fd = open("/dev/ttyUSB0", O_RDWR | O_NOCTTY); // TODO: O_NOATIME, O_NOCTTY, O_SYNC ??
  if(context->fd < 0)
  {
    perror("open()");
    return false;
  }

  /* Get the current configuration */
  result = tcgetattr(context->fd, &termios);
  if(result != 0)
  {
    perror("tcgetattr()");
    return false;
  }

  /* Update the configuration */

  /* input modes */
  termios.c_iflag |=  IGNBRK;
  termios.c_iflag &= ~BRKINT;
  termios.c_iflag &= ~IGNPAR;
  termios.c_iflag |=  PARMRK;  /* prefix framing and parity errors */
  termios.c_iflag |=  INPCK;   // CONFIG (parity)
  termios.c_iflag &= ~ISTRIP;
  termios.c_iflag &= ~INLCR;
  termios.c_iflag &= ~IGNCR;
  termios.c_iflag &= ~ICRNL;
  termios.c_iflag &= ~IUCLC;
  termios.c_iflag &= ~IXON;    // CONFIG (flow control)
  termios.c_iflag &= ~IXANY;
  termios.c_iflag &= ~IXOFF;   // CONFIG (flow control)
  termios.c_iflag &= ~IMAXBEL;
  termios.c_iflag &= ~IUTF8;

  /* output modes */
  termios.c_oflag &= ~OPOST; /* refer to https://www.freebsd.org/cgi/man.cgi?query=termios&sektion=4 for explanation */
  termios.c_oflag &= ~OLCUC;
  termios.c_oflag &= ~ONLCR;
  termios.c_oflag &= ~OCRNL;
  termios.c_oflag &= ~ONOCR;
  termios.c_oflag &= ~ONLRET;
  termios.c_oflag &= ~OFILL;
  termios.c_oflag &= ~OFDEL;
  termios.c_oflag &= ~NLDLY;  /* (NL0,  NL1) */
  termios.c_oflag &= ~CRDLY;  /* (CR0,  CR1,  CR2,  CR3) */
  termios.c_oflag &= ~TABDLY; /* (TAB0, TAB1, TAB2, TAB3=XTABS) */
  termios.c_oflag &= ~BSDLY;  /* (BS0,  BS1) */
  termios.c_oflag &= ~VTDLY;  /* (VT0,  VT1) */
  termios.c_oflag &= ~FFDLY;  /* (FF0,  FF1) */

  /* control modes */
  termios.c_cflag &= ~CBAUD;
  termios.c_cflag &= ~CBAUDEX;
  termios.c_cflag &= ~CSIZE;
  termios.c_cflag |=  CS8;    // CONFIG (data bits)
  termios.c_cflag &= ~CSTOPB; // CONFIG (1 stop bit)
  termios.c_cflag |=  CREAD;
  termios.c_cflag |=  PARENB; // CONFIG (parity)
  termios.c_cflag &= ~PARODD; // CONFIG (parity)
  termios.c_cflag &= ~HUPCL;
  termios.c_cflag |=  CLOCAL;
/*termios.c_cflag &= ~LOBLK;*/ /* Not implemented on Linux */
  termios.c_cflag &= ~CIBAUD;
  termios.c_cflag &= ~CMSPAR;  // CONFIG (parity)
  termios.c_cflag &= ~CRTSCTS; // CONFIG (flow control)

  /* local modes */
  termios.c_lflag &= ~ISIG;
  termios.c_lflag &= ~ICANON;
  termios.c_lflag &= ~XCASE;
  termios.c_lflag &= ~ECHO;
  termios.c_lflag &= ~ECHOE;
  termios.c_lflag &= ~ECHOK;
  termios.c_lflag &= ~ECHONL;
  termios.c_lflag &= ~ECHOCTL;
  termios.c_lflag &= ~ECHOPRT;
  termios.c_lflag &= ~ECHOKE;
/*termios.c_lflag &= ~DEFECHO;*/ /* Not implemented on Linux */
  termios.c_lflag &= ~FLUSHO;
  termios.c_lflag &= ~NOFLSH;
  termios.c_lflag &= ~TOSTOP;
  termios.c_lflag &= ~PENDIN;
  termios.c_lflag &= ~IEXTEN;

  /* special characters */
  termios.c_cc[VDISCARD] = _POSIX_VDISABLE;
/*termios.c_cc[VDSUSP]   = _POSIX_VDISABLE;*/ /* Not implemented on Linux */
  termios.c_cc[VEOF]     = _POSIX_VDISABLE;
  termios.c_cc[VEOL]     = _POSIX_VDISABLE;
  termios.c_cc[VEOL2]    = _POSIX_VDISABLE;
  termios.c_cc[VERASE]   = _POSIX_VDISABLE;
  termios.c_cc[VINTR]    = _POSIX_VDISABLE;
  termios.c_cc[VKILL]    = _POSIX_VDISABLE;
  termios.c_cc[VLNEXT]   = _POSIX_VDISABLE;
  termios.c_cc[VMIN]     = 1; // CONFIG (number of characters to read)
  termios.c_cc[VQUIT]    = _POSIX_VDISABLE;
  termios.c_cc[VREPRINT] = _POSIX_VDISABLE;
  termios.c_cc[VSTART]   = _POSIX_VDISABLE; // CONFIG (flow control)
/*termios.c_cc[VSTATUS]  = _POSIX_VDISABLE;*/ /* Not implemented on Linux */
  termios.c_cc[VSTOP]    = _POSIX_VDISABLE; // CONFIG (flow control)
  termios.c_cc[VSUSP]    = _POSIX_VDISABLE;
/*termios.c_cc[VSWTCH]   = _POSIX_VDISABLE;*/ /* Not implemented on Linux */
  termios.c_cc[VTIME]    = 1; // CONFIG (read timeout)
  termios.c_cc[VWERASE]  = _POSIX_VDISABLE;

  /* input speed */
  result = cfsetispeed(&termios, B9600); // CONFIG (speed)
  if(result != 0)
  {
    perror("cfsetispeed()");
    return false;
  }

  /* output speed */
  result = cfsetospeed(&termios, B9600); // CONFIG (speed)
  if(result != 0)
  {
    perror("cfsetospeed()");
    return false;
  }

  /* Set the new configuration */
  result = tcsetattr(context->fd, TCSAFLUSH, &termios);
  if(result != 0)
  {
    perror("tcsetattr()");
    return false;
  }

  return true;
}

