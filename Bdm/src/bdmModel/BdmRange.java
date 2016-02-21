/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package bdmModel;

public class BdmRange
{
  protected BdmEnumValue  m_lowerBound;
  protected BdmEnumValue  m_upperBound;

  public BdmRange(BdmEnumValue lowerBound, BdmEnumValue upperBound)
  {
    m_lowerBound  = lowerBound;
    m_upperBound  = upperBound;
  }

  public BdmEnumValue getLowerBound()
  {
    return m_lowerBound;
  }

  public BdmEnumValue getUpperBound()
  {
    return m_upperBound;
  }

}
