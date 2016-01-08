/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

import java.io.*;
import java.net.*;

public class BdmClient
{
  protected Socket m_socket;
  protected DataOutputStream m_out;
  protected DataInputStream  m_in;

  public BdmClient()
  {
  }

  public void connect() throws UnknownHostException, IOException
  {
    m_socket = new Socket("localhost", 2440);
    m_out = new DataOutputStream(m_socket.getOutputStream());
    m_in  = new DataInputStream(m_socket.getInputStream());

    System.out.println("Connected.");

    BdmMaster bdmMaster = new BdmMaster(m_in, m_out);


    /* BdmClient is the initiator */

    //socket.close();
  }

}

