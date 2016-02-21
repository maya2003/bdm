/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package bdmModel.data;

import java.util.ArrayList;
import java.util.List;

import bdmModel.BdmException;

public class BdmDataDictionary
{
  protected String m_name;
  protected String m_filename;
  public final List<BdmData> data;

  public BdmDataDictionary() throws BdmException //TODO !!
  {
    data = new ArrayList<BdmData>();
  }

  public String getName()
  {
    return m_name;
  }

  public void setName(String name)
  {
    m_name = name;
  }

  public String getFilename()
  {
    return m_filename;
  }

  public void setFilename(String filename)
  {
    m_filename = filename;
  }

}
