/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package data_dictionary.parser;

import protocol.model.BdmException;
import protocol.parser.BdmCell;

import com.sun.star.lang.IndexOutOfBoundsException;

import data_dictionary.model.BdmData;
import data_dictionary.model.BdmDataDictionary;

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

int i = 3;

    m_bdmDataDictionary.setName("bdm");        //TODO
    m_bdmDataDictionary.setFilename("bdm_dd"); //TODO

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

