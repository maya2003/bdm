/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

// TODO: Use NIO

import java.io.*;
import java.net.*;

class BdmServer
{
  public BdmServer()
  {
  }

  public void accept() throws IOException
  {
    ServerSocket serverSocket = new ServerSocket(2440);

    while(true)
    {
      System.out.println("Waiting for connection...");

      try
      {
        Socket socket = serverSocket.accept();
        DataInputStream  in  = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        System.out.println("Connected.");

        BdmSlave bdmSlave = new BdmSlave(in, out);
      }
      catch(IOException e)
      {
        System.out.println("Disconnected!");
      }
    }
  }

  static public void main(String argv[]) throws Exception
  {
    new BdmServer().accept();
  }

}

