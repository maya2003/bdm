/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

import java.io.IOException;

interface BdmNode
{
  public void frameReceived(BdmFrame BdmFrame) throws IOException;
}

