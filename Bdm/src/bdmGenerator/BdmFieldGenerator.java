/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.
*/

package bdmGenerator;

import bdmModel.BdmEnumValue;
import bdmModel.BdmException;
import bdmModel.BdmField;

public class BdmFieldGenerator
{
  protected BdmField m_bdmField;
  
  protected final String m_fullName;
  protected final String m_fullNameUpper;
  protected final String m_min;
  protected final String m_max;

  public BdmFieldGenerator(BdmFrameGenerator bdmFrameGenerator, BdmField bdmField)
  {
    m_bdmField = bdmField;

    BdmCaseFormat bdmCaseFormat = new BdmCaseFormat(m_bdmField.m_name.getValue());

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
  }

  /** "u8" */
  public String getType()
  {
    return m_bdmField.m_type.getValue();
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
    s.append("    switch("); s.append(getFullName()); s.append(")\n" +
             "    {\n");

    /* Error values */
    for(BdmEnumValue enumValue: m_bdmField.m_errorValues.m_values)
    {
      s.append("      case "); s.append(enumValue.getName()); s.append(":\n" +
               "      {\n" +
               "        /* field invalid */\n" +
               "        valid = false;\n" +
               "        break;\n" +
               "      }\n\n");
    }

    /* Not available values */
    for(BdmEnumValue enumValue: m_bdmField.m_notAvailableValues.m_values)
    {
      s.append("      case "); s.append(enumValue.getName()); s.append(":\n" +
               "      {\n" +
               "        /* The field is valid, do nothing. */\n" +
               "        break;\n" +
               "      }\n" + 
               "\n");
    }

    /* Valid values */
    for(BdmEnumValue enumValue: m_bdmField.m_validValues.m_values)
    {
      s.append("      case "); s.append(enumValue.getName()); s.append(":\n" +
               "      {\n" +
               "        /* The field is valid, do nothing. */\n" +
               "        break;\n" +
               "      }\n" + 
               "\n");
    }

    s.append("      default:\n" +
             "      {\n" +
             "        /* field invalid */\n" +
             "        valid = false;\n" +
             "        break;\n" +
             "      }\n");

    s.append("    }\n");

    s.append("\n");

    if(hasMin() || hasMax())
    {
      s.append("    if");
    }

    if(hasMin() && hasMax())
    {
      s.append("(");
    }

    if(hasMin())
    {
      s.append("(");
      s.append(getFullName());
      s.append(" <= ");
      s.append(getMin());
      s.append(")");
    };

    if(hasMin() && hasMax())
    {
      s.append(
        " || ");
    }

    if(hasMax())
    {
      s.append("(");
      s.append(getFullName());
      s.append(" >= ");
      s.append(getMax());
      s.append(")");
    }

    if(hasMin() && hasMax())
    {
      s.append(
        ")");
    }

    if(hasMin() || hasMax())
    {
      s.append(
          "\n");

      s.append(
        "    {\n" +
        "      /* field invalid */\n" +
        "      valid = false;\n" +
        "    }\n");
    }

    if(hasMin() || hasMax())
    {
      s.append("\n");
    }
  }

}
