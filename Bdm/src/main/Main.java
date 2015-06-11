/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package main;

import java.io.Writer;

import unoGenerator.ProtocolLayoutGenerator;
import unoParser.BdmCell;
import unoParser.BdmProtocolParser;
import unoParser.data.BdmDataDictionaryParser;

import bdmGenerator.BdmFileGenerator;
import bdmGenerator.BdmProtocolGenerator;
import bdmGenerator.PyCrcGenerator;
import bdmModel.BdmProtocol;
import bdmModel.data.BdmDataDictionary;

import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.sheet.XSpreadsheets;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.beans.PropertyValue;
import com.sun.star.container.XIndexAccess;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XModel;

public class Main
{
  public static void main(String[] args) throws Exception
  {
    new PyCrcGenerator().createCrcFiles();

    System.out.println("Connecting to LibreOffice...");
    XComponentContext xComponentContext = com.sun.star.comp.helper.Bootstrap.bootstrap();
    Object desktopFrame = xComponentContext.getServiceManager().createInstanceWithContext("com.sun.star.frame.Desktop", xComponentContext);
    XComponentLoader xComponentLoader = (XComponentLoader)UnoRuntime.queryInterface(XComponentLoader.class, desktopFrame);

    StringBuilder specificationPath = new StringBuilder("file://").append(System.getProperty("user.dir")).append("/Specification/Specification.ods");
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
      XSpreadsheet xSpreadsheet1 = (XSpreadsheet)UnoRuntime.queryInterface(XSpreadsheet.class, xIndexAccess.getByIndex(1));
      XSpreadsheet xSpreadsheet2 = (XSpreadsheet)UnoRuntime.queryInterface(XSpreadsheet.class, xIndexAccess.getByIndex(3));

      System.out.println("Parsing specification spreadsheet...");
      BdmProtocol bdmProtocol = new BdmProtocol();
      BdmCell bdmCell = new BdmCell(xSpreadsheet1, 2, 1);
      BdmProtocolParser bdmProtocolParser = new BdmProtocolParser(bdmProtocol, bdmCell);
      bdmProtocolParser.parse();

      System.out.println("Parsing data dictionary spreadsheet...");
      BdmDataDictionary bdmDataDictionary = new BdmDataDictionary();
      bdmCell = new BdmCell(xSpreadsheet2, 3, 0);
      BdmDataDictionaryParser bdmDataDictionaryParser = new BdmDataDictionaryParser(bdmDataDictionary, bdmCell);
      bdmDataDictionaryParser.parse();

      System.out.println("Generating protocol...");
      BdmFileGenerator bdmFileGenerator = new BdmFileGenerator(System.getProperty("user.dir") + "/Generated sources/");
      BdmProtocolGenerator bdmProtocolGenerator = new BdmProtocolGenerator(bdmFileGenerator, bdmProtocol);
      bdmProtocolGenerator.createHeaderFile();
      bdmProtocolGenerator.createImplementationFile();

      System.out.println("Generating dictionary...");
      Writer dictionaryHeaderFile         = bdmFileGenerator.getFile("dictionary.h");
      Writer dictionaryImplementationFile = bdmFileGenerator.getFile("dictionary.c");
      dictionaryHeaderFile.append("/* <Copyright statement> */\n\n\n" + "/* This file has been generated using the BDM generator – https://sourceforge.net/projects/bdm-generator/. */\n\n");
      dictionaryImplementationFile.append("/* <Copyright statement> */\n\n\n" + "/* This file has been generated using the BDM generator – https://sourceforge.net/projects/bdm-generator/. */\n\n");
      dictionaryHeaderFile.close();
      dictionaryImplementationFile.close();

      System.out.println("Generating protocol layout...");
      ProtocolLayoutGenerator protocolLayoutGenerator = new ProtocolLayoutGenerator(xSpreadsheetDocument);
      protocolLayoutGenerator.drawFrames(xSpreadsheet1, bdmProtocol);

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
