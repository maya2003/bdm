/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package bdmModel;

import bdmModel.BdmAttribute;
import bdmModel.BdmException;

public class BdmStringAttribute extends BdmAttribute
{
  protected String  m_default;
  protected Integer m_minLength;
  protected Integer m_maxLength;
  protected String  m_value;

  public BdmStringAttribute(String name, boolean nullAllowed, boolean hasDefaultValue, String initialValue, Integer minLength, Integer maxLength) throws BdmException
  {
    super(name, nullAllowed, hasDefaultValue);

    m_default   = initialValue;
    m_minLength = minLength;
    m_maxLength = maxLength;

    setValue(initialValue);
  }

  public String getValue()
  {
    return m_value;
  }

  public void setDefault() throws BdmException
  {
    if(!m_hasDefault)
    {
      throw new BdmException();
    }

    m_value = m_default;
  }

  public void setValue(String value) throws BdmException
  {
    if(value == null)
    {
      if(!m_nullAllowed)
      {
        throw new BdmException();
      }

      m_isNull = true;
      m_value = null;
    }
    else
    {
      if(m_minLength != null)
      {
        if(value.length() < m_minLength)
        {
          throw new BdmException();
        }
      }

      if(m_maxLength != null)
      {
        if(value.length() > m_maxLength)
        {
          throw new BdmException();
        }
      }

      m_isNull = false;
      m_value = value;
    }
  }

}
