/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package bdmModel.data;

import java.util.ArrayList;
import java.util.List;

import bdmModel.BdmException;

public class BdmDataDictionary
{
  public final List<BdmData> data;

  public BdmDataDictionary() throws BdmException //TODO !!
  {
    data = new ArrayList<BdmData>();
  }

}

