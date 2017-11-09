package Client;

import Helpers.DoubleToBytes;
import Server.Packet;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Connects the server and the UI together.
 * Start this and the server to see the application begin.
 *
 * @author Tiffany Le-Nguyen
 * @author Trevor Eames
 * @author Alessandro Ciotola
 */
public class Client {
    public static void main(String[] args) throws IOException{
        Scanner reader = new Scanner(System.in);
        Packet packet = new Packet();
        Boolean anotherNumber = true;
        byte[] input = new byte[10];
        input[0] = 0;
        
        final Pattern PATTERN = 
                Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        System.out.print("Hello, Welcome to Framing and parsing!\n"
                + "Please enter the ip address of the server you wish to connect to: ");
        String serverIp = reader.next();
        
        while(!PATTERN.matcher(serverIp).matches()){
            System.out.println("I need a valid IP address please.");
            serverIp = reader.next();
        }

        // Create socket that is connected to server on specified port
        Socket socket = new Socket(serverIp, 51000);
        
        while (anotherNumber) {
            input = packet.read(socket);            
            byte[] response = new byte[10];
            
            //SERVER: RESTART=0, END=1, RECEIVE=2
            switch (input[0]) {
                case 0:
                    response[0] = 2;
                    byte[] doubleBytes = getByteArrayFromDouble();
                    for(int i = 1; i < 8; i++)
                        response[i] = doubleBytes[i - 1];
                    response[9] = getByteFromInt();
                    packet.write(response, socket);
                    break;
                case 1:
                    anotherNumber = false;
                    break;
                case 2:
                    byte[] doubles = new byte[8];
                    for(int i = 0; i < 8; i++)
                        doubles[i] = input[i + 1];
                    double byteDouble = DoubleToBytes.byteArrayToDouble(doubles);
                    System.out.println("Your number: " + byteDouble);
                    response[0] = getUserResponse();
                    response[1] = 0;
                    response[2] = 0;
                    response[3] = 0;
                    response[4] = 0;
                    response[5] = 0;
                    response[6] = 0;
                    response[7] = 0;
                    response[8] = 0;
                    response[9] = 0;
                    packet.write(response, socket);
                    break;
                case 3:
                    System.out.println("An error has occured!");
                    anotherNumber = false;
                    break;
            }
        }        
        socket.close();     
    }
    
    private static byte getUserResponse()
    {
        Scanner reader = new Scanner(System.in);
        System.out.println("Do you want to enter another number? (y/n)");
        String answer = reader.next();  
        if(answer.equalsIgnoreCase("y"))
            return (byte)0;
        else 
            return (byte)1;
    }
    
    private static byte[] getByteArrayFromDouble()
    {
        Scanner reader = new Scanner(System.in);
        double doubleNumber = 0.0;
        System.out.println("Please enter your double number: ");
        while(!reader.hasNextDouble())
        {
            doubleNumber = reader.nextDouble();        
        }
        
        byte[] doubleBytes = DoubleToBytes.doubleToByteArray(doubleNumber);
        return doubleBytes;
    }
    
    private static byte getByteFromInt()
    {
        Scanner reader = new Scanner(System.in);
        int intNumber = 0;
        System.out.println("Please enter your int number: ");
        while(!reader.hasNextInt())
        {
            intNumber = reader.nextInt();        
        }        
        return (byte)intNumber;
    }
}
