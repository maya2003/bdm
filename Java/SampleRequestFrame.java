/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * Structure of the sample frame:
 * 0: byte (1 octet)
 **/

public class SampleRequestFrame extends BdmFrame
{
  protected static final byte id = 0x01;
  protected static final short frameSize = 1;

  public byte param;


  public SampleRequestFrame()
  {
    super(frameSize);
  }

  public byte getId()
  {
    return id;
  }

  public short getSize()
  {
    return frameSize;
  }

  public void describe()
  {
    System.out.println("param: " + param);
  }

  protected byte[] toByteStream()
  {
    m_byteArray[0] = id;

    m_byteArray[1+0] = param;

    return m_byteArray;
  }

  protected void fromByteStream(final byte[] payload)
  {
    param = payload[0];
  }

}

