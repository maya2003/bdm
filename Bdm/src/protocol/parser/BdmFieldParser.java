/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package protocol.parser;

import protocol.model.BdmException;
import protocol.model.BdmField;
import protocol.model.BdmStringAttribute;
import protocol.parser.BdmBooleanAttributeParser;
import protocol.parser.BdmCell;
import protocol.parser.BdmIntegerAttributeParser;
import protocol.parser.BdmStringAttributeParser;

import com.sun.star.lang.IndexOutOfBoundsException;

public class BdmFieldParser
{
  protected final BdmStringAttributeParser  m_fieldMarker; // special
  protected final BdmStringAttributeParser  m_requirementId;
  protected final BdmStringAttributeParser  m_coveredRequirements;
  protected final BdmStringAttributeParser  m_name;
  protected final BdmStringAttributeParser  m_id;
  protected final BdmIntegerAttributeParser m_index;
  protected final BdmStringAttributeParser  m_type;
  protected final BdmBooleanAttributeParser m_notAvailableAllowed;
  protected final BdmStringAttributeParser  m_unit;
  protected final BdmIntegerAttributeParser m_startByte;
  protected final BdmIntegerAttributeParser m_startBit;
  protected final BdmIntegerAttributeParser m_size;
  protected final BdmValidityAttributeParser     m_errorValues;
  protected final BdmValidityAttributeParser     m_notAvailableValues;
  protected final BdmValidityAttributeParser     m_validValues;
  protected final BdmIntegerAttributeParser m_offsetNumerator;
  protected final BdmIntegerAttributeParser m_gainNumerator;
  protected final BdmIntegerAttributeParser m_denominator;
  protected final BdmBooleanAttributeParser m_specificConversion;
//protected final BdmStringAttributeParser  m_realOffset; // special
//protected final BdmStringAttributeParser  m_realGain;   // special
  protected final BdmStringAttributeParser  m_comment;

  protected final BdmStringAttributeParser  m_destination; // remove

  public static boolean isField(BdmCell bdmCell) throws IndexOutOfBoundsException
  {
    return bdmCell.getCell().getFormula().equals(BdmField.FIELD_MARKER);
  }

  public BdmFieldParser(BdmField bdmField, BdmCell bdmCell) throws IndexOutOfBoundsException
  {
    m_fieldMarker         = new BdmStringAttributeParser  (bdmField.m_fieldMarker,         bdmCell.getCell(),      bdmCell.getCell()); // special
    m_requirementId       = new BdmStringAttributeParser  (bdmField.m_requirementId,       bdmCell.getCell(1,  1), bdmCell.getCell());
    m_coveredRequirements = new BdmStringAttributeParser  (bdmField.m_coveredRequirements, bdmCell.getCell(1,  0), bdmCell.getCell());
    m_name                = new BdmStringAttributeParser  (bdmField.m_name,                bdmCell.getCell(1,  0), bdmCell.getCell());
    m_id                  = new BdmStringAttributeParser  (bdmField.m_id,                  bdmCell.getCell(0,  2), bdmCell.getCell());
    m_index               = new BdmIntegerAttributeParser (bdmField.m_index,               bdmCell.getCell(0,  2), bdmCell.getCell());
    m_type                = new BdmStringAttributeParser  (bdmField.m_type,                bdmCell.getCell(1, -4), bdmCell.getCell());
    m_notAvailableAllowed = new BdmBooleanAttributeParser (bdmField.m_notAvailableAllowed, bdmCell.getCell(0,  2), bdmCell.getCell());
    m_unit                = new BdmStringAttributeParser  (bdmField.m_unit,                bdmCell.getCell(0,  2), bdmCell.getCell());
    m_startByte           = new BdmIntegerAttributeParser (bdmField.m_startByte,           bdmCell.getCell(1, -4), bdmCell.getCell());
    m_startBit            = new BdmIntegerAttributeParser (bdmField.m_startBit,            bdmCell.getCell(0,  2), bdmCell.getCell());
    m_size                = new BdmIntegerAttributeParser (bdmField.m_size,                bdmCell.getCell(0,  2), bdmCell.getCell());
    m_errorValues         = new BdmValidityAttributeParser(bdmField.m_errorValues,         bdmCell.getCell(1, -4), bdmCell.getCell());
    m_notAvailableValues  = new BdmValidityAttributeParser(bdmField.m_notAvailableValues,  bdmCell.getCell(1,  0), bdmCell.getCell());
    m_validValues         = new BdmValidityAttributeParser(bdmField.m_validValues,         bdmCell.getCell(1,  0), bdmCell.getCell());
    m_offsetNumerator     = new BdmIntegerAttributeParser (bdmField.m_offsetNumerator,     bdmCell.getCell(1,  0), bdmCell.getCell());
    m_gainNumerator       = new BdmIntegerAttributeParser (bdmField.m_gainNumerator,       bdmCell.getCell(0,  2), bdmCell.getCell());
    m_denominator         = new BdmIntegerAttributeParser (bdmField.m_denominator,         bdmCell.getCell(0,  2), bdmCell.getCell());
    m_specificConversion  = new BdmBooleanAttributeParser (bdmField.m_specificConversion,  bdmCell.getCell(1, -4), bdmCell.getCell());
  //m_realOffset          = new BdmStringAttributeParser  (bdmField.m_realOffset,          bdmCell.getCell(0,  2), bdmCell.getCell()); // special
  //m_realGain            = new BdmStringAttributeParser  (bdmField.m_realGain,            bdmCell.getCell(0,  2), bdmCell.getCell()); // special
    m_comment             = new BdmStringAttributeParser  (bdmField.m_comment,             bdmCell.getCell(1,  0), bdmCell.getCell());

    m_destination         = new BdmStringAttributeParser  (bdmField.m_destination,         bdmCell.getCell(1,  0), bdmCell.getCell()); // remove
    bdmCell.getCell(2, -1);
  }

  public void parse() throws BdmException
  {
    m_fieldMarker.parse(); // special
    m_requirementId.parse();
    m_coveredRequirements.parse();
    m_name.parse();
    System.out.print("    ");
    System.out.println(((BdmStringAttribute)m_name.m_bdmAttribute).getValue());
    m_id.parse();
    m_index.parse();
    m_type.parse();
    m_notAvailableAllowed.parse();
    m_unit.parse();
    m_startByte.parse();
    m_startBit.parse();
    m_size.parse();
    m_errorValues.parse();
    m_notAvailableValues.parse();
    m_validValues.parse();
    m_offsetNumerator.parse();
    m_gainNumerator.parse();
    m_denominator.parse();
    m_specificConversion.parse();
    //m_realOffset.parse(); // special
    //m_realGain.parse();   // special
    m_comment.parse();

    m_destination.parse(); // remove
  }

}
