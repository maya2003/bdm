/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package protocol.parser;

import protocol.model.BdmException;
import protocol.model.BdmField;
import protocol.model.BdmFrame;
import protocol.model.BdmStringAttribute;
import protocol.parser.BdmCell;
import protocol.parser.BdmFieldParser;

import com.sun.star.lang.IndexOutOfBoundsException;

public class BdmFrameParser
{
  protected BdmFrame m_bdmFrame;
  protected BdmCell  m_bdmCell;

  protected final BdmStringAttributeParser   m_name;
  protected final BdmIntegerAttributeParser  m_id;
  protected final BdmValidityAttributeParser m_transmitters;
  protected final BdmValidityAttributeParser m_receivers;

  public static boolean isFrame(BdmCell bdmCell) throws IndexOutOfBoundsException
  {
    return bdmCell.getCell().getFormula().equals(BdmFrame.FRAME_MARKER);
  }

  public BdmFrameParser(BdmFrame bdmFrame, BdmCell bdmCell) throws IndexOutOfBoundsException
  {
    m_bdmFrame = bdmFrame;
    m_bdmCell  = bdmCell;

    m_name         = new BdmStringAttributeParser ( bdmFrame.m_name,         bdmCell.getCell( 3, 1), bdmCell.getCell());
    m_id           = new BdmIntegerAttributeParser( bdmFrame.m_id,           bdmCell.getCell( 0, 2), bdmCell.getCell());
    m_transmitters = new BdmValidityAttributeParser(bdmFrame.m_transmitters, bdmCell.getCell(-2, 2), bdmCell.getCell());
    m_receivers    = new BdmValidityAttributeParser(bdmFrame.m_receivers,    bdmCell.getCell( 2, 0), bdmCell.getCell());
  }

  public void parse() throws BdmException, IndexOutOfBoundsException
  {
    BdmField bdmField;
    BdmFieldParser bdmFieldParser;

    m_name.parse();
    System.out.print("  ");
    System.out.println(((BdmStringAttribute)m_name.m_bdmAttribute).getValue());
    m_id.parse();
    m_transmitters.parse();
    m_receivers.parse();

    m_bdmCell.getCell(3, -5);

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
    
    m_bdmCell.getCell(1, 0);
  }

}
