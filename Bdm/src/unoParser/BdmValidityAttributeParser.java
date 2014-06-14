/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package unoParser;

import bdmModel.BdmEnumValue;
import bdmModel.BdmException;
import bdmModel.BdmRange;
import bdmModel.BdmSet;
import bdmModel.BdmValidityAttribute;

import com.sun.star.table.XCell;

public class BdmValidityAttributeParser extends BdmAttributeParser
{
  public BdmValidityAttributeParser(BdmValidityAttribute bdmValidityAttribute, XCell cell, XCell errorCell)
  {
    super(bdmValidityAttribute, cell, errorCell);

    m_type = BdmFieldType.ftInteger;
  }

  @Override
  public void doParse() throws BdmException
  {
    // TODO Review parsing...
    // TODO Only min, only max, several ranges...

    if(m_rawValue.charAt(0) == '[')
    {
      if(m_rawValue.charAt(m_rawValue.length() - 1) != ']')
      {
        System.err.println("Range not terminated!");
        throw new BdmException();
      }

      String delim = ", ";
      String[] enums = m_rawValue.substring(1, m_rawValue.length() - 1).split(delim);

      if(enums.length != 2)
      {
        System.err.println("Unmanaged parsing error!");
        throw new BdmException();
      }

      BdmEnumValue lowerBound;
      BdmEnumValue upperBound;

      delim = " = ";
      String []lowerBoundData = enums[0].split(delim);
      String []upperBoundData = enums[1].split(delim);

      if(lowerBoundData.length == 1)
      {
        lowerBound = new BdmEnumValue(lowerBoundData[0]);
      }
      else if(lowerBoundData.length == 2)
      {
        lowerBound = new BdmEnumValue(lowerBoundData[0], Long.parseLong(lowerBoundData[1]));
      }
      else
      {
        System.err.println("Unmanaged parsing error!");
        throw new BdmException();
      }

      if(upperBoundData.length == 1)
      {
        upperBound = new BdmEnumValue(upperBoundData[0]);
      }
      else if(upperBoundData.length == 2)
      {
        upperBound = new BdmEnumValue(upperBoundData[0], Long.parseLong(upperBoundData[1]));
      }
      else
      {
        System.err.println("Unmanaged parsing error!");
        throw new BdmException();
      }

      ((BdmValidityAttribute)m_bdmAttribute).setRange(new BdmRange(lowerBound, upperBound));
    }
    else
    {
      String delim = ", ";
      String[] enums = m_rawValue.split(delim);

      ((BdmValidityAttribute)m_bdmAttribute).setSet(new BdmSet());

      for(String key: enums)
      {
        BdmEnumValue enumValue;

        delim = " = ";
        String []enumValueData = key.split(delim);

        if(enumValueData.length == 1)
        {
          enumValue = new BdmEnumValue(enumValueData[0]);
        }
        else if(enumValueData.length == 2)
        {
          enumValue = new BdmEnumValue(enumValueData[0], Long.parseLong(enumValueData[1]));
        }
        else
        {
          System.err.println("Unmanaged parsing error!");
          throw new BdmException();
        }

        ((BdmValidityAttribute)m_bdmAttribute).getSet().add(enumValue);
      }
    }
  }

}
