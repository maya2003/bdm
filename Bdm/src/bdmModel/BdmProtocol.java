/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.
*/

package bdmModel;

import java.util.ArrayList;
import java.util.List;

public class BdmProtocol
{
  public static final String PROTOCOL_MARKER = "Protocol";

  public final BdmStringAttribute m_name;
  public final List<BdmFrame>     frames;

  public BdmProtocol() throws BdmException //TODO !!
  {
    m_name = new BdmStringAttribute("name", false, false, "", null, null);
    frames = new ArrayList<BdmFrame>();
  }

}
