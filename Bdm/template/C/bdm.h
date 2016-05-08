/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

#ifndef __BDM_H__
#define __BDM_H__

#include <stdbool.h>
#include <stdint.h>
#include <stddef.h>

#include "bdm_serial.h"

#ifdef __cplusplus
extern "C"
{
#endif /* __cplusplus */

typedef   int8_t  s8;
typedef  uint8_t  u8;
typedef  int16_t s16;
typedef uint16_t u16;
typedef  int32_t s32;
typedef uint32_t u32;

typedef enum
{
  BDM_STX   = 0x02,
  BDM_ETX   = 0x03,
  BDM_DLE   = 0x10,
  BDM_SPACE = 0x20,
  BDM_DEL   = 0x7F,
} Bdm_Character;

///////////////////////////////////////////////////////////////////////////////////////

typedef enum
{
  BDM_TS_WAIT_STX,
  BDM_TS_WAIT_DATA,
  BDM_TS_WAIT_ESCAPED_DATA
} Bdm_TransparencyState;

typedef struct
{
  const u8 frameStart;
  const u8 frameEnd;
  const u8 escapeCharacter;
} Bdm_TransparencyConfiguration;

typedef struct
{
  Bdm_TransparencyState state;
  const Bdm_TransparencyConfiguration *configuration;
} Bdm_TransparencyContext;

///////////////////////////////////////////////////////////////////////////////////////

#define BDM_MAX_FRAME_SIZE 100

typedef enum
{
  BDM_FS_WAIT_START,
  BDM_FS_WAIT_HEADER,
  BDM_FS_WAIT_DATA,
  BDM_FS_WAIT_FOOTER,
  BDM_FS_WAIT_END
} Bdm_FrameState;

typedef struct
{
  const u16 protocolSignature;
} Bdm_FrameConfiguration;

#pragma pack(push, 1)

typedef union
{
  struct
  {
//  u8  preamble[8];
    u16 protocolSignature;
//  u16 protocolVersion;
//  u16 sourceAddress;
//  u16 destinationAddress;
//  u8  frameCounter;
    u8  id;
//  u8  size;
  };
  u8 data[0];
} Bdm_FrameHeader;

typedef union
{
  struct
  {
    u16 crc;
  };
  u8 data[0];
} Bdm_FrameFooter;

#pragma pack(pop)

typedef struct
{
  const Bdm_FrameConfiguration *configuration;

  Bdm_FrameHeader txHeader;
  Bdm_FrameFooter txFooter;

  Bdm_FrameState  state;
  size_t          fieldSize;
  size_t          dataSize;
  Bdm_FrameHeader rxHeader;
  u8              data[BDM_MAX_FRAME_SIZE];
  Bdm_FrameFooter rxFooter;

  Bdm_TransparencyContext transparencyContext;
} Bdm_FrameContext;

///////////////////////////////////////////////////////////////////////////////////////

typedef struct
{
  const char *device;
  /* speed, etc */
} Bdm_ProtocolConfiguration;

typedef struct
{
  const Bdm_ProtocolConfiguration *configuration;

  int fd;
  Bdm_FrameContext frameContext;
} Bdm_ProtocolContext;

///////////////////////////////////////////////////////////////////////////////////////

extern bool Bdm_linuxReceiveThreadStart(Bdm_ProtocolContext *context);
extern bool Bdm_startFrame(const Bdm_ProtocolContext *context, u8 data);
extern bool Bdm_appendData(const Bdm_ProtocolContext *context, u8 data);
extern bool Bdm_sendFrame(const Bdm_ProtocolContext *context, u8 data);
extern bool Bdm_transparencyInit(Bdm_ProtocolContext *context);
extern bool Bdm_transparencySendStx(const Bdm_ProtocolContext *context);
extern bool Bdm_transparencySendEtx(const Bdm_ProtocolContext *context);
extern bool Bdm_transparencySendData(const Bdm_ProtocolContext *context, const u8 *data, size_t size);
extern bool Bdm_transparencyOctetReceived(Bdm_ProtocolContext *context, u8 octet);
extern bool Bdm_protocolInit(Bdm_ProtocolContext *context);
extern bool Bdm_protocolSendFrame(Bdm_ProtocolContext *context, u8 id, const void *data, size_t size);
extern bool Bdm_protocolStartOfFrameReceived(Bdm_ProtocolContext *context);
extern bool Bdm_protocolEndOfFrameReceived(Bdm_ProtocolContext *context);
extern bool Bdm_protocolOctetReceived(Bdm_ProtocolContext *context, u8 octet);
extern bool Bdm_getFrameSize(size_t *size, u8 id);
extern void Bdm_dumpMemory(const u8 *data, size_t size);
extern void Bdm_dumpFrame(const Bdm_ProtocolContext *context);

#ifdef __cplusplus
}
#endif /* __cplusplus */

#endif /* __BDM_H__ */

