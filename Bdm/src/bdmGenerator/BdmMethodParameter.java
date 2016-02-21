/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package bdmGenerator;

public class BdmMethodParameter
{
  protected String  m_type;
  protected boolean m_pointer;
  protected String  m_name;

  public BdmMethodParameter(String type, String name)
  {
    m_type = type;
    m_name = name;
    
    if(m_type.charAt(m_type.length() - 1) == '*')
    {
      m_pointer = true;
    }
    else
    {
      m_pointer = false;
    }
  }

  public String getType()
  {
    return m_type;
  }

  public boolean isPointer()
  {
    return m_pointer;
  }

  public String getName()
  {
    return m_name;
  }

}
