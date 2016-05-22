/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

#ifndef __BDM_SERIAL_H__
#define __BDM_SERIAL_H__

#include <stdint.h>

#ifdef __cplusplus
extern "C"
{
#endif /* __cplusplus */

#define BDM_XON  0x11
#define BDM_XOFF 0x13

typedef uint32_t u32;

typedef enum
{
  BDM_5_DATA_BITS = 5,
  BDM_6_DATA_BITS = 6,
  BDM_7_DATA_BITS = 7,
  BDM_8_DATA_BITS = 8,
  BDM_9_DATA_BITS = 9
} Bdm_SerialDataBits;

typedef enum
{
  BDM_PARITY_NONE,
  BDM_PARITY_ODD,
  BDM_PARITY_EVEN,
  BDM_PARITY_MARK,
  BDM_PARITY_SPACE
} Bdm_SerialParity;

typedef enum
{
  BDM_1_STOP_BIT  = 1,
  BDM_2_STOP_BITS = 2
} Bdm_SerialStopBits;

typedef enum
{
  BDM_FLOW_CONTROL_NONE,
  BDM_FLOW_CONTROL_XON_XOFF,
  BDM_FLOW_CONTROL_RTS_CTS,
  BDM_FLOW_CONTROL_XON_XOFF_RTS_CTS
} Bdm_SerialFlowControl;

typedef struct
{
  u32                   speed;
  Bdm_SerialDataBits    dataBits;
  Bdm_SerialParity      parity;
  Bdm_SerialStopBits    stopBits;
  Bdm_SerialFlowControl flowControl;
} Bdm_SerialConfiguration;

typedef enum
{
  BDM_PIN_INACTIVE,
  BDM_PIN_ACTIVE,
  BDM_PIN_UNSET
} Bdm_SerialPinState;

extern int Bdm_serialOpen(const char *deviceName, const Bdm_SerialConfiguration *configuration, Bdm_SerialPinState dtrInitialState, Bdm_SerialPinState rtsInitialState);

#ifdef __cplusplus
}
#endif /* __cplusplus */

#endif /* __BDM_SERIAL_H__ */

