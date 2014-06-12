/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package bdmModel;

import java.util.Set;
import java.util.LinkedHashSet;

public class BdmSet
{
  public final Set<BdmEnumValue> m_values;

  public BdmSet() throws BdmException
  {
    // TODO Replace by java.util.EnumSet<>?
    // TODO Sort
    m_values = new LinkedHashSet<BdmEnumValue>();
  }
  
  public void add(BdmEnumValue bdmEnumValue)
  {
    m_values.add(bdmEnumValue);
  }

  public void setDefault() throws BdmException
  {
    m_values.clear();
  }

}
