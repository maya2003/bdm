/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package bdmModel.data;

import bdmModel.BdmException;
import bdmModel.BdmStringAttribute;

public class BdmData
{
  public final BdmStringAttribute m_requirementId;
  public final BdmStringAttribute m_coveredRequirements;
  public final BdmStringAttribute m_name;
  public final BdmStringAttribute m_type;
  public final BdmStringAttribute m_cType;

  public BdmData() throws BdmException //TODO !!
  {
    m_requirementId       = new BdmStringAttribute("requirementId",       true,  true,  null, null, null);
    m_coveredRequirements = new BdmStringAttribute("coveredRequirements", true,  true,  null, null, null);
    m_name                = new BdmStringAttribute("name",                false, false, "",   null, null);
    m_type                = new BdmStringAttribute("type",                false, false, "",   null, null);
    m_cType               = new BdmStringAttribute("cType",               false, false, "",   null, null);
  }

}

