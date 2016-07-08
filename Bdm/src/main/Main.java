/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package main;

import protocol.data_dictionary.generator.DataDictionaryGenerator;
import protocol.generator.BdmFileGenerator;
import protocol.generator.BdmProtocolGenerator;
import protocol.generator.PyCrcGenerator;
import protocol.layout.generator.ProtocolLayoutGenerator;
import protocol.model.BdmProtocol;
import protocol.parser.BdmCell;
import protocol.parser.BdmProtocolParser;

import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.sheet.XSpreadsheets;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.beans.PropertyValue;
import com.sun.star.container.XIndexAccess;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XModel;

import data_dictionary.generator.BdmDataDictionaryGenerator;
import data_dictionary.model.BdmDataDictionary;
import data_dictionary.parser.BdmDataDictionaryParser;

public class Main
{
  public static void main(String[] args) throws Exception
  {
    System.out.println("Connecting to LibreOffice...");
    XComponentContext xComponentContext = Bootstrap.bootstrap();
    Object desktopFrame = xComponentContext.getServiceManager().createInstanceWithContext("com.sun.star.frame.Desktop", xComponentContext);
    XComponentLoader xComponentLoader = (XComponentLoader)UnoRuntime.queryInterface(XComponentLoader.class, desktopFrame);

    StringBuilder specificationPath = new StringBuilder("file://").append(System.getProperty("user.dir")).append("/../documentation/input/specification.ods");
    System.out.print("Opening specification spreadsheet: ");
    System.out.print(specificationPath);
    System.out.println("...");

    try
    {
      /* Load the spreadsheet document */
      Object component = xComponentLoader.loadComponentFromURL(specificationPath.toString(), "_default", 0, new PropertyValue[0]);
      XModel xModel = (XModel)UnoRuntime.queryInterface(XModel.class, component);
      XSpreadsheetDocument xSpreadsheetDocument = (XSpreadsheetDocument)UnoRuntime.queryInterface(XSpreadsheetDocument.class, xModel);

      /* Get the sheet */
      XSpreadsheets xSpreadsheets = xSpreadsheetDocument.getSheets();
      XIndexAccess xIndexAccess = (XIndexAccess)UnoRuntime.queryInterface(XIndexAccess.class, xSpreadsheets);
      XSpreadsheet xSpreadsheet1 = (XSpreadsheet)UnoRuntime.queryInterface(XSpreadsheet.class, xIndexAccess.getByIndex(2));
      XSpreadsheet xSpreadsheet2 = (XSpreadsheet)UnoRuntime.queryInterface(XSpreadsheet.class, xIndexAccess.getByIndex(3));

      System.out.println("Parsing specification spreadsheet...");
      BdmProtocol bdmProtocol = new BdmProtocol();
      BdmCell bdmCell = new BdmCell(xSpreadsheet1, 1, 1);
      BdmProtocolParser bdmProtocolParser = new BdmProtocolParser(bdmProtocol, bdmCell);
      bdmProtocolParser.parse();

      System.out.println("Generating sample data dictionary...");
      DataDictionaryGenerator dataDictionaryGenerator = new DataDictionaryGenerator(xSpreadsheetDocument);
      dataDictionaryGenerator.fill(xSpreadsheet2, bdmProtocol);

      System.out.println("Parsing data dictionary spreadsheet...");
      BdmDataDictionary bdmDataDictionary = new BdmDataDictionary();
      bdmCell = new BdmCell(xSpreadsheet2, 3, 0);
      BdmDataDictionaryParser bdmDataDictionaryParser = new BdmDataDictionaryParser(bdmDataDictionary, bdmCell);
      bdmDataDictionaryParser.parse();

      System.out.println("Generating protocol...");
      BdmFileGenerator bdmFileGenerator = new BdmFileGenerator(System.getProperty("user.dir") + "/../generated_sources/");
      BdmProtocolGenerator bdmProtocolGenerator = new BdmProtocolGenerator(bdmFileGenerator, bdmProtocol);
      bdmProtocolGenerator.createHeaderFile();
      bdmProtocolGenerator.createImplementationFile();

      System.out.println("Generating data dictionary...");
      BdmDataDictionaryGenerator bdmDataDictionaryGenerator = new BdmDataDictionaryGenerator(bdmFileGenerator, bdmDataDictionary);
      bdmDataDictionaryGenerator.createHeaderFile();
      bdmDataDictionaryGenerator.createImplementationFile();

      //dictionaryHeaderFile.close();

      System.out.println("Generating protocol layout...");
      ProtocolLayoutGenerator protocolLayoutGenerator = new ProtocolLayoutGenerator(xSpreadsheetDocument);
      protocolLayoutGenerator.drawFrames(xSpreadsheet1, bdmProtocol);

      System.out.println("Generating CRC generator/checker...");
      new PyCrcGenerator().createCrcFiles();

      System.out.println("End.");
    }
    catch(Exception e)
    {
      System.out.println("Error has occurred!");
      e.printStackTrace();
    }

    // ???
    System.exit(0);
  }

}
