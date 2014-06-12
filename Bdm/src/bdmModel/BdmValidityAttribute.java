/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package bdmModel;

public class BdmValidityAttribute extends BdmAttribute
{
  protected BdmRange m_bdmRange;
  protected BdmSet   m_bdmSet;

  public BdmValidityAttribute(String name, boolean nullAllowed) throws BdmException
  {
    super(name, nullAllowed, nullAllowed);

    m_bdmRange = null;
    m_bdmSet   = null;
  }

  @Override
  public void setDefault() throws BdmException
  {
    m_bdmRange = null;
    m_bdmSet   = null;
  }

  public BdmRange getRange()
  {
    return m_bdmRange;
  }

  public void setRange(BdmRange bdmRange)
  {
    m_bdmRange = bdmRange;
  }

  public BdmSet getSet()
  {
    return m_bdmSet;
  }

  public void setSet(BdmSet bdmSet)
  {
    m_bdmSet = bdmSet;
  }

}
