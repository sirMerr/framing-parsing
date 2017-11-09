package Server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Handles reading and writing packets
 *
 * @author Tiffany Le-Nguyen
 * @author Trevor Eames
 * @author Alessandro Ciotola
 */
public class Packet {
    public static final int BUFSIZE = 10;

    /**
     * Reads bytes from stream
     *
     * @param socket Socket that receives data
     * @return bytes read from the stream
     * @throws IOException
     */
    public byte[] read(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        byte[] buffer = new byte[BUFSIZE];
        in.read(buffer);

        return buffer;
    }

    /**
     * Write bytes to stream
     *
     * @param bytes  Bytes to write
     * @param socket Socket that will be written to
     * @throws IOException
     */
    public void write(byte[] bytes, Socket socket) throws IOException {
        // Check size is what we expect
        if (bytes.length != BUFSIZE) {
            throw new IOException("Packet size is: " + bytes.length
                    + " instead of [" + BUFSIZE + "]");
        }

        OutputStream out = socket.getOutputStream();
        out.write(bytes);
    }
}
