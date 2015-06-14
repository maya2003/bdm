/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package unoGenerator;

import java.awt.Color;

import bdmModel.BdmException;
import bdmModel.BdmField;
import bdmModel.BdmFrame;
import bdmModel.BdmProtocol;

import com.sun.star.beans.PropertyVetoException;
import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.beans.XPropertySet;
import com.sun.star.container.ElementExistException;
import com.sun.star.container.NoSuchElementException;
import com.sun.star.container.XNameAccess;
import com.sun.star.container.XNameContainer;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.IndexOutOfBoundsException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.style.XStyleFamiliesSupplier;
import com.sun.star.table.BorderLine;
import com.sun.star.table.CellHoriJustify;
import com.sun.star.table.CellVertJustify;
import com.sun.star.table.XCell;
import com.sun.star.table.XCellRange;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XInterface;
import com.sun.star.util.XMergeable;

/* Merge cases:
 *
 * 76543210
 *
 * ********
 *
 * ********
 * ********
 *
 * ......**
 *
 * **......
 *
 * ..****..
 *
 * ......**
 * ********
 * ********
 * ********
 * **......
 *
 * ......**
 * ********
 *
 * ********
 * **......
 *
 * ********
 * ********
 * **......
 *
 */


public class ProtocolLayoutGenerator
{
  /* Style names */
  protected static final String FRAME_HEADING_STYLE = "Frame heading";
  protected static final String OCTET_HEADING_STYLE = "Octet heading";
  protected static final String BIT_HEADING_STYLE   = "Bit heading";
  protected static final String SPARE_STYLE         = "Spare";
  protected static final String FIELD_STYLE         = "Field";

  protected XSpreadsheetDocument m_xSpreadsheetDocument;
  protected XNameContainer m_cellStyleNameContainer;

  protected static final int m_column = 9;
  protected int m_line;

  public ProtocolLayoutGenerator(XSpreadsheetDocument xSpreadsheetDocument) throws IllegalArgumentException, ElementExistException, UnknownPropertyException, PropertyVetoException, Exception
  {
    m_xSpreadsheetDocument = xSpreadsheetDocument;

    /* Get the list of cell styles */
    XNameAccess styleFamilies = UnoRuntime.queryInterface(XStyleFamiliesSupplier.class, m_xSpreadsheetDocument).getStyleFamilies();
    XNameAccess cellStyles = UnoRuntime.queryInterface(XNameAccess.class, styleFamilies.getByName("CellStyles"));
    m_cellStyleNameContainer = UnoRuntime.queryInterface(XNameContainer.class, cellStyles);

    /* Delete the previous styles */
    deleteStyles();

    /* Create the new styles */
    createStyles();
  }

  // TODO Create styles only if they don't already exist
  protected void deleteStyles() throws WrappedTargetException
  {
    /* Frame heading style */
    try
    {
      m_cellStyleNameContainer.removeByName(FRAME_HEADING_STYLE);
    }
    catch(NoSuchElementException e){}

    /* Octet heading style */
    try
    {
      m_cellStyleNameContainer.removeByName(OCTET_HEADING_STYLE);
    }
    catch(NoSuchElementException e){}

    /* Bit heading style */
    try
    {
      m_cellStyleNameContainer.removeByName(BIT_HEADING_STYLE);
    }
    catch(NoSuchElementException e){}

    /* Spare style */
    try
    {
      m_cellStyleNameContainer.removeByName(SPARE_STYLE);
    }
    catch(NoSuchElementException e){}

    /* Field style */
    try
    {
      m_cellStyleNameContainer.removeByName(FIELD_STYLE);
    }
    catch(NoSuchElementException e){}
  }

  protected void createStyles() throws IllegalArgumentException, ElementExistException, WrappedTargetException, UnknownPropertyException, PropertyVetoException, Exception
  {
    XInterface cellStyle;
    XPropertySet xPropertySet;
    XMultiServiceFactory xMultiServiceFactory = UnoRuntime.queryInterface(XMultiServiceFactory.class, m_xSpreadsheetDocument);

    /* Common style parameters */
    BorderLine borderLine = new BorderLine();
    borderLine.InnerLineWidth = 5;
    borderLine.Color          = Color.black.getRGB() & 0xFFFFFF;

    /* Frame heading style */
    cellStyle = (XInterface)xMultiServiceFactory.createInstance("com.sun.star.style.CellStyle");
    m_cellStyleNameContainer.insertByName(FRAME_HEADING_STYLE, cellStyle);
    xPropertySet = UnoRuntime.queryInterface(XPropertySet.class, cellStyle);
    xPropertySet.setPropertyValue("HoriJustify",   CellHoriJustify.CENTER);
    xPropertySet.setPropertyValue("CellBackColor", Color.orange.getRGB() & 0xFFFFFF);
    xPropertySet.setPropertyValue("TopBorder",     borderLine);
    xPropertySet.setPropertyValue("LeftBorder",    borderLine);
    xPropertySet.setPropertyValue("BottomBorder",  borderLine);
    xPropertySet.setPropertyValue("RightBorder",   borderLine);

    /* Octet heading style */
    cellStyle = (XInterface)xMultiServiceFactory.createInstance("com.sun.star.style.CellStyle");
    m_cellStyleNameContainer.insertByName(OCTET_HEADING_STYLE, cellStyle);
    xPropertySet = UnoRuntime.queryInterface(XPropertySet.class, cellStyle);
    xPropertySet.setPropertyValue("HoriJustify",   CellHoriJustify.RIGHT);

    /* Bit heading style */
    cellStyle = (XInterface)xMultiServiceFactory.createInstance("com.sun.star.style.CellStyle");
    m_cellStyleNameContainer.insertByName(BIT_HEADING_STYLE, cellStyle);
    xPropertySet = UnoRuntime.queryInterface(XPropertySet.class, cellStyle);
    xPropertySet.setPropertyValue("HoriJustify",   CellHoriJustify.CENTER);
    xPropertySet.setPropertyValue("CellBackColor", 0x729FCF);
    xPropertySet.setPropertyValue("TopBorder",     borderLine);
    xPropertySet.setPropertyValue("LeftBorder",    borderLine);
    xPropertySet.setPropertyValue("BottomBorder",  borderLine);
    xPropertySet.setPropertyValue("RightBorder",   borderLine);

    /* Spare style */
    cellStyle = (XInterface)xMultiServiceFactory.createInstance("com.sun.star.style.CellStyle");
    m_cellStyleNameContainer.insertByName(SPARE_STYLE, cellStyle);
    xPropertySet = UnoRuntime.queryInterface(XPropertySet.class, cellStyle);
    xPropertySet.setPropertyValue("HoriJustify",   CellHoriJustify.CENTER);
    xPropertySet.setPropertyValue("CellBackColor", Color.lightGray.getRGB() & 0xFFFFFF);
    xPropertySet.setPropertyValue("TopBorder",     borderLine);
    xPropertySet.setPropertyValue("LeftBorder",    borderLine);
    xPropertySet.setPropertyValue("BottomBorder",  borderLine);
    xPropertySet.setPropertyValue("RightBorder",   borderLine);

    /* Field style */
    cellStyle = (XInterface)xMultiServiceFactory.createInstance("com.sun.star.style.CellStyle");
    m_cellStyleNameContainer.insertByName(FIELD_STYLE, cellStyle);
    xPropertySet = UnoRuntime.queryInterface(XPropertySet.class, cellStyle);
    xPropertySet.setPropertyValue("HoriJustify",   CellHoriJustify.CENTER);
    xPropertySet.setPropertyValue("VertJustify",   CellVertJustify.CENTER_value);   // CellVertJustify.CENTER does not work...
    xPropertySet.setPropertyValue("CellBackColor", Color.pink.getRGB() & 0xFFFFFF);
    xPropertySet.setPropertyValue("TopBorder",     borderLine);
    xPropertySet.setPropertyValue("LeftBorder",    borderLine);
    xPropertySet.setPropertyValue("BottomBorder",  borderLine);
    xPropertySet.setPropertyValue("RightBorder",   borderLine);
  }

  public void drawFrames(XSpreadsheet xSpreadsheet, BdmProtocol bdmProtocol)
  {
    try
    {
      m_line = 11;

      for(BdmFrame bdmFrame: bdmProtocol.frames)
      {
        drawFrame(xSpreadsheet, bdmFrame, bdmProtocol.m_minFrameSize.getValue() * 8);

        m_line += 2;
      }
    }
    catch(IllegalArgumentException | IndexOutOfBoundsException
            | UnknownPropertyException | PropertyVetoException
            | WrappedTargetException | BdmException e) {}
  }

  protected void drawFrame(XSpreadsheet xSpreadsheet, BdmFrame bdmFrame, int minFrameSize) throws BdmException, IndexOutOfBoundsException, IllegalArgumentException, UnknownPropertyException, PropertyVetoException, WrappedTargetException
  {
    int currentShift;
    int byteIndex;
    int bitIndex;
    int top;
    int left;

    XCell      xCell;
    XCellRange xCellRange;

    currentShift = 0;
    byteIndex    = 0;
    bitIndex     = 0;

    /* Bit numbers */
    xCellRange = xSpreadsheet.getCellRangeByPosition(m_column + 1, m_line, m_column + 8, m_line);
    UnoRuntime.queryInterface(XPropertySet.class, xCellRange).setPropertyValue("CellStyle", BIT_HEADING_STYLE);

    for(int i = 0; i <= 7; i++)
    {
      xSpreadsheet.getCellByPosition(m_column + 1 + i, m_line).setValue(i);
    }

    m_line++;

    /* Frame header */
    xCell = xSpreadsheet.getCellByPosition(m_column + 1 , m_line);
    UnoRuntime.queryInterface(XPropertySet.class, xCell).setPropertyValue("CellStyle", FRAME_HEADING_STYLE);
    xCell.setFormula(bdmFrame.m_name.getValue());

    xCellRange = xSpreadsheet.getCellRangeByPosition(m_column + 1, m_line, m_column + 8, m_line);
    UnoRuntime.queryInterface(XMergeable.class, xCellRange).merge(true);

    m_line++;

    for(BdmField bdmField: bdmFrame.fields)
    {
      int fieldShift = bdmField.m_startByte.getValue() * 8 + bdmField.m_startBit.getValue();

      /* Octet number */
      if(bitIndex == 0)
      {
        xCell = xSpreadsheet.getCellByPosition(m_column, m_line + byteIndex);
        UnoRuntime.queryInterface(XPropertySet.class, xCell).setPropertyValue("CellStyle", OCTET_HEADING_STYLE);
        xCell.setValue(byteIndex);
      }

      /* Insert spare bits */
      while(currentShift < fieldShift)
      {
        xCell = xSpreadsheet.getCellByPosition(m_column + 1 + bitIndex, m_line + byteIndex);
        UnoRuntime.queryInterface(XPropertySet.class, xCell).setPropertyValue("CellStyle", SPARE_STYLE);
        xCell.setFormula("-");

        currentShift++;
        bitIndex++;

        if(bitIndex >= 8)
        {
          bitIndex = 0;
          byteIndex++;

          /* Octet number */
          xCell = xSpreadsheet.getCellByPosition(m_column, m_line + byteIndex);
          UnoRuntime.queryInterface(XPropertySet.class, xCell).setPropertyValue("CellStyle", OCTET_HEADING_STYLE);
          xCell.setValue(byteIndex);
        }
      }

      /* Left cell of merge area */
      left = bitIndex;
      
      /* Top cell of merge area (multi-line) */
      if(bitIndex == 0)
      {
        top = byteIndex;
      }
      else
      {
        top = -1;
      }

      /* Fill bits */
      for(int i = 0; i < bdmField.m_size.getValue(); i++)
      {
        if((i == 0) || (bitIndex == 0))
        {
          xCell = xSpreadsheet.getCellByPosition(m_column + 1 + bitIndex, m_line + byteIndex);
          UnoRuntime.queryInterface(XPropertySet.class, xCell).setPropertyValue("CellStyle", FIELD_STYLE);
          xCell.setFormula(bdmField.m_name.getValue());
        }

        currentShift++;
        bitIndex++;

        if(bitIndex >= 8)
        {
          /* Merge cells at octet boundary */

          /* Not a complete line */
          if(left != 0)
          {
            xCellRange = xSpreadsheet.getCellRangeByPosition(m_column + 1 + left, m_line + byteIndex, m_column + 8, m_line + byteIndex);
            UnoRuntime.queryInterface(XMergeable.class, xCellRange).merge(true);
          }
          /* End of multi-line merge area */
          else if(bdmField.m_size.getValue() - i <= 8)
          {
            xCellRange = xSpreadsheet.getCellRangeByPosition(m_column + 1, m_line + top, m_column + 8, m_line + byteIndex);
            UnoRuntime.queryInterface(XMergeable.class, xCellRange).merge(true);
          }

          bitIndex = 0;
          byteIndex++;

          /* Left cell of merge area */
          left = 0;

          /* Octet number */
          if(i < (bdmField.m_size.getValue() - 1))
          {
            xCell = xSpreadsheet.getCellByPosition(m_column, m_line + byteIndex);
            UnoRuntime.queryInterface(XPropertySet.class, xCell).setPropertyValue("CellStyle", OCTET_HEADING_STYLE);
            xCell.setValue(byteIndex);
          }
        }
      }

      /* Merge cells at end of field */
      if(bitIndex - left > 1)
      {
        xCellRange = xSpreadsheet.getCellRangeByPosition(m_column + 1 + left, m_line + byteIndex, m_column + bitIndex, m_line + byteIndex);
        UnoRuntime.queryInterface(com.sun.star.util.XMergeable.class, xCellRange).merge(true);
      }
    }

    /* Complete last line */
    while((bitIndex > 0) && (bitIndex < 8))
    {
      xCell = xSpreadsheet.getCellByPosition(m_column + 1 + bitIndex, m_line + byteIndex);
      UnoRuntime.queryInterface(XPropertySet.class, xCell).setPropertyValue("CellStyle", SPARE_STYLE);
      xCell.setFormula("-");

      currentShift++;
      bitIndex++;

      if(bitIndex >= 8)
      {
        bitIndex = 0;
        byteIndex++;
      }
    }

    /* Fixed-size frames */
    while(currentShift < minFrameSize)
    {
      /* Octet number */
      if(bitIndex == 0)
      {
        xCell = xSpreadsheet.getCellByPosition(m_column, m_line + byteIndex);
        UnoRuntime.queryInterface(XPropertySet.class, xCell).setPropertyValue("CellStyle", OCTET_HEADING_STYLE);
        xCell.setValue(byteIndex);
      }

      xCell = xSpreadsheet.getCellByPosition(m_column + 1 + bitIndex, m_line + byteIndex);
      UnoRuntime.queryInterface(XPropertySet.class, xCell).setPropertyValue("CellStyle", SPARE_STYLE);
      xCell.setFormula("-");

      currentShift++;
      bitIndex++;

      if(bitIndex >= 8)
      {
        bitIndex = 0;
        byteIndex++;
      }
    }

    m_line += byteIndex;
  }

}
