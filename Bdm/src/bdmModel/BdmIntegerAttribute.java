/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package bdmModel;

import bdmModel.BdmAttribute;
import bdmModel.BdmException;

public class BdmIntegerAttribute extends BdmAttribute
{
  protected Integer m_default;
  protected Integer m_min;
  protected Integer m_max;
  protected int     m_value;

  public BdmIntegerAttribute(String name, boolean nullAllowed, boolean hasDefaultValue, Integer initialValue, Integer min, Integer max) throws BdmException
  {
    super(name, nullAllowed, hasDefaultValue);

    m_default = initialValue;
    m_min     = min;
    m_max     = max;
    setIntegerValue(m_default);
  }

  public int getValue() throws BdmException
  {
    if(m_isNull)
    {
      throw new BdmException();
    }

    return m_value;
  }

  public Integer getIntegerValue()
  {
    if(m_isNull)
    {
      return null;
    }
    else
    {
      return new Integer(m_value);
    }
  }

  public void setDefault() throws BdmException
  {
    if(!m_hasDefault)
    {
      throw new BdmException();
    }

    setIntegerValue(m_default);
  }

  public void setValue(int value)
  {
    m_isNull = false;
    m_value  = value;
  }

  public void setIntegerValue(Integer value) throws BdmException
  {
    if(value == null)
    {
      if(!m_nullAllowed)
      {
        throw new BdmException();
      }

      m_isNull = true;
    }
    else
    {
      if(m_min != null)
      {
        if(value < m_min)
        {
          throw new BdmException();
        }
      }

      if(m_max != null)
      {
        if(value > m_max)
        {
          throw new BdmException();
        }
      }

      m_isNull = false;
      m_value  = value;
    }
  }

}
