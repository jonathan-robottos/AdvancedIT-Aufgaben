package Aufgabe14;

import java.io.*;
import java.net.*;

public class Client {
    public static final int DEFAULT_PORT = 5999;
    public static final int MAX_PACKET_SIZE = 65507;

    public static void main(String[] args) {
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        String hostname = "localhost";
        int port = DEFAULT_PORT;
        DatagramSocket socket = null;

        //determine server from command line if available
        if(args.length > 1){
            try{
                hostname = args[0];
                port = Integer.parseInt(args[1]);
                if(port < 1 || port > 65535); throw new Exception();
            }catch (Exception e){
                System.out.println("***** Usage: Client hostname portno");
            }
        }

        try{
            InetAddress adress = InetAddress.getByName(hostname);
            socket = new DatagramSocket(port);

            System.out.println("*** Client: ready for sending packets to host:" + hostname + " at port " + port + ".");
            System.out.println(("Type messages to send! (':q' for exit)"));

            while (true){
                //Send to server
                System.out.print("> ");
                String s = userIn.readLine();
                if( s == null || s.equals(":q")) break;

                DatagramPacket request = new DatagramPacket(new byte[MAX_PACKET_SIZE], MAX_PACKET_SIZE, adress, port);
                socket.send(request);

                //receive from server
                byte[] buffer = new byte[512];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);

                String lineContent = new String(buffer, 0, response.getLength());
                System.out.println(lineContent);
            }

        }catch (SocketException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
