/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

public abstract class BdmFrame
{
  protected byte m_byteArray[];

  protected BdmFrame(short size)
  {
    m_byteArray = new byte[1 + size];
  }

  public abstract byte  getId();
  public abstract short getSize();
  public abstract void  describe();

  protected abstract byte[] toByteStream();
  protected abstract void   fromByteStream(final byte[] payload);
}

