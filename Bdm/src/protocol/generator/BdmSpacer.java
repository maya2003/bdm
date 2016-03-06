/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package protocol.generator;

import java.util.Arrays;

public class BdmSpacer
{
  protected final static int[] m_maxInteger = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};

  protected int m_margin;
  protected int m_width;

  public BdmSpacer()
  {
    m_margin = 0;
    m_width  = 0;
  }

  public BdmSpacer(int margin)
  {
    m_margin = margin;
    m_width  = 0;
  }

  public void set(int width)
  {
    m_width = Math.max(m_width, width);
  }

  public void set(int width, int number)
  {
    m_width = Math.max(m_width, width + getIntegerWidth(number));
  }

  public void set(String string)
  {
    m_width = Math.max(m_width, string.length());
  }

  // TODO Use cache
  public String space()
  {
    char[] chars = new char[m_margin + m_width];
    Arrays.fill(chars, ' ');
    String s = new String(chars);

    return s;
  }

  public String space(int width)
  {
    char[] chars = new char[m_margin + m_width - width];
    Arrays.fill(chars, ' ');
    String s = new String(chars);

    return s;
  }

  public String space(int width, int number)
  {
    char[] chars = new char[m_margin + m_width - width - getIntegerWidth(number)];
    Arrays.fill(chars, ' ');
    String s = new String(chars);

    return s;
  }

  public String space(String string)
  {
    char[] chars = new char[m_margin + m_width - string.length()];
    Arrays.fill(chars, ' ');
    String s = new String(chars);

    return s;
  }

  protected static int getIntegerWidth(int x)
  {
    int i = 0;

    while(x > m_maxInteger[i])
    {
      i++;
    }

    return i + 1;
  }

}
