/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

// TODO: Manage unsigned types
// TODO: Manage a String (instead of several characters)

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * Structure of the sample frame:
 * 0.0:   bit    (1
 * 0.1:   bit       octet
 * 0.2:   bit             )
 * 1.0:   bit    (1 octet )
 * 2:     byte   (1 octet )
 * 3-4:   short  (2 octets)
 * 5-8:   int    (4 octets)
 * 9-16:  long   (8 octets)
 * 17-20: float  (4 octets)
 * 21-28: double (8 octets)
 * 29-30: char   (2 octets)
 * 31-32: char   (2 octets)
 * 33-34: char   (2 octets)
 * 35-36: char   (2 octets)
 **/

public class SampleResponseFrame extends BdmFrame
{
  protected static final byte id = (byte)0x81;
  protected static final short frameSize = 37;

  public boolean isA;
  public boolean isB;
  public boolean isC;
  public boolean isD;
  public byte    e;
  public short   f;
  public int     g;
  public long    h;
  public float   i;
  public double  j;
  public char    k;
  public char    l;
  public char    m;
  public char    n;

  public SampleResponseFrame()
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
    System.out.println("isA: " + isA);
    System.out.println("isB: " + isB);
    System.out.println("isC: " + isC);
    System.out.println("isD: " + isD);
    System.out.println("e:   0x" + Integer.toHexString(e & 0xFF).toUpperCase());
    System.out.println("f:   0x" + Integer.toHexString(f & 0xFFFF).toUpperCase());
    System.out.println("g:   0x" + Integer.toHexString(g).toUpperCase());
    System.out.println("h:   0x" + Long.toHexString(h).toUpperCase());
    System.out.println("i:   " + i);
    System.out.println("j:   " + j);
    System.out.println("k:   " + k);
    System.out.println("l:   " + l);
    System.out.println("m,n: " + m + n);
  }

  protected byte[] toByteStream()
  {
    ByteBuffer m_byteBuffer = ByteBuffer.wrap(m_byteArray).order(ByteOrder.BIG_ENDIAN); // cache, rewarp?

    m_byteArray[0] = id;

    // Method 1 (direct)
    m_byteArray[1+0] = (byte)((isA? 1: 0) | (isB? 2: 0) | (isC? 4: 0));
    m_byteArray[1+1] = (byte)(isD? 1: 0);
    m_byteArray[1+2] = e;

    // Method 2 (ByteBuffer)
    m_byteBuffer.putShort(  1+3, f);
    m_byteBuffer.putInt(    1+5, g);
    m_byteBuffer.putLong(   1+9, h);
    m_byteBuffer.putFloat( 1+17, i);
    m_byteBuffer.putDouble(1+21, j);
    m_byteBuffer.putChar(  1+29, k);
    m_byteBuffer.putChar(  1+31, l);
    m_byteBuffer.putChar(  1+33, m);
    m_byteBuffer.putChar(  1+35, n);

    return m_byteArray;
  }

  protected void fromByteStream(final byte[] payload)
  {
    ByteBuffer m_byteBuffer = ByteBuffer.wrap(payload).order(ByteOrder.BIG_ENDIAN); // cache, rewarp?

    isA = (payload[0] & 0x01) == 0x01;
    isB = (payload[0] & 0x02) == 0x02;
    isC = (payload[0] & 0x04) == 0x04;
    isD = (payload[1] & 0x01) == 0x01;
    e   = payload[2];
    f   = m_byteBuffer.getShort(  3);
    g   = m_byteBuffer.getInt(    5);
    h   = m_byteBuffer.getLong(   9);
    i   = m_byteBuffer.getFloat( 17);
    j   = m_byteBuffer.getDouble(21);
    k   = m_byteBuffer.getChar(  29);
    l   = m_byteBuffer.getChar(  31);
    m   = m_byteBuffer.getChar(  33);
    n   = m_byteBuffer.getChar(  35);
  }

}

