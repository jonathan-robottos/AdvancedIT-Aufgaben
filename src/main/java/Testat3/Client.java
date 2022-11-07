package Testat3;

import java.net.*;
import java.io.*;

class Client {
    public final static int DEFAULT_PORT = 5999;
    public final static int MAX_PACKET_SIZE = 65507;

    public static void main(String[] args) {
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        String hostname = "localhost";
        int port = DEFAULT_PORT;
        String s;
        //  determineserver from command line if available
        if (args.length > 1) {
            try {
                hostname = args[0];
                port = Integer.parseInt(args[1]);
                if (port < 1 || port > 65535)throw new Exception();
            } catch (Exception e) {
                System.out.println ("∗∗∗∗∗Usage: FileClient[hostname portno.]");
            }
        }
        try {
            // create client datagram socket
            DatagramSocket socket = new DatagramSocket();
            InetAddress host = InetAddress.getByName(hostname);
            System.out.print("Type command to send !");
            System.out.println("( ’READ file, lineNo’ or ’WRITE file, lineNo, data’ or ’x’ to exit )");
            do {
                System.out.print ("> "); // print user prompt
                s = userIn.readLine(); // read user input line
                if (s == null || s.equals("x") )break;
                byte[] data = s.getBytes(); // construct packet to send
                int len = data.length;
                if (len > MAX_PACKET_SIZE)
                    throw new Exception("Data too large to send");
                DatagramPacket outPacket = new DatagramPacket(data, len, host, port);
                socket.send(outPacket); // send packet

                // create buffer for the server’s answer
                byte[] buffer = new byte[MAX_PACKET_SIZE];
                DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(inPacket);
                String answer = new String(inPacket.getData(), 0, inPacket.getLength());
                System.out.println ("∗∗RECEIVED ANSWER: <" + answer + ">");
            } while (true);
            socket.close();
        } // try
        catch (Exception e) {
            System.err.println(e);
        }
    } //main
} // class Client