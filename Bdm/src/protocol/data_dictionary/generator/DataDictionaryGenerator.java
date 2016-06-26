/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package protocol.data_dictionary.generator;

import protocol.model.BdmException;
import protocol.model.BdmField;
import protocol.model.BdmFrame;
import protocol.model.BdmProtocol;
import protocol.parser.BdmCell;

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
      BdmCell bdmCell = new BdmCell(xSpreadsheet, 3 -1 +6, 5 + 14);
      
      for(BdmFrame bdmFrame: bdmProtocol.frames)
      {
        fill(bdmCell, bdmFrame);
      }
    } catch(IndexOutOfBoundsException | BdmException e) {}
  }

  protected void fill(BdmCell bdmCell, BdmFrame bdmFrame) throws IndexOutOfBoundsException, BdmException
  {
    for(BdmField bdmField: bdmFrame.fields)
    {
      bdmCell.getCell(1, -14).setFormula("-");
      bdmCell.getCell(0, 1).setFormula(bdmFrame.m_name.getValue() + "_" + bdmField.m_name.getValue());
      bdmCell.getCell(0, 1).setFormula("integer");
      bdmCell.getCell(0, 1).setFormula(bdmField.m_type.getValue());
      bdmCell.getCell(0, 1).setFormula("-");
      bdmCell.getCell(0, 1).setFormula(Integer.toString(bdmField.m_gainNumerator.getValue()));
      bdmCell.getCell(0, 1).setFormula(Integer.toString(bdmField.m_offsetNumerator.getValue()));
      bdmCell.getCell(0, 1).setFormula(Integer.toString(bdmField.m_denominator.getValue()));
      bdmCell.getCell(0, 1).setFormula(bdmField.m_errorValues.toString());
      bdmCell.getCell(0, 1).setFormula(bdmField.m_notAvailableValues.toString());
      bdmCell.getCell(0, 1).setFormula(bdmField.m_validValues.toString());
      bdmCell.getCell(0, 1).setFormula(bdmField.m_notAvailableAllowed.getValue() == true? "true": "false"); // ##
      bdmCell.getCell(0, 1).setFormula("-");

      if(bdmField.m_unit.isNull())
      {
        bdmCell.getCell(0, 1).setFormula("-");
      }
      else
      {
        bdmCell.getCell(0, 1).setFormula(bdmField.m_unit.getValue());
      }

      if(bdmField.m_comment.isNull())
      {
        bdmCell.getCell(0, 1).setFormula("--");
      }
      else
      {
        bdmCell.getCell(0, 1).setFormula(bdmField.m_comment.getValue());
      }
    }
  }

}
