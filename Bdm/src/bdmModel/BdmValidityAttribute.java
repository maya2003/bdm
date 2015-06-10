/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
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
    m_isNull   = true;
  }

  @Override
  public void setDefault() throws BdmException
  {
    m_bdmRange = null;
    m_bdmSet   = null;
    m_isNull   = true;
  }

  public BdmRange getRange()
  {
    return m_bdmRange;
  }

  public void setRange(BdmRange bdmRange)
  {
    m_bdmRange = bdmRange;
    m_isNull = false; // TODO
  }

  public BdmSet getSet()
  {
    return m_bdmSet;
  }

  public void setSet(BdmSet bdmSet)
  {
    m_bdmSet = bdmSet;
    m_isNull = false; // TODO
  }

}
