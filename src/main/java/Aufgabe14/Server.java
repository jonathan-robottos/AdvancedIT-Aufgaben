package Aufgabe14;

import java.net.*;
import java.io.*;

public class Server {
    public static final int DEFAULT_PORT = 5999;
    public static final int MAX_PACKET_SIZE = 65507;
    public static final String PATH = "D:\\Development\\AdvancedIT-Aufgaben\\src\\main\\java\\Aufgabe14\\";

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        MyFile f = new MyFile();

        //determine server port from command line
        if(args.length > 0){
            try{
                port = Integer.parseInt(args[0]);
                if(port < 1 || port > 65535) throw new Exception();
            }catch (Exception e){
                System.out.println("****** Port number not available, should be between 1 - 65535");
            }
        }

        //Server setup
        try {
            DatagramSocket socket = new DatagramSocket(port);

            while (true) {
                //receive from client
                DatagramPacket request = new DatagramPacket(new byte[MAX_PACKET_SIZE], MAX_PACKET_SIZE);
                socket.receive(request);

                //send to client
                String reqData = new String(request.getData(), 0, request.getLength());

                byte[] buffer = reqData.getBytes();
                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();

                if(reqData.equals("READ")){
                    DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
                    socket.send(response);
                }
            }

        } catch (SocketException e) {
            System.out.println("Socket error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }

    }
}
