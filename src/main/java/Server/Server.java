package Server;

import Helpers.DoubleToBytes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException
    {
        // A1 protocol will have the server listening on this port
        int port = 51000;

        // Create a server socket to accept client connection requests
        ServerSocket servSocket = new ServerSocket(port);
        Packet packet = new Packet();
        DoubleToBytes dtb = new DoubleToBytes();
        AddOne a1 = new AddOne();

        Boolean keepGoing = true;

        // Run forever, accepting and servicing connections
        for (;;) {
            // Get client connection
            Socket clntSock = servSocket.accept();

            System.out.println("Handling client at " +
                    clntSock.getInetAddress().getHostAddress() + " on port " +
                    clntSock.getPort());

            // Receive until client closes connection, indicated by -1
            while (keepGoing) {
                byte[] input = packet.read(clntSock);
                byte[] response = new byte[Packet.BUFSIZE];

                if (!(input.length == 0)) {
                    // Look at first byte
                    switch (input[0]) {
                        // Client sends data
                        case 0:
                            byte[] doubleByte = modifyDouble(input);
                            response[0] = 2;
                            response[1] = doubleByte[0];
                            response[2] = doubleByte[1];
                            response[3] = doubleByte[2];
                            response[4] = doubleByte[3];
                            response[5] = doubleByte[4];
                            response[6] = doubleByte[5];
                            response[7] = doubleByte[6];
                            response[8] = doubleByte[7];
                            response[9] = 2; //int
                            break;
                        // Game ended
                        case 1:
                            response[0] = 1;
                            response[1] = 0;
                            response[2] = 0;
                            response[3] = 0;
                            response[4] = 0;
                            response[5] = 0;
                            response[6] = 0;
                            response[7] = 0;
                            response[8] = 0;
                            response[9] = 0;
                            packet.write(response, clntSock);
                            break;
                        // Restart
                        case 2:
                            response[0] = 0;
                            response[1] = 0;
                            response[1] = 0;
                            response[2] = 0;
                            response[3] = 0;
                            response[4] = 0;
                            response[5] = 0;
                            response[6] = 0;
                            response[7] = 0;
                            response[8] = 0;
                            response[9] = 0;
                            packet.write(response, clntSock);
                            break;
                        // Error
                        default:
                            response[0] = 3;
                            response[1] = 0;
                            response[1] = 0;
                            response[2] = 0;
                            response[3] = 0;
                            response[4] = 0;
                            response[5] = 0;
                            response[6] = 0;
                            response[7] = 0;
                            response[8] = 0;
                            response[9] = 0;
                            packet.write(response, clntSock);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Takes index 1-8 from the input and returns
     * a byte array
     * @param input
     * @return byte array consisting of only the double
     */
    private static byte[] makeByteArray(byte[] input) {
        byte[] doubleNum = new byte[8];
        doubleNum[0] = input[1];
        doubleNum[1] = input[2];
        doubleNum[2] = input[3];
        doubleNum[3] = input[4];
        doubleNum[4] = input[5];
        doubleNum[5] = input[6];
        doubleNum[6] = input[7];
        doubleNum[7] = input[8];

        return doubleNum;
    }

    /**
     * Modify the double to have 1.0 added to it and
     * return a new byte array with the modified byte array
     *
     * @param doubleInput
     * @return byte array
     */
    private static byte[] modifyDouble(byte[] doubleInput) {
        // Get a byte array consisting of only the double
        byte[] doubleByteArr = makeByteArray(doubleInput);

        // Get the double value from that byte array
        double doubleNum = DoubleToBytes.byteArrayToDouble(doubleByteArr);

        // Add 1.0 and parse it back into a byte array
        doubleByteArr = DoubleToBytes.doubleToByteArray(AddOne.addOne(doubleNum));

        // Return changed array
        return doubleByteArr;
    }

    /**
     * Modify double to have 1 added
     * @param intInput
     * @return
     */
    private static byte modifyInt(byte intInput) {
        return (byte) AddOne.addOne(intInput);
    }
}
