/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package protocol.parser;

import protocol.parser.BdmCell;

import com.sun.star.lang.IndexOutOfBoundsException;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.table.XCell;

public class BdmCell
{
  private XSpreadsheet m_xSpreadsheet;
  private int m_topLine;
  private int m_currentLine;
  private int m_leftColumn;
  private int m_currentColumn;

  public BdmCell(XSpreadsheet xSpreadsheet, int line, int column)
  {
    m_xSpreadsheet  = xSpreadsheet;
    m_currentLine   = m_topLine    = line;
    m_currentColumn = m_leftColumn = column;
  }

  public XCell getCell() throws IndexOutOfBoundsException
  {
    return m_xSpreadsheet.getCellByPosition(m_currentColumn, m_currentLine);
  }

  public XCell getCell(int down, int right) throws IndexOutOfBoundsException
  {
    m_currentLine   += down;
    m_currentColumn += right;
    return m_xSpreadsheet.getCellByPosition(m_currentColumn, m_currentLine);
  }

  public BdmCell top()
  {
    m_currentLine = m_topLine;
    return this;
  }

  public BdmCell up()
  {
    m_currentLine--;
    return this;
  }

  public BdmCell down()
  {
    m_currentLine++;
    return this;
  }

  public BdmCell leftside()
  {
    m_currentColumn = m_leftColumn;
    return this;
  }

  public BdmCell left()
  {
    m_currentColumn--;
    return this;
  }

  public BdmCell right()
  {
    m_currentColumn++;
    return this;
  }

  public XSpreadsheet getXSpreadsheet()
  {
    return m_xSpreadsheet;
  }

  public int getLine()
  {
    return m_currentLine;
  }

  public int getColumn()
  {
    return m_currentColumn;
  }

}
