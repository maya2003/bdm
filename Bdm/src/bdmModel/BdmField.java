/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package bdmModel;

import bdmModel.BdmBooleanAttribute;
import bdmModel.BdmException;
import bdmModel.BdmIntegerAttribute;
import bdmModel.BdmStringAttribute;
import bdmModel.BooleanValue;

public class BdmField
{
  public static final String FIELD_MARKER = "Field";

  public final BdmStringAttribute   m_fieldMarker; // special
  public final BdmStringAttribute   m_requirementId;
  public final BdmStringAttribute   m_coveredRequirements;
  public final BdmStringAttribute   m_name;
  public final BdmStringAttribute   m_id;
  public final BdmIntegerAttribute  m_index;
  public final BdmStringAttribute   m_type;
  public final BdmBooleanAttribute  m_notAvailableAllowed;
  public final BdmStringAttribute   m_unit;
  public final BdmIntegerAttribute  m_startByte;
  public final BdmIntegerAttribute  m_startBit;
  public final BdmIntegerAttribute  m_size;
  public final BdmValidityAttribute m_errorValues;
  public final BdmValidityAttribute m_notAvailableValues;
  public final BdmValidityAttribute m_validValues;
  public final BdmIntegerAttribute  m_offsetNumerator;
  public final BdmIntegerAttribute  m_gainNumerator;
  public final BdmIntegerAttribute  m_denominator;
  public final BdmBooleanAttribute  m_specificConversion;
//public final BdmStringAttribute   m_realOffset; // special
//public final BdmStringAttribute   m_realGain;   // special
  public final BdmStringAttribute   m_comment;

  public final BdmIntegerAttribute  m_rawMin;      // remove
  public final BdmIntegerAttribute  m_rawMax;      // remove
  public final BdmStringAttribute   m_destination; // remove

  public BdmField() throws BdmException //TODO !!
  {
    m_fieldMarker         = new BdmStringAttribute  ("fieldMarker",         false, true,  FIELD_MARKER, FIELD_MARKER.length(), FIELD_MARKER.length()); // special
    m_requirementId       = new BdmStringAttribute  ("requirementId",       true,  true,  null,    null, null);
    m_coveredRequirements = new BdmStringAttribute  ("coveredRequirements", true,  true,  null,    null, null);
    m_name                = new BdmStringAttribute  ("name",                false, false, "",      null, null);
    m_id                  = new BdmStringAttribute  ("id",                  true,  true,  null,    null, null);
    m_index               = new BdmIntegerAttribute ("index",               true,  true,  null,    null, null);
    m_type                = new BdmStringAttribute  ("type",                false, false, "u8",    null, null);
    m_notAvailableAllowed = new BdmBooleanAttribute ("notAvailableAllowed", false, false, BooleanValue.dvFalse);
    m_unit                = new BdmStringAttribute  ("unit",                true,  true,  null,    null, null);
    m_startByte           = new BdmIntegerAttribute ("startByte",           false, false, 0,       0,    null);
    m_startBit            = new BdmIntegerAttribute ("startBit",            false, false, 0,       0,    7);
    m_size                = new BdmIntegerAttribute ("size",                false, false, 0,       0,    null);
    m_errorValues         = new BdmValidityAttribute("errorValues",         true);
    m_notAvailableValues  = new BdmValidityAttribute("notAvailableValues",  true);
    m_validValues         = new BdmValidityAttribute("validValues",         true);
    m_offsetNumerator     = new BdmIntegerAttribute ("offsetNumerator",     false, true,  0,       null, null);
    m_gainNumerator       = new BdmIntegerAttribute ("gainNumerator",       false, true,  1,       null, null);
    m_denominator         = new BdmIntegerAttribute ("denominator",         false, true,  1,       1,    null);
    m_specificConversion  = new BdmBooleanAttribute ("specificConversion",  false, true,  BooleanValue.dvFalse);
  //m_realOffset          = new BdmStringAttribute  ("realOffset",          true,  false, null,    null, null); // special
  //m_realGain            = new BdmStringAttribute  ("realGain",            true,  false, null,    null, null); // special
    m_comment             = new BdmStringAttribute  ("comment",             true,  true,  null,    null, null);

    m_rawMin              = new BdmIntegerAttribute ("rawMin",              true,  true,  null,    0,    null); // remove
    m_rawMax              = new BdmIntegerAttribute ("rawMax",              true,  true,  null,    0,    null); // remove
    m_destination         = new BdmStringAttribute  ("destination",         true,  false, null,    null, null); // remove
  }

}
