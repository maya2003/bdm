/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package bdmGenerator;

import bdmModel.BdmEnumValue;
import bdmModel.BdmRange;
import bdmModel.BdmSet;
import bdmModel.BdmValidityAttribute;

public class BdmValidityAttributeGenerator
{
  BdmValidityAttribute m_bdmValidityAttribute;

  public BdmValidityAttributeGenerator(BdmValidityAttribute bdmValidityAttribute)
  {
    m_bdmValidityAttribute = bdmValidityAttribute;
  }

  public void appendEnumDefinition(StringBuilder s)
  {
    BdmRange bdmRange = m_bdmValidityAttribute.getRange();

    if(bdmRange != null)
    {
      // TODO spacers
      s.append("  ");
      s.append(new BdmCaseFormat(bdmRange.getLowerBound().getName()).toUpper());
      s.append(" = 0x");
      s.append(Long.toHexString(bdmRange.getLowerBound().getValue()).toUpperCase());
      s.append(",\n");

      s.append("  ");
      s.append(new BdmCaseFormat(bdmRange.getUpperBound().getName()).toUpper());
      s.append(" = 0x");
      // TODO: check max ranges / set limits
      s.append(Long.toHexString(bdmRange.getUpperBound().getValue()).toUpperCase());
      s.append(",\n");
    }

    BdmSet bdmSet = m_bdmValidityAttribute.getSet();

    if(bdmSet != null)
    {
      for(BdmEnumValue enumValue: bdmSet.m_values)
      {
        s.append("  ");
        s.append(new BdmCaseFormat(enumValue.getName()).toUpper());
        s.append(" = 0x");
        // TODO: check max ranges / set limits
        s.append(Long.toHexString(enumValue.getValue()).toUpperCase());
        s.append(",\n");
      }
    }
  }

  public void appendCheckRange(StringBuilder s, String fullAttributeName, String actionStatement)
  {
    BdmRange bdmRange = m_bdmValidityAttribute.getRange();

    if(bdmRange != null)
    {
      if(bdmRange.getLowerBound() != null || bdmRange.getUpperBound() != null)
      {
        s.append("  if");
      }

      if(bdmRange.getLowerBound() != null && bdmRange.getUpperBound() != null)
      {
        s.append("(");
      }

      if(bdmRange.getLowerBound() != null)
      {
        s.append("(");
        s.append(fullAttributeName);
        s.append(" <= ");
        s.append(new BdmCaseFormat(bdmRange.getLowerBound().getName()).toUpper());
        s.append(")");
      };

      if(bdmRange.getLowerBound() != null && bdmRange.getUpperBound() != null)
      {
        s.append(" || ");
      }

      if(bdmRange.getUpperBound() != null)
      {
        s.append("(");
        s.append(fullAttributeName);
        s.append(" >= ");
        s.append(new BdmCaseFormat(bdmRange.getUpperBound().getName()).toUpper());
        s.append(")");
      }

      if(bdmRange.getLowerBound() != null && bdmRange.getUpperBound() != null)
      {
        s.append(")");
      }

      if(bdmRange.getLowerBound() != null || bdmRange.getUpperBound() != null)
      {
        s.append("\n" +
                 "  {\n")
         .append(actionStatement)
         .append("  }\n\n");
      }
    }
  }

  public void appendCheckSet(StringBuilder s, String actionStatement)
  {
    BdmSet bdmSet = m_bdmValidityAttribute.getSet();

    if(bdmSet != null)
    {
      for(BdmEnumValue enumValue: bdmSet.m_values)
      {
        s.append("    case "); s.append(new BdmCaseFormat(enumValue.getName()).toUpper()); s.append(":\n" +
                 "    {\n")
         .append(actionStatement)
         .append("      break;\n" +
                 "    }\n\n");
      }
    }
  }

}
