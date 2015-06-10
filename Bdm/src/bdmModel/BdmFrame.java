/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package bdmModel;

import java.util.ArrayList;
import java.util.List;

import bdmModel.BdmException;
import bdmModel.BdmField;

public class BdmFrame
{
  public static final String FRAME_MARKER = "Frame";

  public final BdmStringAttribute  m_name;
  public final BdmIntegerAttribute m_id;
  public final List<BdmField> fields;

  public BdmFrame() throws BdmException //TODO !!
  {
    m_name = new BdmStringAttribute("name", false, false, "", null, null);
    m_id   = new BdmIntegerAttribute("id", true, false, 0, null, null);
    fields = new ArrayList<BdmField>();
  }

}
