/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

#include "bdm.h"

/*
 */
void Bdm_set_bool(Bdm_Data *data, bool value)
{
  data->isValid = true;
  data->value   = (u64)value;
}

/*
 */
void Bdm_clear_bool(Bdm_Data *data)
{
  data->isValid = false;
}

/*
 */
bool Bdm_get_bool(const Bdm_Data *data, bool *value)
{
  if(data->isValid)
  {
    *value = (bool)data->value;
    return true;
  }
  else
  {
    return false;
  }
}

/*
 */
void Bdm_set_u8(Bdm_Data *data, u8 value)
{
  data->isValid = true;
  data->value   = (u64)value;
}

/*
 */
void Bdm_clear_u8(Bdm_Data *data)
{
  data->isValid = false;
}

/*
 */
bool Bdm_get_u8(const Bdm_Data *data, u8 *value)
{
  if(data->isValid)
  {
    *value = (u8)data->value;
    return true;
  }
  else
  {
    return false;
  }
}

/*
 */
void Bdm_set_s8(Bdm_Data *data, s8 value)
{
  data->isValid = true;
  data->value   = (u64)value;
}

/*
 */
void Bdm_clear_s8(Bdm_Data *data)
{
  data->isValid = false;
}

/*
 */
bool Bdm_get_s8(const Bdm_Data *data, s8 *value)
{
  if(data->isValid)
  {
    *value = (s8)data->value;
    return true;
  }
  else
  {
    return false;
  }
}

/*
 */
void Bdm_set_u16(Bdm_Data *data, u16 value)
{
  data->isValid = true;
  data->value   = (u64)value;
}

/*
 */
void Bdm_clear_u16(Bdm_Data *data)
{
  data->isValid = false;
}

/*
 */
bool Bdm_get_u16(const Bdm_Data *data, u16 *value)
{
  if(data->isValid)
  {
    *value = (u16)data->value;
    return true;
  }
  else
  {
    return false;
  }
}

/*
 */
void Bdm_set_s16(Bdm_Data *data, s16 value)
{
  data->isValid = true;
  data->value   = (u64)value;
}

/*
 */
void Bdm_clear_s16(Bdm_Data *data)
{
  data->isValid = false;
}

/*
 */
bool Bdm_get_s16(const Bdm_Data *data, s16 *value)
{
  if(data->isValid)
  {
    *value = (s16)data->value;
    return true;
  }
  else
  {
    return false;
  }
}

/*
 */
void Bdm_set_u32(Bdm_Data *data, u32 value)
{
  data->isValid = true;
  data->value   = (u64)value;
}

/*
 */
void Bdm_clear_u32(Bdm_Data *data)
{
  data->isValid = false;
}

/*
 */
bool Bdm_get_u32(const Bdm_Data *data, u32 *value)
{
  if(data->isValid)
  {
    *value = (u32)data->value;
    return true;
  }
  else
  {
    return false;
  }
}

/*
 */
void Bdm_set_s32(Bdm_Data *data, s32 value)
{
  data->isValid = true;
  data->value   = (u64)value;
}

/*
 */
void Bdm_clear_s32(Bdm_Data *data)
{
  data->isValid = false;
}

/*
 */
bool Bdm_get_s32(const Bdm_Data *data, s32 *value)
{
  if(data->isValid)
  {
    *value = (s32)data->value;
    return true;
  }
  else
  {
    return false;
  }
}

/*
 */
void Bdm_set_u64(Bdm_Data *data, u64 value)
{
  data->isValid = true;
  data->value   = (u64)value;
}

/*
 */
void Bdm_clear_u64(Bdm_Data *data)
{
  data->isValid = false;
}

/*
 */
bool Bdm_get_u64(const Bdm_Data *data, u64 *value)
{
  if(data->isValid)
  {
    *value = (u64)data->value;
    return true;
  }
  else
  {
    return false;
  }
}

/*
 */
void Bdm_set_s64(Bdm_Data *data, s64 value)
{
  data->isValid = true;
  data->value   = (u64)value;
}

/*
 */
void Bdm_clear_s64(Bdm_Data *data)
{
  data->isValid = false;
}

/*
 */
bool Bdm_get_s64(const Bdm_Data *data, s64 *value)
{
  if(data->isValid)
  {
    *value = (s64)data->value;
    return true;
  }
  else
  {
    return false;
  }
}

