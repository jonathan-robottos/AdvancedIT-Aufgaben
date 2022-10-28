package Aufgabe13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    public static final int DEFAULT_PORT = 4999;
    public static final int MAX_PACKET_SIZE = 65507;

    public static void main(String[] args) {
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        String hostname = "localhost";
        int port = DEFAULT_PORT;
        String s;
        DatagramSocket socket = null;

        //determine server from command line if available
        if(args.length > 1){
            try{
                hostname = args[0];
                port = Integer.parseInt(args[1]);
                if(port < 1 || port > 65535); throw new Exception();
            }catch (Exception e){
                System.out.println("****** Usage: Client hostname portno");
            }
        }

        try{
            socket = new DatagramSocket();
            InetAddress host = InetAddress.getByName(hostname);
            System.out.println("*** Client: ready for sending packets to host:" + host + " at port " + port + ".");
            System.out.println(("Type messages to send! (':q' for exit)"));

            do {
                System.out.print("> ");
                s = userIn.readLine();
                if( s == null || s.equals(":q")) break;

                byte[] data = s.getBytes();
                int len = data.length;
                if(len > MAX_PACKET_SIZE) throw new Exception("Data too large to send");

                DatagramPacket dp = new DatagramPacket(data, len, host, port);
                socket.send(dp);
            }while (true);
        }catch (Exception e){
            System.err.println(e);
        } finally {
            try{
                if(socket != null) socket.close();
            }catch (Exception e){

            }
        }

    }
}
