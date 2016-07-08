/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

// TODO: Review man from "Canonical and noncanonical mode".
// TODO: config: port type (EIA-232/EIA-422/EIA-485/...)
// TODO: config: readSize
// TODO: config: timeouts and delays
// TODO: config: terminaison, pull-up, pull-down, and power
// TODO: config: manage custom speeds (B38400)
// TODO: restore PARMRK (prefix framing and parity errors)
// TODO: manage duplicated 0xFF due to PARMRK
// TODO: bus access
// TODO: direction control and signalisation
// TODO: external IO for direction control and signalisation
// TODO: publish IO + negative logic
// TODO: set errno?
// TODO: tcflush()

#define _BSD_SOURCE
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>
#include <termios.h>
#include <unistd.h>
#include <sys/ioctl.h>

#include "bdm_serial.h"

/*
 */
int Bdm_serialOpen(const char *deviceName, const Bdm_SerialConfiguration *configuration, Bdm_SerialPinState dtrInitialState, Bdm_SerialPinState rtsInitialState)
{
  int speed;
  int dataBits;
  int fd;
  int result;
  struct termios termios;
  int signalling;

  if((!deviceName) || (!*deviceName))
  {
    return -1;
  }

  if(!configuration)
  {
    return -1;
  }

  /* speed */
  switch(configuration->speed)
  {
    case 50:
    {
      speed = B50;
      break;
    }

    case 75:
    {
      speed = B75;
      break;
    }

    case 110:
    {
      speed = B110;
      break;
    }

    case 134:
    {
      speed = B134;
      break;
    }

    case 150:
    {
      speed = B150;
      break;
    }

    case 200:
    {
      speed = B200;
      break;
    }

    case 300:
    {
      speed = B300;
      break;
    }

    case 600:
    {
      speed = B600;
      break;
    }

    case 1200:
    {
      speed = B1200;
      break;
    }

    case 1800:
    {
      speed = B1800;
      break;
    }

    case 2400:
    {
      speed = B2400;
      break;
    }

    case 4800:
    {
      speed = B4800;
      break;
    }

    case 9600:
    {
      speed = B9600;
      break;
    }

    case 19200:
    {
      speed = B19200;
      break;
    }

    case 57600:
    {
      speed = B57600;
      break;
    }

    case 115200:
    {
      speed = B115200;
      break;
    }

    case 230400:
    {
      speed = B230400;
      break;
    }

    case 0:
    {
      return -1;
    }

    case 38400:
    default:
    {
      speed = B38400; /* attention required! */
      break;
    }
  }

  /* data bits */
  switch(configuration->dataBits)
  {
    case BDM_5_DATA_BITS:
    {
      dataBits = CS5;
      break;
    }

    case BDM_6_DATA_BITS:
    {
      dataBits = CS6;
      break;
    }

    case BDM_7_DATA_BITS:
    {
      dataBits = CS7;
      break;
    }

    case BDM_8_DATA_BITS:
    {
      dataBits = CS8;
      break;
    }

    case BDM_9_DATA_BITS:
    default:
    {
      return -1;
    }
  }

  /* parity */
  if(configuration->parity > BDM_PARITY_SPACE)
  {
    return -1;
  }

  /* stop bits */
  if((configuration->stopBits < BDM_1_STOP_BIT) || (configuration->stopBits > BDM_2_STOP_BITS))
  {
    return -1;
  }

  /* initial signalling state */

  if(dtrInitialState > BDM_PIN_UNSET)
  {
    return -1;
  }

  if(rtsInitialState > BDM_PIN_UNSET)
  {
    return -1;
  }

  /* Open the serial port */
  fd = open(deviceName, O_RDWR | O_NOCTTY); // TODO: O_NOATIME, O_NOCTTY, O_SYNC ??
  if(fd < 0)
  {
    perror("open()");
    return -1;
  }

  /* Get the current configuration */
  result = tcgetattr(fd, &termios);
  if(result != 0)
  {
    perror("tcgetattr()");
    close(fd);
    return -1;
  }

  /* Update the configuration */

  /* input modes */
  termios.c_iflag |=  IGNBRK;
  termios.c_iflag &= ~BRKINT;
  termios.c_iflag &= ~IGNPAR;
  termios.c_iflag &= ~PARMRK;
  termios.c_iflag &= ~ISTRIP;
  termios.c_iflag &= ~INLCR;
  termios.c_iflag &= ~IGNCR;
  termios.c_iflag &= ~ICRNL;
  termios.c_iflag &= ~IUCLC;
  termios.c_iflag &= ~IXANY;
  termios.c_iflag &= ~IMAXBEL;
  termios.c_iflag &= ~IUTF8;

  /* output modes */
  termios.c_oflag &= ~OPOST; /* refer to http://www.gnu.org/software/libc/manual/html_node/Output-Modes.html#Output-Modes for explanation */
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
  termios.c_cflag |=  CREAD;
  termios.c_cflag &= ~HUPCL;
  termios.c_cflag |=  CLOCAL;
/*termios.c_cflag &= ~LOBLK;*/ /* not implemented on Linux */
  termios.c_cflag &= ~CIBAUD;

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
/*termios.c_lflag &= ~DEFECHO;*/ /* not implemented on Linux */
  termios.c_lflag &= ~FLUSHO;
  termios.c_lflag &= ~NOFLSH;
  termios.c_lflag &= ~TOSTOP;
  termios.c_lflag &= ~PENDIN;
  termios.c_lflag &= ~IEXTEN;

  /* special characters */
  termios.c_cc[VDISCARD] = _POSIX_VDISABLE;
/*termios.c_cc[VDSUSP]   = _POSIX_VDISABLE;*/ /* not implemented on Linux */
  termios.c_cc[VEOF]     = _POSIX_VDISABLE;
  termios.c_cc[VEOL]     = _POSIX_VDISABLE;
  termios.c_cc[VEOL2]    = _POSIX_VDISABLE;
  termios.c_cc[VERASE]   = _POSIX_VDISABLE;
  termios.c_cc[VINTR]    = _POSIX_VDISABLE;
  termios.c_cc[VKILL]    = _POSIX_VDISABLE;
  termios.c_cc[VLNEXT]   = _POSIX_VDISABLE;
  termios.c_cc[VQUIT]    = _POSIX_VDISABLE;
  termios.c_cc[VREPRINT] = _POSIX_VDISABLE;
/*termios.c_cc[VSTATUS]  = _POSIX_VDISABLE;*/ /* not implemented on Linux */
  termios.c_cc[VSUSP]    = _POSIX_VDISABLE;
/*termios.c_cc[VSWTCH]   = _POSIX_VDISABLE;*/ /* not implemented on Linux */
  termios.c_cc[VWERASE]  = _POSIX_VDISABLE;

  /* input speed */
  result = cfsetispeed(&termios, speed);
  if(result != 0)
  {
    perror("cfsetispeed()");
    close(fd);
    return -1;
  }

  /* output speed */
  result = cfsetospeed(&termios, speed);
  if(result != 0)
  {
    perror("cfsetospeed()");
    close(fd);
    return -1;
  }

  /* data bits */
  termios.c_cflag |= dataBits;

  /* parity */
  switch(configuration->parity)
  {
    case BDM_PARITY_NONE:
    {
      termios.c_iflag &= ~INPCK;
      termios.c_cflag &= ~PARENB;
      termios.c_cflag &= ~PARODD;
      termios.c_cflag &= ~CMSPAR;
      break;
    }

    case BDM_PARITY_ODD:
    {
      termios.c_iflag |=  INPCK;
      termios.c_cflag |=  PARENB;
      termios.c_cflag |=  PARODD;
      termios.c_cflag &= ~CMSPAR;
      break;
    }

    case BDM_PARITY_EVEN:
    {
      termios.c_iflag |=  INPCK;
      termios.c_cflag |=  PARENB;
      termios.c_cflag &= ~PARODD;
      termios.c_cflag &= ~CMSPAR;
      break;
    }

    case BDM_PARITY_MARK:
    {
      termios.c_iflag |=  INPCK;
      termios.c_cflag |=  PARENB;
      termios.c_cflag |=  PARODD;
      termios.c_cflag |=  CMSPAR;
      break;
    }

    case BDM_PARITY_SPACE:
    {
      termios.c_iflag |=  INPCK;
      termios.c_cflag |=  PARENB;
      termios.c_cflag &= ~PARODD;
      termios.c_cflag |=  CMSPAR;
      break;
    }

    /* no default */
  }

  /* stop bits */
  if(BDM_1_STOP_BIT == configuration->stopBits)
  {
    termios.c_cflag &= ~CSTOPB;
  }
  else
  {
    termios.c_cflag |=  CSTOPB;
  }

  /* flow control */
  switch(configuration->flowControl)
  {
    case BDM_FLOW_CONTROL_NONE:
    {
      termios.c_iflag &= ~IXON;
      termios.c_iflag &= ~IXOFF;
      termios.c_cflag &= ~CRTSCTS;
      termios.c_cc[VSTART] = _POSIX_VDISABLE;
      termios.c_cc[VSTOP]  = _POSIX_VDISABLE;
      break;
    }

    case BDM_FLOW_CONTROL_XON_XOFF:
    {
      termios.c_iflag |=  IXON;
      termios.c_iflag |=  IXOFF;
      termios.c_cflag &= ~CRTSCTS;
      termios.c_cc[VSTART] = BDM_XON;
      termios.c_cc[VSTOP]  = BDM_XOFF;
      break;
    }

    case BDM_FLOW_CONTROL_RTS_CTS:
    {
      termios.c_iflag &= ~IXON;
      termios.c_iflag &= ~IXOFF;
      termios.c_cflag |=  CRTSCTS;
      termios.c_cc[VSTART] = _POSIX_VDISABLE;
      termios.c_cc[VSTOP]  = _POSIX_VDISABLE;
      break;
    }

    case BDM_FLOW_CONTROL_XON_XOFF_RTS_CTS:
    {
      termios.c_iflag |=  IXON;
      termios.c_iflag |=  IXOFF;
      termios.c_cflag |=  CRTSCTS;
      termios.c_cc[VSTART] = BDM_XON;
      termios.c_cc[VSTOP]  = BDM_XOFF;
      break;
    }

    /* no default */
  }

  /* number of characters to read */
  termios.c_cc[VMIN]  = 1; // CONFIG (number of characters to read)

  /* read timeout */
  termios.c_cc[VTIME] = 1; // CONFIG (read timeout)

  /* Set the new configuration */
  result = tcsetattr(fd, TCSAFLUSH, &termios);
  if(result != 0)
  {
    perror("tcsetattr()");
    close(fd);
    return -1;
  }

  /* initial signalling state */

  result = 0;

  switch(dtrInitialState)
  {
    case BDM_PIN_INACTIVE:
    {
      signalling = TIOCM_DTR;
      result = ioctl(fd, TIOCMBIC, &signalling);
      break;
    }

    case BDM_PIN_ACTIVE:
    {
      signalling = TIOCM_DTR;
      result = ioctl(fd, TIOCMBIS, &signalling);
      break;
    }

    case BDM_PIN_UNSET:
    {
      /* no action */
      break;
    }

    /* no default */
  }

  if(result < 0)
  {
    perror("ioctl()");
    close(fd);
    return -1;
  }

  switch(rtsInitialState)
  {
    case BDM_PIN_INACTIVE:
    {
      signalling = TIOCM_RTS;
      result = ioctl(fd, TIOCMBIC, &signalling);
      break;
    }

    case BDM_PIN_ACTIVE:
    {
      signalling = TIOCM_RTS;
      result = ioctl(fd, TIOCMBIS, &signalling);
      break;
    }

    case BDM_PIN_UNSET:
    {
      /* no action */
      break;
    }

    /* no default */
  }

  if(result < 0)
  {
    perror("ioctl()");
    close(fd);
    return -1;
  }

  return fd;
}

