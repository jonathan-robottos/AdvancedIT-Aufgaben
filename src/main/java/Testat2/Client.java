package Testat2;

import java.io.*;
import java.net.*;

public class Client {
    public static final int PORT = 7777;

    public static void main(String[] args) {
        //declare Socket
        Socket client = null;

        try {
            while (true){
                //init new connection
                client = new Socket("localhost", PORT);

                //init filter streams
                BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader networkIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter networkOut = new PrintWriter(client.getOutputStream());

                //read from console and send to server
                System.out.print(">");
                String theLine = userIn.readLine();
                if(theLine.equals(":q")) break;
                networkOut.println(theLine);
                networkOut.flush();

                //receive from server and print data to console
                System.out.println(networkIn.readLine());

                //close connection
                client.close();
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }
}
