/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package bdmModel;

public class BdmEnumValue
{
  protected boolean m_hasName;
  protected String  m_name;
  protected boolean m_hasValue;
  protected long    m_value;

  public BdmEnumValue(String name, long value)
  {
    m_hasName  = true;
    m_name     = name;
    m_hasValue = true;
    m_value    = value;
  }

  public BdmEnumValue(String name)
  {
    m_hasName  = true;
    m_name     = name;
    m_hasValue = false;
  }
  
  public BdmEnumValue(long value)
  {
    m_hasName  = false;
    m_hasValue = true;
    m_value = value;
  }

  public String getName()
  {
    return m_name;
  }

  public long getValue()
  {
    return m_value;
  }

}
