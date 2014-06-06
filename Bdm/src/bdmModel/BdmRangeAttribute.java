/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.
*/

package bdmModel;

public class BdmRangeAttribute extends BdmValidity
{
  protected String m_lowerBound;
  protected long   m_lowerBoundValue;
  protected String m_upperBound;
  protected long   m_upperBoundValue;

  public BdmRangeAttribute(String lowerBound, long lowerBoundValue, String upperBound, long upperBoundValue)
  {
    m_lowerBound      = lowerBound;
    m_lowerBoundValue = lowerBoundValue;
    m_upperBound      = upperBound;
    m_upperBoundValue = upperBoundValue;
  }

  public BdmRangeAttribute(String lowerBound, String upperBound)
  {
    m_lowerBound = lowerBound;
    m_upperBound = upperBound;

    try
    {
      m_lowerBoundValue = Long.parseLong(lowerBound);
    }
    catch(NumberFormatException e)
    {
      m_lowerBoundValue = 0;
    }

    try
    {
      m_upperBoundValue = Long.parseLong(upperBound);
    }
    catch(NumberFormatException e)
    {
      m_upperBoundValue = 0;
    }
  }

  public String getLowerBound()
  {
    return m_lowerBound;
  }

  public long getLowerBoundValue()
  {
    return m_lowerBoundValue;
  }

  public String getUpperBound()
  {
    return m_upperBound;
  }

  public long getUpperBoundValue()
  {
    return m_upperBoundValue;
  }

}
