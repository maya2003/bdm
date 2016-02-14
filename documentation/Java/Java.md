# Java TCP generic protocol

This version of the code corresponds to a sample implementation of the generic communication protocol, in Java, based on TCP. It provides a TCP server and a TCP client.


# License

Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU - MIT license  
See the file COPYING for copying permission.


## Structure of the protocol

We rely on TCP-IP for content guaranty, delivery guaranty, packet ordering, routing and so.

* The protocol is made of a serie of frames;
* Each frame starts by a 1-octet header which indicates the type of the frame;
* Each type of frame has a fixed-size payload (which can be 0);
* The rest of the frame after the 1-octet header is a binary frame payload;
* The size of the payload is implicit from the header.


# Compile and test

* In a first terminal, type:  javac BdmServer.java && java BdmServer
* In a second terminal, type: javac BdmMaster.java && java BdmMaster
* The client (master) connects to the server and then periodically sends SampleRequestFrame frames to the server;
* The server (slave) accepts the connection request and then responds by sending back SampleResponseFrame frames to the client.


# Class diagram

## Common classes

* BdmNode: This interface represents a node: either the BdmMaster or the BdmSlave;
* BdmFrame: This abstract class represents a generic frame;
* BdmFrameParser: This class represents the frame parser, which is used by the nodes.

## Server classes

* BdmServer: The TCP server;
* BdmSlave (*): A sample implementation of a BDM slave, which receives and manages a frame. The BdmSlave is instanciated by the BdmServer on response to an incoming connection request.

## Client classes

* BdmMaster (*): A sample implementation of a BDM master, which sends a frame to the server and manages the response;
* BdmClient: The TCP client; the client is instanciated by the BdmMaster.


## Frames of the sample protocol

* SampleRequestFrame (*): Extends BdmFrame; represents a sample frame sent by the BdmMaster to the BdmSlave;
* SampleResponseFrame (*): Extends BdmFrame; represents a sample frame sent by the BdmSlave to the BdmMaster, in response to the SampleRequestFrame.

The classes marked by (*) shall be updated/reworked on a real implementation.


## Server class diagram

<img src='http://g.gravizo.com/g?interface BdmNode{} /** * @navassoc - - - BdmSlave */ class BdmServer{} /** * @navassoc - - - BdmFrameParser */ class BdmSlave implements BdmNode {} class BdmFrame{} /** * @has - - - BdmFrame */ class BdmFrameParser{} class SampleRequestFrame extends BdmFrame {} class SampleResponseFrame extends BdmFrame {}'>


## Client class diagram

<img src='http://g.gravizo.com/g?interface BdmNode{} class BdmClient{} /** * @navassoc - - - BdmClient * @navassoc - - - BdmFrameParser */ class BdmMaster implements BdmNode {} class BdmFrame{} /** * @has - - - BdmFrame */ class BdmFrameParser{} class SampleRequestFrame extends BdmFrame {} class SampleResponseFrame extends BdmFrame {}'>

## Global class diagram

<img src='http://g.gravizo.com/g?interface BdmNode{} /** * @navassoc - - - BdmSlave */ class BdmServer{} /** * @navassoc - - - BdmFrameParser */ class BdmSlave implements BdmNode {} /** * @depend - - - BdmServer */ class BdmClient{} /** * @navassoc - - - BdmClient * @navassoc - - - BdmFrameParser */ class BdmMaster implements BdmNode {} class BdmFrame{} /** * @has - - - BdmFrame */ class BdmFrameParser{} class SampleRequestFrame extends BdmFrame {} class SampleResponseFrame extends BdmFrame {}'>

# How to implement your custom protocol?

1. Each frame of the protocol shall extend BdmFrame;  
For each frame:
2. Set the id attribute; the value shall be unique;
3. Set the frameSize attribute; the frameSize is the payload size, excluding the id;
4. Define some attributes as needed;
5. Define the toByteStream() method which converts the attributes to a binary frame; you can use SampleRequestFrame.java and SampleResponseFrame.java as an example;
6. Define the fromByteStream() method which extracts the values of the attributes from a binary frame; you can use SampleRequestFrame.java and SampleResponseFrame.java as an example;
7. Define the describe() method (optional).
