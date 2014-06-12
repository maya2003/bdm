/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package unoParser;

import unoParser.BdmCell;
import unoParser.BdmFieldParser;

import bdmModel.BdmException;
import bdmModel.BdmField;
import bdmModel.BdmFrame;
import bdmModel.BdmStringAttribute;

import com.sun.star.lang.IndexOutOfBoundsException;

public class BdmFrameParser
{
  protected BdmFrame m_bdmFrame;
  protected BdmCell  m_bdmCell;

  protected final BdmStringAttributeParser m_name;

  public static boolean isFrame(BdmCell bdmCell) throws IndexOutOfBoundsException
  {
    return bdmCell.getCell().getFormula().equals(BdmFrame.FRAME_MARKER);
  }

  public BdmFrameParser(BdmFrame bdmFrame, BdmCell bdmCell) throws IndexOutOfBoundsException
  {
    m_bdmFrame = bdmFrame;
    m_bdmCell  = bdmCell;

    m_name = new BdmStringAttributeParser(bdmFrame.m_name, bdmCell.getCell(3, 1), bdmCell.getCell());
  }

  public void parse() throws BdmException, IndexOutOfBoundsException
  {
    BdmField bdmField;
    BdmFieldParser bdmFieldParser;

    m_name.parse();
    System.out.print("  ");
    System.out.println(((BdmStringAttribute)m_name.m_bdmAttribute).getValue());

    m_bdmCell.getCell(3, -1);

    try
    {
      while(BdmFieldParser.isField(m_bdmCell))
      {
        bdmField = new BdmField();
        bdmFieldParser = new BdmFieldParser(bdmField, m_bdmCell);
        bdmFieldParser.parse();
        
        m_bdmFrame.fields.add(bdmField);
      }
    }
    catch(BdmException e)
    {
    }
    
    m_bdmCell.getCell(0, 0);
  }

}
