/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.
*/

package unoParser;

import bdmModel.BdmEnumValue;
import bdmModel.BdmException;
import bdmModel.BdmSetAttribute;
import com.sun.star.table.XCell;

public class BdmSetAttributeParser extends BdmAttributeParser
{
  public BdmSetAttributeParser(BdmSetAttribute bdmSetAttribute, XCell cell, XCell errorCell)
  {
    super(bdmSetAttribute, cell, errorCell);

    m_type = BdmFieldType.ftInteger;
  }

  @Override
  public void doParse() throws BdmException
  {
    String delims = "[, ]+";
    String[] enumValues = m_rawValue.split(delims);

    for(String enumValue: enumValues)
    {
      ((BdmSetAttribute)m_bdmAttribute).add(new BdmEnumValue(enumValue));
    }
  }

}
