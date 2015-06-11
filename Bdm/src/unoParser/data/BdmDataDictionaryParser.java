/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package unoParser.data;

import unoParser.BdmCell;
import bdmModel.BdmException;
import bdmModel.data.BdmData;
import bdmModel.data.BdmDataDictionary;

import com.sun.star.lang.IndexOutOfBoundsException;

public class BdmDataDictionaryParser
{
  protected BdmDataDictionary m_bdmDataDictionary;
  protected BdmCell m_bdmCell;

  public BdmDataDictionaryParser(BdmDataDictionary bdmDataDictionary, BdmCell bdmCell) throws IndexOutOfBoundsException
  {
    m_bdmDataDictionary = bdmDataDictionary;
    m_bdmCell = bdmCell;
  }

  public void parse() throws BdmException, IndexOutOfBoundsException
  {
    BdmData bdmData;
    BdmDataParser bdmDataParser;

int i = 5;

    try
    {
      while(i-- > 0)
      {
        bdmData = new BdmData();
        bdmDataParser = new BdmDataParser(bdmData, m_bdmCell);
        bdmDataParser.parse();
        
        m_bdmDataDictionary.data.add(bdmData);
      }
    }
    catch(BdmException e)
    {
    }

  }

}

