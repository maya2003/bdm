/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.
*/

package unoParser;

import bdmModel.BdmException;
import bdmModel.BdmFrame;
import bdmModel.BdmProtocol;
import bdmModel.BdmStringAttribute;

import com.sun.star.lang.IndexOutOfBoundsException;

public class BdmProtocolParser
{
  protected BdmProtocol m_bdmProtocol;
  protected BdmCell     m_bdmCell;

  protected final BdmStringAttributeParser m_name;

  public static boolean isFrame(BdmCell bdmCell) throws IndexOutOfBoundsException
  {
    return bdmCell.getCell().getFormula().equals(BdmProtocol.PROTOCOL_MARKER);
  }

  public BdmProtocolParser(BdmProtocol bdmProtocol, BdmCell bdmCell) throws IndexOutOfBoundsException
  {
    m_bdmProtocol = bdmProtocol;
    m_bdmCell     = bdmCell;

    m_name = new BdmStringAttributeParser(bdmProtocol.m_name, bdmCell.getCell(3, 1), bdmCell.getCell());
  }

  public void parse() throws BdmException, IndexOutOfBoundsException
  {
    BdmFrame       bdmFrame;
    BdmFrameParser bdmFrameParser;

    m_name.parse();
    System.out.println(((BdmStringAttribute)m_name.m_bdmAttribute).getValue());

    m_bdmCell.getCell(4, -1);

    try
    {
      while(BdmFrameParser.isFrame(m_bdmCell))
      {
        bdmFrame = new BdmFrame();
        bdmFrameParser = new BdmFrameParser(bdmFrame, m_bdmCell);
        bdmFrameParser.parse();

        m_bdmProtocol.frames.add(bdmFrame);
      }
    }
    catch(BdmException e)
    {
    }
  }

}
