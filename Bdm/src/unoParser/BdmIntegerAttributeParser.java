/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.
*/

package unoParser;

import unoParser.BdmAttributeParser;
import unoParser.BdmFieldType;

import bdmModel.BdmException;
import bdmModel.BdmIntegerAttribute;

import com.sun.star.table.XCell;

public class BdmIntegerAttributeParser extends BdmAttributeParser
{
  public BdmIntegerAttributeParser(BdmIntegerAttribute bdmIntegerAttribute, XCell cell, XCell errorCell)
  {
    super(bdmIntegerAttribute, cell, errorCell);

    m_type = BdmFieldType.ftInteger;
  }

  @Override
  public void doParse() throws BdmException
  {
    ((BdmIntegerAttribute)m_bdmAttribute).setValue(Integer.parseInt(m_rawValue));
  }

}
