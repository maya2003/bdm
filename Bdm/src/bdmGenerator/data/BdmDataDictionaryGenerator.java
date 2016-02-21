/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package bdmGenerator.data;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import bdmGenerator.BdmCaseFormat;
import bdmGenerator.BdmFileGenerator;
import bdmModel.BdmException;
import bdmModel.data.BdmData;
import bdmModel.data.BdmDataDictionary;

public class BdmDataDictionaryGenerator
{
  BdmFileGenerator m_bdmFileGenerator;
  BdmDataDictionary m_bdmDataDictionary;

  protected final String m_fileName;
  protected final String m_nameLowerCamel;
  protected final String m_nameUpperCamel;
  protected final String m_fullNameUpper;

  protected final List<BdmDataGenerator> bdmDataGenerators;

  public BdmDataDictionaryGenerator(BdmFileGenerator bdmFileGenerator, BdmDataDictionary bdmDataDictionary)
  {
    m_bdmFileGenerator  = bdmFileGenerator;
    m_bdmDataDictionary = bdmDataDictionary;

    m_fileName       = bdmDataDictionary.getFilename();

    BdmCaseFormat bdmCaseFormat = new BdmCaseFormat(bdmDataDictionary.getName());
    m_nameLowerCamel = bdmCaseFormat.toLowerCamel();
    m_nameUpperCamel = bdmCaseFormat.toUpperCamel();
    //m_fullNameUpper  = bdmCaseFormat.toUpper();
    m_fullNameUpper = new BdmCaseFormat(bdmDataDictionary.getFilename()).toUpper();

    bdmDataGenerators = new ArrayList<BdmDataGenerator>();
    for(BdmData bdmData: m_bdmDataDictionary.data)
    {
      bdmDataGenerators.add(new BdmDataGenerator(this, bdmData));
    }
  }

  /** "bdm_dictionary" */
  public String getFileName()
  {
    return m_fileName;
  }

  /** "bdmDictionary" */
  public String getName()
  {
    return m_nameLowerCamel;
  }

  /** "BdmDictionary" */
  public String getNameUpperCamel()
  {
    return m_nameUpperCamel;
  }

  /** "BDM_DICTIONARY" */
  public String getNameUpper()
  {
    return m_fullNameUpper;
  }


  public void createHeaderFile() throws IOException, BdmException
  {
    Writer writer = m_bdmFileGenerator.getFile(m_fileName + ".h");
    appendHeader(writer);
    writer.append("#ifndef __"); writer.append(getNameUpper()); writer.append("_H__\n\n");
    writer.append("#define __"); writer.append(getNameUpper()); writer.append("_H__\n\n");
    writer.append("#include \"bdm.h\"\n\n#ifdef __cplusplus\nextern \"C\"\n{\n#endif /* __cplusplus */\n\n");
    appendDataContainerDeclaration(writer);
    writer.append("#ifdef __cplusplus\n}\n#endif /* __cplusplus */\n\n");
    writer.append("#endif /* __"); writer.append(getNameUpper()); writer.append("_H__ */\n\n");
    writer.close();
  }

  public void createImplementationFile() throws IOException
  {
    Writer writer = m_bdmFileGenerator.getFile(m_fileName + ".c");
    appendHeader(writer);
    writer.append("#include \""); writer.append(m_fileName); writer.append(".h\"\n\n");
    appendDataContainerDefinition(writer);
    writer.close();
  }

  public void appendHeader(Writer writer) throws IOException
  {
    StringBuilder s = new StringBuilder();

    //s.append(m_bdmProtocol.m_copyrightNotice.getValue());
    s.append("\n\n/* This file has been generated using the BDM generator – https://github.com/maya2003/bdm */\n\n");

    writer.append(s.toString());
  }

  public void appendDataContainerDeclaration(Writer writer) throws IOException
  {
    StringBuilder s = new StringBuilder();

    for(BdmDataGenerator bdmDataGenerator: bdmDataGenerators)
    {
      bdmDataGenerator.appendFrameStructureDeclaration(s);
    }
    s.append('\n');

    writer.append(s.toString());
  }

  public void appendDataContainerDefinition(Writer writer) throws IOException
  {
    StringBuilder s = new StringBuilder();

    for(BdmDataGenerator bdmDataGenerator: bdmDataGenerators)
    {
      bdmDataGenerator.appendFrameStructureDefinition(s);
    }
    s.append('\n');

    writer.append(s.toString());
  }

}

