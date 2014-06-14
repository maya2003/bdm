/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package bdmModel;

import java.util.ArrayList;
import java.util.List;

public class BdmProtocol
{
  public static final String PROTOCOL_MARKER = "Protocol";

  public final BdmStringAttribute m_copyrightNotice;
  public final BdmStringAttribute m_name;
  public final BdmStringAttribute m_frameTypeContainer;
  public final BdmStringAttribute m_basicTypesInclude;

  public final List<BdmFrame>     frames;

  public BdmProtocol() throws BdmException
  {
    m_copyrightNotice    = new BdmStringAttribute("copyrightNotice",    true,  true,  null, null, null);
    m_name               = new BdmStringAttribute("name",               false, false, "",   null, null);
    m_frameTypeContainer = new BdmStringAttribute("frameTypeContainer", false, true,  "u8", null, null);
    m_basicTypesInclude  = new BdmStringAttribute("basicTypesInclude",  false, false, "",   null, null);

    frames = new ArrayList<BdmFrame>();
  }

}
