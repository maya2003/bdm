/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

// TODO: check lifecycle of types
// TODO: check lifecycle of objects accessed by address

#include <stdio.h>
#include <unistd.h>

#include "bdm.h"

/*
 */
int main(void)
{
  const Bdm_SerialConfiguration serialConfiguration =
  {
    speed:           9600,
    dataBits:        BDM_8_DATA_BITS,
    parity:          BDM_PARITY_EVEN,
    stopBits:        BDM_1_STOP_BIT,
    flowControl:     BDM_FLOW_CONTROL_NONE,
    dtrInitialState: BDM_PIN_UNSET,
    rtsInitialState: BDM_PIN_UNSET
  };

  static const Bdm_ProtocolConfiguration protocolConfiguration =
  {
    device: "/dev/ttyUSB0"
  };

  static const Bdm_FrameConfiguration frameConfiguration =
  {
    protocolSignature: 0x4D79
  };

  static const Bdm_TransparencyConfiguration transparencyConfiguration =
  {
    frameStart:      BDM_STX,
    frameEnd:        BDM_ETX,
    escapeCharacter: BDM_DLE
  };

  static Bdm_ProtocolContext protocolContext =
  {
    configuration: &protocolConfiguration,
    frameContext:
    {
      configuration: &frameConfiguration,
      state:         BDM_FS_WAIT_START,
//    fieldSize:     0,
//    dataSize:      0,
      transparencyContext:
      {
        state:         BDM_TS_WAIT_STX,
        configuration: &transparencyConfiguration
      }
    }
  };

  u8 frame1[] = {BDM_STX, BDM_STX, BDM_STX, 0, 1, 2, 3, 0xFF, 4, BDM_DLE, 1, BDM_DLE, BDM_STX, BDM_DLE, BDM_ETX, BDM_DLE, BDM_DLE, 5, 7, BDM_DLE};
  u8 frame2[] = {};

  protocolContext.fd = Bdm_serialOpen("/dev/ttyUSB0", &serialConfiguration);

  Bdm_linuxReceiveThreadStart(&protocolContext);

  puts("tx:");
  Bdm_dumpMemory(frame1, sizeof(frame1));
  Bdm_protocolSendFrame(&protocolContext, 1, frame1, sizeof(frame1));

  puts("\ntx:");
  Bdm_dumpMemory(frame2, sizeof(frame2));
  Bdm_protocolSendFrame(&protocolContext, 2, frame2, sizeof(frame2));

  puts("\ntx:");
  Bdm_dumpMemory(frame1, sizeof(frame1));
  Bdm_protocolSendFrame(&protocolContext, 1, frame1, sizeof(frame1));
  puts("\n--\n");

  sleep(1);

  return 0;
}

