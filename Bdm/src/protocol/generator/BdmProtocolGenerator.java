/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package protocol.generator;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import protocol.model.BdmException;
import protocol.model.BdmFrame;
import protocol.model.BdmProtocol;

public class BdmProtocolGenerator
{
  BdmFileGenerator m_bdmFileGenerator;
  BdmProtocol m_bdmProtocol;

  protected final String m_fileName;
  protected final String m_nameUpperCamel;
  protected final String m_fullNameUpper;

  protected BdmMethodGenerator m_getFrameSizeMethod;
  protected BdmMethodGenerator m_frameTypeSwitchMethod;
  protected final List<BdmFrameGenerator> bdmFrameGenerators;

  public BdmProtocolGenerator(BdmFileGenerator bdmFileGenerator, BdmProtocol bdmProtocol)
  {
    m_bdmFileGenerator = bdmFileGenerator;
    m_bdmProtocol = bdmProtocol;

    BdmCaseFormat bdmCaseFormat = new BdmCaseFormat(m_bdmProtocol.m_name.getValue());
    m_fileName       = bdmCaseFormat.toFileName();
    m_nameUpperCamel = bdmCaseFormat.toUpperCamel();
    m_fullNameUpper  = bdmCaseFormat.toUpper();

    m_getFrameSizeMethod = new BdmMethodGenerator("bool", new StringBuilder(getNameUpperCamel())
      .append("_getFrameSize").toString());
    m_getFrameSizeMethod.addParameter("size_t *", "size");
    m_getFrameSizeMethod.addParameter("u8", "frameId");

    m_frameTypeSwitchMethod = new BdmMethodGenerator("void", new StringBuilder(getNameUpperCamel())
      .append("_frameReceived").toString());
    m_frameTypeSwitchMethod.addParameter("Bdm_ProtocolContext *", "context");
    m_frameTypeSwitchMethod.addParameter(bdmProtocol.m_frameTypeContainer.getValue(), "frameId");
    m_frameTypeSwitchMethod.addParameter("u8 *", "frame");

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
    Writer writer = m_bdmFileGenerator.getFile(getFileName() + "_frames.h");
    appendHeader(writer);
    writer.append("#ifndef __"); writer.append(getNameUpper()); writer.append("_FRAMES_H__\n");
    writer.append("#define __"); writer.append(getNameUpper()); writer.append("_FRAMES_H__\n\n");
    //TODO: If not empty
    writer.append("#include \"bdm.h\"\n\n#ifdef __cplusplus\nextern \"C\"\n{\n#endif /* __cplusplus */\n\n");
    appendFrameStructureDefinition(writer);
    appendFrameIdDefinition(writer);
    appendFieldEnumsDefinition(writer);
    appendMethodsDeclaration(writer);
    writer.append("#ifdef __cplusplus\n}\n#endif /* __cplusplus */\n\n");
    writer.append("#endif /* __"); writer.append(getNameUpper()); writer.append("_FRAMES_H__ */\n\n");
    writer.close();
  }

  public void createImplementationFile() throws IOException
  {
    Writer writer = m_bdmFileGenerator.getFile(getFileName() + "_frames.c");
    appendHeader(writer);
    writer.append("#include <stdio.h>\n\n"); // TODO
    writer.append("#include \""); writer.append(m_fileName); writer.append("_frames.h\"\n\n");
    appendFrameSizes(writer);
    appendFrameTypeSwitchDefinition(writer);
    appendCheckFramesContent(writer);
    appendBuildFrames(writer);
    writer.close();
  }

  public void appendHeader(Writer writer) throws IOException
  {
    StringBuilder s = new StringBuilder();

    s.append(m_bdmProtocol.m_copyrightNotice.getValue());
    s.append("\n\n/* This file has been generated using the BDM generator – https://github.com/maya2003/bdm */\n\n");

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

    m_getFrameSizeMethod.appendMethodDeclaration(s);

    m_frameTypeSwitchMethod.appendMethodDeclaration(s);

    for(BdmFrameGenerator bdmFrameGenerator: bdmFrameGenerators)
    {
      bdmFrameGenerator.appendFrameCheckContentDeclaration(s);
    }

    for(BdmFrameGenerator bdmFrameGenerator: bdmFrameGenerators)
    {
      bdmFrameGenerator.appendSendFrameDeclaration(s);
    }

    writer.append(s.toString());
  }

  public void appendFrameSizes(Writer writer) throws IOException
  {
    StringBuilder s = new StringBuilder();

    m_getFrameSizeMethod.appendMethodDefinition(s);
    s.append("{\n" +
        "  switch(frameId)\n" +
        "  {\n");

    for(BdmFrameGenerator bdmFrameGenerator: bdmFrameGenerators)
    {
      bdmFrameGenerator.appendFrameSize(s);
    }

    s.append("    default:\n" +
      "    {\n" +
      "      /* Unknown frameId! */\n" +
      "      return false;\n" +
      "      break;\n" +
      "    }\n" +
      "  }\n" +
      "\n" +
      "  return true;\n" +
      "}\n\n");

    writer.append(s.toString());
  }

  public void appendFrameTypeSwitchDefinition(Writer writer) throws IOException
  {
    StringBuilder s = new StringBuilder();

    m_frameTypeSwitchMethod.appendMethodDefinition(s);
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

  public void appendBuildFrames(Writer writer) throws IOException
  {
    StringBuilder s = new StringBuilder();

    for(BdmFrameGenerator bdmFrameGenerator: bdmFrameGenerators)
    {
      bdmFrameGenerator.appendSendFrame(s);
    }

    writer.append(s.toString());
  }

}
