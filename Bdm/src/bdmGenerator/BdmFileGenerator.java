/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
   See the file COPYING for copying permission.

   https://github.com/maya2003/bdm
*/

// TODO: create folders if needed

package bdmGenerator;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class BdmFileGenerator
{
  protected String m_rootPath;

  public BdmFileGenerator(String rootPath)
  {
    m_rootPath = rootPath;
  }

  public Writer getFile(String fileName) throws UnsupportedEncodingException, FileNotFoundException
  {
    String path = m_rootPath + fileName;
    System.out.print("Creating file: ");
    System.out.println(path);
    return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(m_rootPath + fileName), "UTF-8"));
  }

}
