/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package bdmModel;

import java.util.ArrayList;
import java.util.List;

import bdmModel.BdmException;
import bdmModel.BdmField;

public class BdmFrame
{
  public static final String FRAME_MARKER = "Frame";

  public final BdmStringAttribute   m_name;
  public final BdmIntegerAttribute  m_id;
  public final BdmValidityAttribute m_transmitters;
  public final BdmValidityAttribute m_receivers;
  public final List<BdmField> fields;

  public BdmFrame() throws BdmException //TODO !!
  {
    m_name         = new BdmStringAttribute(  "name",         false, false, "", null, null);
    m_id           = new BdmIntegerAttribute( "id",           true,  false, 0,  null, null);
    m_transmitters = new BdmValidityAttribute("transmitters", true);
    m_receivers    = new BdmValidityAttribute("receivers",    true);
    fields = new ArrayList<BdmField>();
  }

}
