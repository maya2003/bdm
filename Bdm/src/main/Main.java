/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package main;

import java.io.Writer;
import unoParser.BdmCell;
import unoParser.BdmProtocolParser;

import bdmGenerator.BdmFileGenerator;
import bdmGenerator.BdmProtocolGenerator;
import bdmModel.BdmProtocol;

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
      XSpreadsheet xSheet = (XSpreadsheet)UnoRuntime.queryInterface(XSpreadsheet.class, xIndexAccess.getByIndex(1));

      System.out.println("Parsing specification spreadsheet...");
      BdmProtocol bdmProtocol = new bdmModel.BdmProtocol();
      BdmCell bdmCell = new BdmCell(xSheet, 2, 1);
      BdmProtocolParser bdmProtocolParser = new BdmProtocolParser(bdmProtocol, bdmCell);
      bdmProtocolParser.parse();

      System.out.println("Generating protocol and dictionary...");
      BdmFileGenerator bdmFileGenerator = new BdmFileGenerator(System.getProperty("user.dir") + "/Generated sources/");
      BdmProtocolGenerator bdmProtocolGenerator = new BdmProtocolGenerator(bdmFileGenerator, bdmProtocol);
      bdmProtocolGenerator.createHeaderFile();
      bdmProtocolGenerator.createImplementationFile();

      Writer dictionaryHeaderFile         = bdmFileGenerator.getFile("dictionary.h");
      Writer dictionaryImplementationFile = bdmFileGenerator.getFile("dictionary.c");
      dictionaryHeaderFile.append("/* <Copyright statement> */\n\n\n" + "/* This file has been generated using the BDM generator – https://sourceforge.net/projects/bdm-generator/. */\n\n");
      dictionaryImplementationFile.append("/* <Copyright statement> */\n\n\n" + "/* This file has been generated using the BDM generator – https://sourceforge.net/projects/bdm-generator/. */\n\n");
      dictionaryHeaderFile.close();
      dictionaryImplementationFile.close();

      System.out.println("End");
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
