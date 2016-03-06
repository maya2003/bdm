/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package protocol.parser;

import protocol.model.BdmException;
import protocol.model.BdmIntegerAttribute;
import protocol.parser.BdmAttributeParser;
import protocol.parser.BdmFieldType;


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
    try
    {
      ((BdmIntegerAttribute)m_bdmAttribute).setValue(Integer.parseInt(m_rawValue));
    }
    catch(NumberFormatException e)
    {
      ((BdmIntegerAttribute)m_bdmAttribute).setValue(Integer.parseInt(m_rawValue.substring(2), 16));
    }
  }

}
