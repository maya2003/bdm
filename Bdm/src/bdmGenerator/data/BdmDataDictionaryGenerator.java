/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package bdmGenerator.data;

import java.io.IOException;
import java.io.Writer;

import bdmGenerator.BdmFileGenerator;
import bdmModel.BdmException;
import bdmModel.data.BdmDataDictionary;

public class BdmDataDictionaryGenerator
{
  BdmFileGenerator m_bdmFileGenerator;
  BdmDataDictionary m_bdmDataDictionary;

  protected final String m_fileName;


  public BdmDataDictionaryGenerator(BdmFileGenerator bdmFileGenerator, BdmDataDictionary bdmDataDictionary)
  {
    m_bdmFileGenerator  = bdmFileGenerator;
    m_bdmDataDictionary = bdmDataDictionary;

    m_fileName = "bdm_dictionary";
  }

  public void createHeaderFile() throws IOException, BdmException
  {
    Writer writer = m_bdmFileGenerator.getFile(m_fileName + ".h");

    writer.close();
  }

  public void createImplementationFile() throws IOException
  {
    Writer writer = m_bdmFileGenerator.getFile(m_fileName + ".c");

    writer.close();
  }

}

