package Aufgabe13;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

    public final static int DEFAULT_PORT = 4999;
    public final static int MAX_PACKET_SIZE = 65507;

    public static void main(String[] args){
        int port = DEFAULT_PORT;

        //determine server port from command line
        if(args.length > 0){
            try{
                port = Integer.parseInt(args[0]);
                if(port < 1 || port > 65535) throw new Exception();
            }catch (Exception e){
                System.out.println("****** Usage: Server portno");
            }
        }

        //set up server
        try{
            DatagramSocket socket = new DatagramSocket(port);

            System.out.println("*** ChatServer started at port " + port + ". Waiting for messages to display");

            byte[] buffer = new byte[MAX_PACKET_SIZE];
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);

            while(true){
                try{
                    socket.receive(dp);

                    String s = new String(dp.getData(), 0, dp.getLength());

                    System.out.println("* Message from " + dp.getAddress() + ":" + dp.getPort() + " < " + s + " >");
                }catch (Exception e) {
                    System.err.println(e);
                }
            }
        }catch (Exception e){
            System.err.println(e);
        }
    }
}
