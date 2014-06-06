/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.
*/

package unoParser;

import unoParser.BdmFieldError;
import unoParser.BdmFieldType;
import unoParser.BdmFieldWarning;

import bdmModel.BdmAttribute;
import bdmModel.BdmException;

import com.sun.star.table.XCell;

/** Allowed values (example for Boolean):
 * - null               --> error
 * - Empty string       --> error
 * - "null" (any case)  --> null (if allowed)
 * - "-"                --> default value (if allowed)
 * - "false" (any case) --> false
 * - "true"  (any case) --> true
 */


enum BdmFieldType
{
  ftEmpty,
  ftLabel,
  ftBoolean,
  ftEnumeration,
  ftInteger,
  ftReal,
  ftString
}

enum BdmFieldError
{
  feNone,
  feNoSuchCell,
  feEmpty,
  feNull,
  feNoDefault,
  feInvalidValue,
  feLessThan,
  feGreaterThan,
  feTooShort,
  feTooLong
}

enum BdmFieldWarning
{
  fwNone,
  fwWrongCase
}

public abstract class BdmAttributeParser
{
  protected BdmFieldType m_type;
  protected BdmAttribute m_bdmAttribute;
  protected XCell        m_cell;
  protected XCell        m_errorCell;

  protected String          m_rawValue;
  protected BdmFieldError   m_error;
  protected BdmFieldWarning m_warning;

  private static final String NULL_ATTRIBUTE    = "null"; /* lower case string */
  private static final String DEFAULT_ATTRIBUTE = "-";

  protected static final String m_noSuchCellError  = "No such cell!";
  protected static final String m_emptyError       = "Cell is empty!";
  protected static final String m_nullError        = "NULL is not allowed!";
  protected static final String m_invalidError     = "Cell is invalid!";
  protected static final String m_lessThanError    = "Cell is less than allowed value!";
  protected static final String m_greaterThanError = "Cell is greater than allowed value!";
  protected static final String m_tooShortError    = "String is too short!";
  protected static final String m_tooLongError     = "String is too long!";

  //public abstract BdmFieldError getError();
  //public abstract String getErrorMessage();
  //public abstract int getLine();
  //public abstract int getColumn();
  //public abstract void displayError();
  //public abstract void clearError();

  public BdmAttributeParser(BdmAttribute bdmAttribute, XCell cell, XCell errorCell)
  {
    m_bdmAttribute = bdmAttribute;
    m_cell         = cell;
    m_errorCell    = errorCell;

    m_error   = BdmFieldError.feNone;
    m_warning = BdmFieldWarning.fwNone;
  }

  public void parse() throws bdmModel.BdmException
  {
    m_rawValue = m_cell.getFormula();

    if(m_rawValue.equals(""))
    {
      m_error = BdmFieldError.feEmpty;
      throw new BdmException();
    }
    else if(m_rawValue.toLowerCase().equals(NULL_ATTRIBUTE))
    {
      try
      {
        m_bdmAttribute.setNull();
      }
      catch(BdmException bdmException)
      {
        m_error = BdmFieldError.feNull;
        throw bdmException;
      }

      /* Check for case warning */
      if((!m_rawValue.equals(NULL_ATTRIBUTE)) && (!m_rawValue.equals(NULL_ATTRIBUTE.toUpperCase())))
      {
        m_warning = BdmFieldWarning.fwWrongCase;
      }
    }
    else if(m_rawValue.equals(DEFAULT_ATTRIBUTE))
    {
      try
      {
        m_bdmAttribute.setDefault();
      }
      catch(BdmException bdmException)
      {
        m_error = BdmFieldError.feNoDefault;
        throw bdmException;
      }
    }
    else
    {
      doParse();
    }
  }

  public abstract void doParse() throws bdmModel.BdmException;

}
