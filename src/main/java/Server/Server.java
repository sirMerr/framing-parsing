package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) throws IOException
    {
        // A1 protocol will have the server listening on this port
        int port = 5100;

        // Create a server socket to accept client connection requests
        ServerSocket socket = new ServerSocket(port);
        Packet packet = new Packet();
    }
}
