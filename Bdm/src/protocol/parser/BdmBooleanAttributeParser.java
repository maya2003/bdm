/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package protocol.parser;

import protocol.model.BdmBooleanAttribute;
import protocol.model.BdmException;
import protocol.parser.BdmAttributeParser;
import protocol.parser.BdmFieldError;
import protocol.parser.BdmFieldType;
import protocol.parser.BdmFieldWarning;

import com.sun.star.table.XCell;


public class BdmBooleanAttributeParser extends BdmAttributeParser
{
  public BdmBooleanAttributeParser(BdmBooleanAttribute bdmBooleanAttribute, XCell cell, XCell errorCell)
  {
    super(bdmBooleanAttribute, cell, errorCell);

    m_type = BdmFieldType.ftBoolean;
  }

  @Override
  public void doParse() throws BdmException
  {
    if(m_rawValue.toLowerCase().equals("'false"))
    {
      ((BdmBooleanAttribute)m_bdmAttribute).setValue(false);
    }
    else if(m_rawValue.toLowerCase().equals("'true"))
    {
      ((BdmBooleanAttribute)m_bdmAttribute).setValue(true);
    }
    else
    {
      m_error = BdmFieldError.feInvalidValue;
      throw new BdmException();
    }

    /* Check for case warning */
    if(!m_rawValue.equals("'false") && !m_rawValue.equals("'true"))
    {
      m_warning = BdmFieldWarning.fwWrongCase;
    }
  }

}
