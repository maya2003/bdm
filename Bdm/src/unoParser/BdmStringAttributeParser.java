/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package unoParser;

import unoParser.BdmAttributeParser;
import unoParser.BdmFieldType;

import bdmModel.BdmException;
import bdmModel.BdmStringAttribute;

import com.sun.star.table.XCell;

public class BdmStringAttributeParser extends BdmAttributeParser
{
  public BdmStringAttributeParser(BdmStringAttribute bdmStringAttribute, XCell cell, XCell errorCell)
  {
    super(bdmStringAttribute, cell, errorCell);

    m_type = BdmFieldType.ftString;
  }

  @Override
  public void doParse() throws BdmException
  {
    ((BdmStringAttribute)m_bdmAttribute).setValue(m_rawValue);
  }

}
