/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package bdmGenerator;

import bdmModel.BdmException;
import bdmModel.BdmField;

public class BdmFieldGenerator
{
  protected BdmField m_bdmField;

  protected final String m_type;
  protected final String m_fullName;
  protected final String m_fullNameUpper;
  protected final String m_min;
  protected final String m_max;

  public final BdmValidityAttributeGenerator m_errorValues;
  public final BdmValidityAttributeGenerator m_notAvailableValues;
  public final BdmValidityAttributeGenerator m_validValues;

  public BdmFieldGenerator(BdmFrameGenerator bdmFrameGenerator, BdmField bdmField)
  {
    m_bdmField = bdmField;

    BdmCaseFormat bdmCaseFormat = new BdmCaseFormat(m_bdmField.m_name.getValue());

    m_type = bdmCaseFormat.toUpperCamel();

    m_fullName = new StringBuilder(bdmFrameGenerator.getName())
      .append("->")
      .append(bdmCaseFormat.toLowerCamel()).toString();

    m_fullNameUpper = new StringBuilder(bdmFrameGenerator.getFullNameUpper())
      .append('_')
      .append(bdmCaseFormat.toUpper()).toString();

    m_min = new StringBuilder(m_fullNameUpper)
      .append('_')
      .append("MIN").toString();

    m_max = new StringBuilder(m_fullNameUpper)
      .append('_')
      .append("MAX").toString();

    m_errorValues        = new BdmValidityAttributeGenerator(bdmField.m_errorValues);
    m_notAvailableValues = new BdmValidityAttributeGenerator(bdmField.m_notAvailableValues);
    m_validValues        = new BdmValidityAttributeGenerator(bdmField.m_validValues);
  }

  /** "u8" */
  public String getFieldType()
  {
    return m_bdmField.m_type.getValue();
  }

  /** "SampleField" */
  public String getType()
  {
    return m_type;
  }

  /** "sampleField" */
  public String getName()
  {
    return m_bdmField.m_name.getValue();
  }

  /** "sampleFrame->sampleField" */
  public String getFullName()
  {
    return m_fullName;
  }

  /** "SAMPLE_PROTOCOL_SAMPLE_FRAME_SAMPLE_FIELD" */
  public String getFullNameUpper()
  {
    return m_fullNameUpper;
  }

  /** false | true */
  public boolean hasMin()
  {
    return !m_bdmField.m_rawMin.isNull();
  }

  /** "SAMPLE_PROTOCOL_SAMPLE_FRAME_SAMPLE_FIELD_MIN" */
  public String getMin()
  {
    return m_min;
  }

  /** 5 */
  public int getMinValue() throws BdmException
  {
    return m_bdmField.m_rawMin.getValue();
  }

  /** false | true */
  public boolean hasMax()
  {
    return !m_bdmField.m_rawMax.isNull();
  }

  /** "SAMPLE_PROTOCOL_SAMPLE_FRAME_SAMPLE_FIELD_MAX" */
  public String getMax()
  {
    return m_max;
  }

  /** 7 */
  public int getMaxValue() throws BdmException
  {
    return m_bdmField.m_rawMax.getValue();
  }


  public void appendCheckFieldsBounds(StringBuilder s)
  {
    s.append("  switch("); s.append(getFullName()); s.append(")\n" +
             "  {\n");

    /* Error values */
    m_errorValues.appendCheckSet(s,        "      /* field invalid */\n" +
                                           "      valid = false;\n");

    /* Not available values */
    m_notAvailableValues.appendCheckSet(s, "      /* The field is valid, do nothing. */\n");

    /* Valid values */
    m_validValues.appendCheckSet(s,        "      /* The field is valid, do nothing. */\n");

    s.append("    default:\n" +
             "    {\n" +
             "      /* field invalid */\n" +
             "      valid = false;\n" +
             "      break;\n" +
             "    }\n");

    s.append("  }\n");

    s.append("\n");

    /* Error values */
    m_errorValues.appendCheckRange(s, getFullName(),        "    /* field invalid */\n" +
                                                            "    valid = false;\n");

    /* Not available values */
    m_notAvailableValues.appendCheckRange(s, getFullName(), "    /* The field is valid, do nothing. */\n");

    /* Valid values */
    m_validValues.appendCheckRange(s, getFullName(),        "    /* The field is valid, do nothing. */\n");
  }

}
