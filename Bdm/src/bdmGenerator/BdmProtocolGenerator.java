/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
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
  BdmFileGenerator m_bdmFileGenerator;
  BdmProtocol m_bdmProtocol;

  protected final String m_fileName;
  protected final String m_nameUpperCamel;
  protected final String m_fullNameUpper;

  protected BdmMethodGenerator m_bdmMethodGenerator;
  protected final List<BdmFrameGenerator> bdmFrameGenerators;

  public BdmProtocolGenerator(BdmFileGenerator bdmFileGenerator, BdmProtocol bdmProtocol)
  {
    m_bdmFileGenerator = bdmFileGenerator;
    m_bdmProtocol = bdmProtocol;

    BdmCaseFormat bdmCaseFormat = new BdmCaseFormat(m_bdmProtocol.m_name.getValue());
    m_fileName       = bdmCaseFormat.toFileName();
    m_nameUpperCamel = bdmCaseFormat.toUpperCamel();
    m_fullNameUpper  = bdmCaseFormat.toUpper();

    m_bdmMethodGenerator = new BdmMethodGenerator("void", new StringBuilder(getNameUpperCamel())
      .append("_frameReceived").toString());
    m_bdmMethodGenerator.addParameter(bdmProtocol.m_frameTypeContainer.getValue(), "frameId");
    m_bdmMethodGenerator.addParameter("u8 *", "frame");

    bdmFrameGenerators = new ArrayList<BdmFrameGenerator>();
    for(BdmFrame bdmFrame: m_bdmProtocol.frames)
    {
      bdmFrameGenerators.add(new BdmFrameGenerator(this, bdmFrame));
    }
  }

  /** "sample_protocol" */
  public String getFileName()
  {
    return m_fileName;
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


  public void createHeaderFile() throws IOException, BdmException
  {
    Writer writer = m_bdmFileGenerator.getFile(m_fileName + ".h");
    appendHeader(writer);
    writer.append(m_bdmProtocol.m_basicTypesInclude.getValue()); writer.append("\n");
    writer.append('\n');
    appendFrameStructureDefinition(writer);
    appendFrameIdDefinition(writer);
    appendFieldEnumsDefinition(writer);
    appendMethodsDeclaration(writer);
    writer.close();
  }

  public void createImplementationFile() throws IOException
  {
    Writer writer = m_bdmFileGenerator.getFile(m_fileName + ".c");
    appendHeader(writer);
    writer.append("#include \""); writer.append(m_fileName); writer.append(".h\"\n");
    writer.append('\n');
    appendFrameTypeSwitchDefinition(writer);
    appendCheckFramesContent(writer);
    writer.close();
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

  public void appendFrameIdDefinition(Writer writer) throws IOException, BdmException
  {
    StringBuilder s = new StringBuilder();

    s.append("typedef enum\n" +
             "{\n");

    for(BdmFrameGenerator bdmFrameGenerator: bdmFrameGenerators)
    {
      bdmFrameGenerator.appendFrameIdDefinition(s);
    }

    s.append("} " + getNameUpperCamel() + "_FrameId;\n\n");

    writer.append(s.toString());
  }

  public void appendFieldEnumsDefinition(Writer writer) throws IOException
  {
    StringBuilder s = new StringBuilder();

    for(BdmFrameGenerator bdmFrameGenerator: bdmFrameGenerators)
    {
      bdmFrameGenerator.appendFieldEnumsDefinition(s);
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
             "  switch(frameId)\n" +
             "  {\n");

    for(BdmFrameGenerator bdmFrameGenerator: bdmFrameGenerators)
    {
      bdmFrameGenerator.appendFrameTypeCase(s);
    }

    s.append("    default:\n" +
             "    {\n" +
             "      /* Unknown frame received! */\n" +
             "      break;\n" +
             "    }\n" +

             "  }\n" +
             "\n" +
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
