/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package unoGenerator;

import unoParser.BdmCell;
import bdmModel.BdmField;
import bdmModel.BdmFrame;
import bdmModel.BdmProtocol;

import com.sun.star.lang.IndexOutOfBoundsException;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheetDocument;

public class DataDictionaryGenerator
{
  protected XSpreadsheetDocument m_xSpreadsheetDocument;

  public DataDictionaryGenerator(XSpreadsheetDocument xSpreadsheetDocument)
  {
    m_xSpreadsheetDocument = xSpreadsheetDocument;
  }

  public void fill(XSpreadsheet xSpreadsheet, BdmProtocol bdmProtocol)
  {
    try
    {
      BdmCell bdmCell = new BdmCell(xSpreadsheet, 3 +6, 6);
      
      for(BdmFrame bdmFrame: bdmProtocol.frames)
      {
        fill(bdmCell, bdmFrame);
      }
    } catch(IndexOutOfBoundsException e) {}
  }

  protected void fill(BdmCell bdmCell, BdmFrame bdmFrame) throws IndexOutOfBoundsException
  {
    for(BdmField bdmField: bdmFrame.fields)
    {
      bdmCell.getCell(1, 0).setFormula(bdmFrame.m_name.getValue() + "_" + bdmField.m_name.getValue());
    }
  }

}
