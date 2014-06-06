/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.
*/

package bdmModel;

import java.util.Set;
import java.util.LinkedHashSet;

public class BdmSetAttribute extends BdmAttribute
{
  public final Set<BdmEnumValue> m_values;

  public BdmSetAttribute(String name, boolean nullAllowed) throws BdmException
  {
    super(name, nullAllowed, true);

    // TODO Replace by java.util.EnumSet<>?
    // Sort
    m_values = new LinkedHashSet<BdmEnumValue>();
  }
  
  public void add(BdmEnumValue bdmEnumValue)
  {
    m_values.add(bdmEnumValue);
  }

  @Override
  public void setDefault() throws BdmException
  {
    m_values.clear();
  }

}
