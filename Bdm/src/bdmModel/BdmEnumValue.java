/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.
*/

package bdmModel;

public class BdmEnumValue
{
  protected String m_name;
  protected long   m_value;
  
  public BdmEnumValue(String name, long value)
  {
    m_name  = name;
    m_value = value;
  }

  public BdmEnumValue(String name)
  {
    m_name  = name;
    m_value = 0;
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
