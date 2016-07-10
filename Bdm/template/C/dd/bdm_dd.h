/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

typedef struct
{
  bool isValid;
  u64  value;
} Bdm_Data;

void Bdm_set_bool(Bdm_Data *data, bool value);
void Bdm_clear_bool(Bdm_Data *data);
bool Bdm_get_bool(const Bdm_Data *data, bool *value);

void Bdm_set_u8(Bdm_Data *data, u8 value);
void Bdm_clear_u8(Bdm_Data *data);
bool Bdm_get_u8(const Bdm_Data *data, u8 *value);

void Bdm_set_s8(Bdm_Data *data, s8 value);
void Bdm_clear_s8(Bdm_Data *data);
bool Bdm_get_s8(const Bdm_Data *data, s8 *value);

void Bdm_set_u16(Bdm_Data *data, u16 value);
void Bdm_clear_u16(Bdm_Data *data);
bool Bdm_get_u16(const Bdm_Data *data, u16 *value);

void Bdm_set_s16(Bdm_Data *data, s16 value);
void Bdm_clear_s16(Bdm_Data *data);
bool Bdm_get_s16(const Bdm_Data *data, s16 *value);

void Bdm_set_u32(Bdm_Data *data, u32 value);
void Bdm_clear_u32(Bdm_Data *data);
bool Bdm_get_u32(const Bdm_Data *data, u32 *value);

void Bdm_set_s32(Bdm_Data *data, s32 value);
void Bdm_clear_s32(Bdm_Data *data);
bool Bdm_get_s32(const Bdm_Data *data, s32 *value);

void Bdm_set_u64(Bdm_Data *data, u64 value);
void Bdm_clear_u64(Bdm_Data *data);
bool Bdm_get_u64(const Bdm_Data *data, u64 *value);

void Bdm_set_s64(Bdm_Data *data, s64 value);
void Bdm_clear_s64(Bdm_Data *data);
bool Bdm_get_s64(const Bdm_Data *data, s64 *value);
