/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package protocol.model;

import protocol.model.BdmAttribute;
import protocol.model.BdmException;
import protocol.model.BooleanValue;

/* Types of BdmBooleanAttribute:
 * - without default value, NULL not allowed,
 * - without default value, NULL allowed,
 * - with default value,    NULL not allowed,
 * - with default value,    NULL allowed.
 */

enum BooleanValue
{
  dvNull,
  dvFalse,
  dvTrue
}

public class BdmBooleanAttribute extends BdmAttribute
{
  protected BooleanValue m_default;
  protected boolean      m_value;

  public BdmBooleanAttribute(String name, boolean nullAllowed, boolean hasDefaultValue, BooleanValue initialValue) throws BdmException
  {
    super(name, nullAllowed, hasDefaultValue);

    m_default = initialValue;

    setBooleanValue(m_default);
  }

  public boolean getValue() throws BdmException
  {
    if(m_isNull)
    {
      throw new BdmException();
    }

    return m_value;
  }

  public BooleanValue getBooleanValue()
  {
    if(m_isNull)
    {
      return BooleanValue.dvNull;
    }
    else if(m_value == false)
    {
      return BooleanValue.dvFalse;
    }

    return BooleanValue.dvTrue;
  }

  public void setDefault() throws BdmException
  {
    if(!m_hasDefault)
    {
      throw new BdmException();
    }

    setBooleanValue(m_default);
  }

  public void setValue(boolean value)
  {
    m_isNull = false;
    m_value  = value;
  }

  public void setBooleanValue(BooleanValue value) throws BdmException
  {
    switch(value)
    {
      case dvNull:
      {
        if(!m_nullAllowed)
        {
          throw new BdmException();
        }

        m_isNull = true;
        break;
      }

      case dvFalse:
      {
        m_isNull = false;
        m_value  = false;
        break;
      }

      case dvTrue:
      {
        m_isNull = false;
        m_value  = true;
        break;
      }
    }
  }

}
