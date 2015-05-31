/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

#include <stdbool.h>
#include <stdint.h>
#include <stddef.h>

typedef  uint8_t  u8;
typedef uint16_t u16;
typedef uint32_t u32;

typedef enum
{
  BDM_STX = 0x02,
  BDM_ETX = 0x03,
  BDM_DLE = 0x10
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
  const u8 escape;

  Bdm_TransparencyState state;
} Bdm_TransparencyContext;

///////////////////////////////////////////////////////////////////////////////////////

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
    u8  command;
    u8  size; //!!
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
  Bdm_FrameState  state;
  size_t          length;

  Bdm_FrameHeader header;
  u8              data[100];
  Bdm_FrameFooter footer;
} Bdm_FrameContext;

///////////////////////////////////////////////////////////////////////////////////////

extern void Bdm_dumper(const u8 *data, u8 size);
extern bool Bdm_serialOpen(int *fd);
extern bool Bdm_receiveStart(void);
extern bool Bdm_write(const u8 *data, size_t size);
extern bool BdmTransparency_initContext(Bdm_TransparencyContext *context);
extern bool BdmTransparency_sendStx(const Bdm_TransparencyContext *context);
extern bool BdmTransparency_sendEtx(const Bdm_TransparencyContext *context);
extern bool BdmTransparency_sendData(const Bdm_TransparencyContext *context, const u8 *data, u16 size);
extern bool BdmTransparency_octetReceived(Bdm_TransparencyContext *context, u8 octet);
extern bool BdmProtocol_initContext(Bdm_FrameContext *context);
extern bool BdmProtocol_sendFrame(Bdm_FrameContext *context, const u8 *data, u8 size);
extern bool BdmProtocol_startOfFrameReceived(Bdm_FrameContext *context);
extern bool BdmProtocol_endOfFrameReceived(Bdm_FrameContext *context);
extern bool BdmProtocol_octetReceived(Bdm_FrameContext *context, u8 octet);

