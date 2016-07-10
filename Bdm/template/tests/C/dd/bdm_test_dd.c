/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

#include <stdio.h>

#include "bdm.h"


/*
 */
int main(void)
{
  bool result;
  bool boolValue = true;
   u8  u8Value   = 5;
   s8  s8Value   = 5;
  u16 u16Value   = 5;
  s16 s16Value   = 5;
  u32 u32Value   = 5;
  s32 s32Value   = 5;
  u64 u64Value   = 5;
  s64 s64Value   = 5;
  Bdm_Data bdm_Data;


  /* bool */

  Bdm_set_bool(&bdm_Data, false);
  result = Bdm_get_bool(&bdm_Data, &boolValue);
  if(result)
  {
    printf("%20hhu : %016hhX (valid)\n", boolValue, boolValue);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_set_bool(&bdm_Data, true);
  result = Bdm_get_bool(&bdm_Data, &boolValue);
  if(result)
  {
    printf("%20hhu : %016hhX (valid)\n", boolValue, boolValue);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_clear_bool(&bdm_Data);
  result = Bdm_get_bool(&bdm_Data, &boolValue);
  if(result)
  {
    printf("%20hhu : %016hhX (valid)\n", boolValue, boolValue);
  }
  else
  {
    printf("INVALID!\n");
  }


  /* u8 */

  Bdm_set_u8(&bdm_Data, 0);
  result = Bdm_get_u8(&bdm_Data, &u8Value);
  if(result)
  {
    printf("%20hhu : %016hhX (valid)\n", u8Value, u8Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_set_u8(&bdm_Data, UINT8_MAX);
  result = Bdm_get_u8(&bdm_Data, &u8Value);
  if(result)
  {
    printf("%20hhu : %016hhX (valid)\n", u8Value, u8Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_clear_u8(&bdm_Data);
  result = Bdm_get_u8(&bdm_Data, &u8Value);
  if(result)
  {
    printf("%20hhu : %016hhX (valid)\n", u8Value, u8Value);
  }
  else
  {
    printf("INVALID!\n");
  }


  /* s8 */

  Bdm_set_s8(&bdm_Data, INT8_MIN);
  result = Bdm_get_s8(&bdm_Data, &s8Value);
  if(result)
  {
    printf("%20hhd : %016hhX (valid)\n", s8Value, s8Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_set_s8(&bdm_Data, INT8_MAX);
  result = Bdm_get_s8(&bdm_Data, &s8Value);
  if(result)
  {
    printf("%20hhd : %016hhX (valid)\n", s8Value, s8Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_clear_s8(&bdm_Data);
  result = Bdm_get_s8(&bdm_Data, &s8Value);
  if(result)
  {
    printf("%20hhd : %016hhX (valid)\n", s8Value, s8Value);
  }
  else
  {
    printf("INVALID!\n");
  }


  /* u16 */

  Bdm_set_u16(&bdm_Data, 0);
  result = Bdm_get_u16(&bdm_Data, &u16Value);
  if(result)
  {
    printf("%20hu : %016hX (valid)\n", u16Value, u16Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_set_u16(&bdm_Data, UINT16_MAX);
  result = Bdm_get_u16(&bdm_Data, &u16Value);
  if(result)
  {
    printf("%20hu : %016hX (valid)\n", u16Value, u16Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_clear_u16(&bdm_Data);
  result = Bdm_get_u16(&bdm_Data, &u16Value);
  if(result)
  {
    printf("%20hu : %016hX (valid)\n", u16Value, u16Value);
  }
  else
  {
    printf("INVALID!\n");
  }


  /* s16 */

  Bdm_set_s16(&bdm_Data, INT16_MIN);
  result = Bdm_get_s16(&bdm_Data, &s16Value);
  if(result)
  {
    printf("%20hd : %016hX (valid)\n", s16Value, s16Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_set_s16(&bdm_Data, INT16_MAX);
  result = Bdm_get_s16(&bdm_Data, &s16Value);
  if(result)
  {
    printf("%20hd : %016hX (valid)\n", s16Value, s16Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_clear_s16(&bdm_Data);
  result = Bdm_get_s16(&bdm_Data, &s16Value);
  if(result)
  {
    printf("%20hd : %016hX (valid)\n", s16Value, s16Value);
  }
  else
  {
    printf("INVALID!\n");
  }


  /* u32 */

  Bdm_set_u32(&bdm_Data, 0);
  result = Bdm_get_u32(&bdm_Data, &u32Value);
  if(result)
  {
    printf("%20u : %016X (valid)\n", u32Value, u32Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_set_u32(&bdm_Data, UINT32_MAX);
  result = Bdm_get_u32(&bdm_Data, &u32Value);
  if(result)
  {
    printf("%20u : %016X (valid)\n", u32Value, u32Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_clear_u32(&bdm_Data);
  result = Bdm_get_u32(&bdm_Data, &u32Value);
  if(result)
  {
    printf("%20u : %016X (valid)\n", u32Value, u32Value);
  }
  else
  {
    printf("INVALID!\n");
  }


  /* s32 */

  Bdm_set_s32(&bdm_Data, INT32_MIN);
  result = Bdm_get_s32(&bdm_Data, &s32Value);
  if(result)
  {
    printf("%20d : %016X (valid)\n", s32Value, s32Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_set_s32(&bdm_Data, INT32_MAX);
  result = Bdm_get_s32(&bdm_Data, &s32Value);
  if(result)
  {
    printf("%20d : %016X (valid)\n", s32Value, s32Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_clear_s32(&bdm_Data);
  result = Bdm_get_s32(&bdm_Data, &s32Value);
  if(result)
  {
    printf("%20d : %016X (valid)\n", s32Value, s32Value);
  }
  else
  {
    printf("INVALID!\n");
  }


  /* u64 */

  Bdm_set_u64(&bdm_Data, 0);
  result = Bdm_get_u64(&bdm_Data, &u64Value);
  if(result)
  {
    printf("%20lu : %016lX (valid)\n", u64Value, u64Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_set_u64(&bdm_Data, UINT64_MAX);
  result = Bdm_get_u64(&bdm_Data, &u64Value);
  if(result)
  {
    printf("%20lu : %016lX (valid)\n", u64Value, u64Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_clear_u64(&bdm_Data);
  result = Bdm_get_u64(&bdm_Data, &u64Value);
  if(result)
  {
    printf("%20lu : %016lX (valid)\n", u64Value, u64Value);
  }
  else
  {
    printf("INVALID!\n");
  }


  /* s64 */

  Bdm_set_s64(&bdm_Data, INT64_MIN);
  result = Bdm_get_s64(&bdm_Data, &s64Value);
  if(result)
  {
    printf("%20ld : %016lX (valid)\n", s64Value, s64Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_set_s64(&bdm_Data, INT64_MAX);
  result = Bdm_get_s64(&bdm_Data, &s64Value);
  if(result)
  {
    printf("%20ld : %016lX (valid)\n", s64Value, s64Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  Bdm_clear_s64(&bdm_Data);
  result = Bdm_get_s64(&bdm_Data, &s64Value);
  if(result)
  {
    printf("%20ld : %016lX (valid)\n", s64Value, s64Value);
  }
  else
  {
    printf("INVALID!\n");
  }

  return 0;
}

