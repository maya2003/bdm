/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package main;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import unoParser.BdmCell;
import unoParser.BdmProtocolParser;

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

      StringBuilder protocolHeader           = new StringBuilder(System.getProperty("user.dir")).append("/Generated sources/protocol.h");
      StringBuilder protocolImplementation   = new StringBuilder(System.getProperty("user.dir")).append("/Generated sources/protocol.c");
      StringBuilder dictionaryHeader         = new StringBuilder(System.getProperty("user.dir")).append("/Generated sources/dictionary.h");
      StringBuilder dictionaryImplementation = new StringBuilder(System.getProperty("user.dir")).append("/Generated sources/dictionary.c");

      System.out.print("Protocol header:           ");
      System.out.println(protocolHeader);
      System.out.print("Protocol implementation:   ");
      System.out.println(protocolImplementation);
      System.out.print("Dictionary header:         ");
      System.out.println(dictionaryHeader);
      System.out.print("Dictionary implementation: ");
      System.out.println(dictionaryImplementation);

      Writer protocolHeaderFile           = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(protocolHeader.toString()),           "UTF-8"));
      Writer protocolImplementationFile   = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(protocolImplementation.toString()),   "UTF-8"));
      Writer dictionaryHeaderFile         = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dictionaryHeader.toString()),         "UTF-8"));
      Writer dictionaryImplementationFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dictionaryImplementation.toString()), "UTF-8"));

      bdmGenerator.BdmProtocolGenerator pr = new bdmGenerator.BdmProtocolGenerator(bdmProtocol);

      pr.createHeaderFile(protocolHeaderFile);
      pr.createImplementationFile(protocolImplementationFile);

      dictionaryHeaderFile.append("/* <Copyright statement> */\n\n\n" + "/* This file has been generated using the BDM generator – https://sourceforge.net/projects/bdm-generator/. */\n\n");
      dictionaryImplementationFile.append("/* <Copyright statement> */\n\n\n" + "/* This file has been generated using the BDM generator – https://sourceforge.net/projects/bdm-generator/. */\n\n");
      dictionaryHeaderFile.append("/* end of file */\n");
      dictionaryImplementationFile.append("/* end of file */\n");

      protocolHeaderFile.close();
      protocolImplementationFile.close();
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
