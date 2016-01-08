/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BdmFrameParser
{
  private enum State
  {
    WAIT_ID,
    WAIT_DATA,
  }

  public static final int PAYLOAD_MAX_SIZE = 37; /* The max. size of a frame excluding the header; must be >= 1! */

  public static final boolean debug = false;

  protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();

  protected BdmNode  m_bdmNode;
  protected BdmFrame m_bdmFrames[];
  protected State    m_state;
  protected BdmFrame m_currentFrame;
  protected short    m_currentLen;
  protected byte     m_payload[];

  public BdmFrameParser(BdmNode bdmNode)
  {
    m_bdmNode   = bdmNode;
    m_bdmFrames = new BdmFrame[256];
    m_payload   = new byte[PAYLOAD_MAX_SIZE];
    resetState();
  }

  public void registerRxFrame(BdmFrame bdmFrame)
  {
    if(m_bdmFrames[bdmFrame.getId() & 0xFF] != null)
    {
      throw new UnsupportedOperationException("Duplicate frame id '0x" + Integer.toHexString(bdmFrame.getId() & 0xFF).toUpperCase() + "'!");
    }

    m_bdmFrames[bdmFrame.getId() & 0xFF] = bdmFrame;

    if(debug)
    {
      System.out.println("Registered frame " + bdmFrame + " with id '0x" + Integer.toHexString(bdmFrame.getId() & 0xFF).toUpperCase() + "'.");
    }
  }

  public void resetState()
  {
    m_state = State.WAIT_ID;
    m_currentFrame = null;
    m_currentLen   = 0;
  }

  /* Manage a received chunk */
  /* Warning, a chunk can be a full frame, the begining of frame, a part of a frame and even contain both the end of frame and the begining of the next frame! */
  public void bytesReceived(byte buffer[], int size) throws IOException
  {
    for(int i = 0; i < size; i++)
    {
      if(debug)
      {
        System.out.print(hexArray[(buffer[i] & 0xFF) >>> 4]);
        System.out.print(hexArray[(buffer[i] & 0xFF) & 0x0F]);
        System.out.print(":");
      }

      switch(m_state)
      {
        case WAIT_ID:
        {
          m_currentFrame = m_bdmFrames[buffer[i] & 0xFF];

          if(m_currentFrame == null)
          {
            /* Abort protocol! */
            throw new UnsupportedOperationException("Received unhandled frame with id '0x" + Integer.toHexString(buffer[i] & 0xFF).toUpperCase() + "'!");
          }

          m_state = State.WAIT_DATA;
          break;
        }

        case WAIT_DATA:
        {
          m_payload[m_currentLen++] = buffer[i];

          if(m_currentLen == m_currentFrame.getSize())
          {
            System.out.println();
            System.out.print("Frame received: ");
            m_currentFrame.fromByteStream(m_payload);
            m_currentFrame.describe();
            m_bdmNode.frameReceived(m_currentFrame);
            resetState();
          }

          break;
        }
      }
    }
  }

  protected void dump()
  {
    for(int i = 0; i < m_currentLen; i++)
    {
      System.out.print(hexArray[(m_payload[i] & 0xFF) >>> 4]);
      System.out.print(hexArray[(m_payload[i] & 0xFF) & 0x0F]);

      if(i < m_currentLen - 1)
      {
        System.out.print(':');
      }
    }

    System.out.println();
  }

}

