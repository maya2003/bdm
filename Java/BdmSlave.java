/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

import java.io.*;

public class BdmSlave implements BdmNode
{
  protected DataOutputStream m_out;
  protected BdmFrameParser m_bdmFrameParser;

  public BdmSlave(DataInputStream in, DataOutputStream out) throws IOException
  {
    m_out = out;

    /* Create the BdmFrameParser and register the frames of the protocol */
    m_bdmFrameParser = new BdmFrameParser(this);
    m_bdmFrameParser.registerRxFrame(new SampleRequestFrame());
    System.out.println();

    byte toto[] = new byte[100]; // TODO: list buffers...

    try
    {
      while(true)
      {
        int result = in.read(toto);
        m_bdmFrameParser.bytesReceived(toto, result);
      }
    }
    catch(java.lang.NullPointerException e)
    {
    }
  }

  public void frameReceived(BdmFrame BdmFrame) throws IOException
  {
    // TODO: check frame type...
    SampleResponseFrame sampleResponseFrame = new SampleResponseFrame();
    send(sampleResponseFrame);
  }

  // duplicate
  public void send(BdmFrame bdmFrame) throws IOException
  {
    m_out.write(bdmFrame.toByteStream());
  }

}

