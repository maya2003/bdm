/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

// TODO: move common features to BdmNode

import java.io.*;
import java.net.*;

public class BdmMaster implements BdmNode
{
  protected DataOutputStream m_out;
  protected BdmFrameParser m_bdmFrameParser;
  protected int i;

  public BdmMaster(DataInputStream in, DataOutputStream out) throws IOException
  {
    m_out = out;
    m_bdmFrameParser = new BdmFrameParser(this);
    i = 1;

    /* register the frames of the protocol */
    m_bdmFrameParser.registerRxFrame(new SampleResponseFrame());
    System.out.println();

    SampleRequestFrame sampleRequestFrame = new SampleRequestFrame();
    sampleRequestFrame.param = (byte)i++;
    send(sampleRequestFrame);

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
    /* Delay 1 s to simulate a processing */
    try
    {
      Thread.sleep(1000);
    }
    catch(InterruptedException e)
    {
    }

    SampleRequestFrame sampleRequestFrame = new SampleRequestFrame();
    sampleRequestFrame.param = (byte)i++;
    send(sampleRequestFrame);
  }

  public void send(BdmFrame bdmFrame) throws IOException
  {
    m_out.write(bdmFrame.toByteStream());
  }

  public static void main(String args[]) throws IOException
  {
    BdmClient bdmClient = new BdmClient();
    bdmClient.connect();
  }

}

