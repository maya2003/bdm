/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package unoParser.data;

import unoParser.BdmCell;
import unoParser.BdmStringAttributeParser;

import com.sun.star.lang.IndexOutOfBoundsException;

import bdmModel.BdmException;
import bdmModel.data.BdmData;

public class BdmDataParser
{
  public final BdmStringAttributeParser m_requirementId;
  public final BdmStringAttributeParser m_coveredRequirements;
  public final BdmStringAttributeParser m_name;
  public final BdmStringAttributeParser m_type;
  public final BdmStringAttributeParser m_cType;

  public BdmDataParser(BdmData bdmData, BdmCell bdmCell) throws IndexOutOfBoundsException
  {
    m_requirementId       = new BdmStringAttributeParser(bdmData.m_requirementId,       bdmCell.getCell(), bdmCell.getCell());
    m_coveredRequirements = new BdmStringAttributeParser(bdmData.m_coveredRequirements, bdmCell.getCell(0, 1), bdmCell.getCell());
    m_name                = new BdmStringAttributeParser(bdmData.m_name,                bdmCell.getCell(0, 5), bdmCell.getCell());
    m_type                = new BdmStringAttributeParser(bdmData.m_type,                bdmCell.getCell(0, 1), bdmCell.getCell());
    m_cType               = new BdmStringAttributeParser(bdmData.m_cType,               bdmCell.getCell(0, 1), bdmCell.getCell());
    bdmCell.getCell(1, -8);
  }

  public void parse() throws BdmException
  {
    m_requirementId.parse();
    m_coveredRequirements.parse();
    m_name.parse();
    m_type.parse();
    m_cType.parse();
  }

}

