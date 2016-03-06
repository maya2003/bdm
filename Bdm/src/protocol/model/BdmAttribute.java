/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package protocol.model;

import protocol.model.BdmException;

public abstract class BdmAttribute
{
  protected String  m_name;
  protected boolean m_nullAllowed;
  protected boolean m_hasDefault;

  protected boolean m_isNull;

  public BdmAttribute(String name, boolean nullAllowed, boolean hasDefault)
  {
    m_name        = name;
    m_nullAllowed = nullAllowed;
    m_hasDefault  = hasDefault;
  }

  public String getName()
  {
    return m_name;
  }
  
  public boolean isNullAllowed()
  {
    return m_nullAllowed;
  }

  public boolean isNull()
  {
    return m_isNull;
  }

  public void setNull() throws BdmException
  {
    if(!m_nullAllowed)
    {
      throw new BdmException();
    }
    
    m_isNull  = true;
  }

  public boolean hasDefault()
  {
    return m_hasDefault;
  }

  public abstract void setDefault() throws BdmException;

}
