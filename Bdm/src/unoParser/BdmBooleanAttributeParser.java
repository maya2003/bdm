/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.
*/

package unoParser;

import bdmModel.BdmBooleanAttribute;
import bdmModel.BdmException;

import com.sun.star.table.XCell;

import unoParser.BdmAttributeParser;
import unoParser.BdmFieldError;
import unoParser.BdmFieldType;
import unoParser.BdmFieldWarning;

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
