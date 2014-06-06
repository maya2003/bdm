/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.
*/

package main;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import unoParser.BdmCell;
import unoParser.BdmProtocolParser;

import bdmGenerator.BdmFieldGenerator;
import bdmGenerator.BdmFrameGenerator;

import com.sun.star.lang.XMultiComponentFactory;
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
    XComponentContext xContext = com.sun.star.comp.helper.Bootstrap.bootstrap();
    System.out.println("Connected to LibreOffice...");

    XMultiComponentFactory multiComponentFactory = xContext.getServiceManager();
    Object desktopFrame = multiComponentFactory.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);
    XComponentLoader componentloader = (XComponentLoader)UnoRuntime.queryInterface(XComponentLoader.class, desktopFrame);

    XSpreadsheetDocument spreadsheet = null;

    String specificationPath = "file://" + System.getProperty("user.dir") + "/Specification/Specification.ods";
    String outFilePath = System.getProperty("user.dir") + "/Generated sources/protocol.c";
    System.out.println(specificationPath);
    System.out.println(outFilePath);

    /* Load the spreadsheet */
    try
    {
      Object component = componentloader.loadComponentFromURL(specificationPath, "_default", 0, new PropertyValue[0]);

      System.out.println("BDM spreadsheet open...");

      /* Get the active spreadsheet of the component */
      XModel model = (XModel)UnoRuntime.queryInterface(XModel.class, component);
      spreadsheet = (XSpreadsheetDocument)UnoRuntime.queryInterface(XSpreadsheetDocument.class, model);
    } catch(Exception e) {}

    try
    {
      XSpreadsheets xSheets = spreadsheet.getSheets();
      XIndexAccess oIndexSheets = (XIndexAccess)UnoRuntime.queryInterface(XIndexAccess.class, xSheets);
      XSpreadsheet xSheet = (XSpreadsheet)UnoRuntime.queryInterface(XSpreadsheet.class, oIndexSheets.getByIndex(1));

      Writer outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFilePath), "UTF-8"));

      bdmModel.BdmProtocol bdmProtocol = new bdmModel.BdmProtocol();
      BdmCell bdmCell = new BdmCell(xSheet, 2, 1);
      BdmProtocolParser bdmProtocolParser = new BdmProtocolParser(bdmProtocol, bdmCell);
      bdmProtocolParser.parse();

      StringBuilder s2 = new StringBuilder();
      bdmGenerator.BdmProtocolGenerator pr = new bdmGenerator.BdmProtocolGenerator(bdmProtocol);
      BdmFrameGenerator fr = new BdmFrameGenerator(pr, bdmProtocol.frames.get(0));
      BdmFieldGenerator fi = new BdmFieldGenerator(fr, bdmProtocol.frames.get(0).fields.get(0));
      fi.appendCheckFieldsBounds(s2);
      s2.append("----------------\n");
      fr.appendFrameStructureDefinition(s2);
      fr.appendCheckFieldsBounds(s2);
      fr.appendCheckSpares(s2);
      System.out.println(s2.toString());

      pr.appendFrameStructureDefinition(outFile);
      pr.appendFrameTypeSwitch(outFile);

      outFile.close();
    }
    catch(Exception e)
    {
    }

    System.out.println("\nEnd.");

    // ???
    System.exit(0);
  }

}
