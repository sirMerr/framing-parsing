# framing-parsing
Writing TCP Client and Server Software Framing and Parsing

Due: November 17, 2017

## Overview
This project normally has specs as shown on the following pages. In order to save time, you will do this project in an abbreviated form.

- [ ] Read the following pages carefully. Examine the `doubleToByteArray` and the `byteArrayToDouble` methods, in the `DoubleToBytes` class in the course subdirectory, carefully.

- [ ] Answer the question posed in the specs regarding byte reversal. Note that the answer must be one sentence.

- [ ] Answer the following question in one sentence: in the `byteArrayToDouble` method, why is it necessary to zero out the most significant seven bytes of the long into which each array byte is cast, before shifting and ORing it with the others to produce the final long result? 

Final documentation (per team) will consist of:
1. a title page
2. your answer to the byte reversal question
3. your answer to the second question
4. all of the above in an appropriate binder.

## More details
You will work in assigned groups. All members of a group are expected to contribute more or less equally to the project and to be familiar with all of the work of the project. All group members must be present at the demonstration.

Each team will write a client-server TCP-based system in Java that implements the Add 1 (A1) protocol. It will allow a person using the client to send a block consisting of one double item and one int item to the server, have the server process the items by adding 1.0 to the double and 1 to the int, and then have the server send the modified block back to the client.

The client will allow the user to input the IP address of the server. The machines will then connect to begin the session, after which:
- [ ] the client program will allow the user to enter the double value and the int value, or to quit the client; the client will allow the user the same two choices after the server sends back the modified block
- [ ] the server will continue servicing the client that began the session until the client terminates the session by closing the socket, at which point the server will resume listening for new clients (i.e. the server will be an iterative server, servicing one client at a time).

- [ ] Both the client and the server are to display appropriate messages while running and the information sent and received while a session is in progress.

- [ ] Your A1 protocol will have the server listening on port `51,000`.

- [ ] All A1 blocks consist of one double item and one int item.

- [ ] An A1 session consists of zero or more blocks exchanged.

- [ ] The client initiates a session by sending the server a block. The server processes the block by adding 1.0 to the double item and 1 to the int item, and sends back the modified block, after which the client can send another block, etc.

Because Java TCP sockets send and receive arrays of bytes, your protocol will have to chop the double and the int into bytes, before sending a block. Similarly, it will have to re-assemble the double and the int at the receiving end. The chopping and re-assembling can be accomplished in 2 ways:

1. use convenient, existing Java classes, interfaces and methods (e.g. `writeShort()` of `DataOutput`, `DataOutputStream`, `BufferedOutputStream`,  `OutputStream`, `socket.getOutputStream()`, and `readShort` of `DataInput`, etc.)

2. have your own code perform the tasks (e.g. write methods such as `doubleToByteArray()` (that will use `Double.doubleToRawLongBits()` to take a double and access it as a long, then right-shift and cast to isolate the bytes of the long) and b`yteArrayToDouble()` (that will do the reverse: cast a byte to long, zero out the high-order 56 bits using &, left-shift to the proper position, combine to form the `long` using |, use `Double.longBitsToDouble()` to get the original double from the `long`)). See the file `DoubleToBytes.java` in the course subdirectory on the server for much of the needed code.

You must also consider the following when sending multi-byte data types. The architecture of a CPU is either little-endian or big-endian; some modern CPUs allow a choice via software. A big-endian CPU will store a multi-byte data type into memory with the most significant byte at the lowest address in RAM, etc. A little-endian CPU will store a multi-byte data type into memory with the least significant byte at the lowest address in RAM, etc. Assume that an int with a value of `0x12345678` is stored at address `0`. That int would be stored as follows:


| Address  | 00  | 01  | 02  | 03  |
|---|---|---|---|---|
| big-endian |  12 |  34 | 56  | 78  |
| small-endian  | 78  |  56 |  34 |12|

The TCP/IP protocol standard specifies that all the bytes that make up an item must be sent in ‘network order’, which happens to be big-endian. Intel Pentium CPUs are little-endian; many other CPUs are big-endian. This implies that, on an Intel machine, your software would normally have to chop an int into bytes, and then reverse the bytes before transmitting them. However, your Java software does not need to perform the reversal. 

- [ ] By the due date, each team may submit a sheet with one sentence explaining why your software does not have to perform the reversal, for 1 bonus point. **Consider the machines involved in your system**

- [ ] Additionally, your protocol will have to frame the data before sending the message (i.e. it will have to ensure that every data item is either fixed-length and in the appropriate position, or is variable-length and either prefixed with a count, or ended with a delimiter), so that all items can be properly identified by the receiver. Since your protocol transmits two fixed-length items, framing will not be a concern.

- [ ] At the receiving end, the protocol will have to parse the data (i.e. it will have to access the correct number of bytes, starting at the correct position, for each item before assembling those bytes into that item’s data type).

It should be clear that accuracy in framing and parsing is absolutely essential to the proper operation of a protocol.
