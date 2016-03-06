/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package protocol.model;

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
