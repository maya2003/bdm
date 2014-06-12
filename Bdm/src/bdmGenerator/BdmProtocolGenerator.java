/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package bdmGenerator;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import bdmModel.BdmException;
import bdmModel.BdmFrame;
import bdmModel.BdmProtocol;

public class BdmProtocolGenerator
{
  BdmProtocol m_bdmProtocol;

  protected final String m_nameUpperCamel;
  protected final String m_fullNameUpper;

  protected BdmMethodGenerator m_bdmMethodGenerator;
  protected final List<BdmFrameGenerator> bdmFrameGenerators;

  public BdmProtocolGenerator(BdmProtocol bdmProtocol)
  {
    m_bdmProtocol = bdmProtocol;

    BdmCaseFormat bdmCaseFormat = new BdmCaseFormat(m_bdmProtocol.m_name.getValue());
    m_nameUpperCamel = bdmCaseFormat.toUpperCamel();
    m_fullNameUpper  = bdmCaseFormat.toUpper();

    m_bdmMethodGenerator = new BdmMethodGenerator("bool", new StringBuilder(getNameUpperCamel())
      .append("_frameReceived").toString());
    m_bdmMethodGenerator.addParameter(bdmProtocol.m_frameTypeContainer.getValue(), "frameId");
    m_bdmMethodGenerator.addParameter("u8 *", "frame");

    bdmFrameGenerators = new ArrayList<BdmFrameGenerator>();
    for(BdmFrame bdmFrame: m_bdmProtocol.frames)
    {
      bdmFrameGenerators.add(new BdmFrameGenerator(this, bdmFrame));
    }
  }

  /** "sampleProtocol" */
  public String getName()
  {
    return m_bdmProtocol.m_name.getValue();
  }

  /** "SampleProtocol" */
  public String getNameUpperCamel()
  {
    return m_nameUpperCamel;
  }

  /** "SAMPLE_PROTOCOL" */
  public String getNameUpper()
  {
    return m_fullNameUpper;
  }


  public void createHeaderFile(Writer writer) throws IOException
  {
    appendHeader(writer);
    appendFrameStructureDefinition(writer);
    appendMethodsDeclaration(writer);
    writer.append("/* end of file */\n");
  }

  public void createImplementationFile(Writer writer) throws IOException
  {
    appendHeader(writer);
    writer.append("#include \""); writer.append(m_bdmProtocol.m_name.getValue()); writer.append(".h\"\n\n");
    appendFrameTypeSwitchDefinition(writer);
    appendCheckFramesContent(writer);
    writer.append("/* end of file */\n");
  }

  public void appendHeader(Writer writer) throws IOException
  {
    StringBuilder s = new StringBuilder();

    s.append(m_bdmProtocol.m_copyrightNotice.getValue());
    s.append("\n\n/* This file has been generated using the BDM generator – https://sourceforge.net/projects/bdm-generator/. */\n\n");

    writer.append(s.toString());
  }

  public void appendFrameStructureDefinition(Writer writer) throws IOException
  {
    StringBuilder s = new StringBuilder();

    try
    {
      for(BdmFrameGenerator bdmFrameGenerator: bdmFrameGenerators)
      {
        bdmFrameGenerator.appendFrameStructureDefinition(s);
      }
    }
    catch (BdmException e)
    {
    }
    
    writer.append(s.toString());
  }

  public void appendMethodsDeclaration(Writer writer) throws IOException
  {
    StringBuilder s = new StringBuilder();

    m_bdmMethodGenerator.appendMethodDeclaration(s);
    for(BdmFrameGenerator bdmFrameGenerator: bdmFrameGenerators)
    {
      bdmFrameGenerator.appendFrameCheckContentDeclaration(s);
    }

    writer.append(s.toString());
  }

  public void appendFrameTypeSwitchDefinition(Writer writer) throws IOException
  {
    StringBuilder s = new StringBuilder();

    m_bdmMethodGenerator.appendMethodDefinition(s);
    s.append("{\n" +
             "  bool result;\n" +
             '\n' +
             "  switch(frameId)\n" +
             "  {\n");
    for(BdmFrameGenerator bdmFrameGenerator: bdmFrameGenerators)
    {
      bdmFrameGenerator.appendFrameTypeCase(s);
    }

    s.append("    default:\n" +
             "    {\n" +
             "      /* Unknown frame received! */\n" +
             "      result = false;\n" +
             "      break;\n" +
             "    }\n" +

             "  }\n" +
             "\n" +
             "  return result;\n" +
             "}\n\n");

    writer.append(s.toString());
  }

  public void appendCheckFramesContent(Writer writer) throws IOException
  {
    StringBuilder s = new StringBuilder();

    for(BdmFrameGenerator bdmFrameGenerator: bdmFrameGenerators)
    {
      bdmFrameGenerator.appendCheckFrameContent(s);
    }

    writer.append(s.toString());
  }

}
