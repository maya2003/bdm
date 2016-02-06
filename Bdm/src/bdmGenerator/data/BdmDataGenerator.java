/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
   See the file COPYING for copying permission.

   https://github.com/maya2003/bdm
*/

package bdmGenerator.data;

import bdmGenerator.BdmCaseFormat;
import bdmModel.data.BdmData;

public class BdmDataGenerator
{
  protected BdmData m_bdmData;

  protected final String m_fullName;

  public BdmDataGenerator(BdmDataDictionaryGenerator bdmDataDictionaryGenerator, BdmData bdmData)
  {
    m_bdmData = bdmData;

    BdmCaseFormat bdmCaseFormat = new BdmCaseFormat(m_bdmData.m_name.getValue());

    m_fullName = new StringBuilder(bdmDataDictionaryGenerator.m_nameUpperCamel)
      .append("_")
      .append(bdmCaseFormat.toLowerCamel()).toString();
  }

  public void appendFrameStructureDeclaration(StringBuilder s)
  {
    s.append("extern ");
    s.append(m_bdmData.m_cType.getValue());
    s.append(" ");
    s.append(m_fullName);
    s.append(";\n");
  }

  public void appendFrameStructureDefinition(StringBuilder s)
  {
    s.append(m_bdmData.m_cType.getValue());
    s.append(" ");
    s.append(m_fullName);
    s.append(";\n");
  }

}
