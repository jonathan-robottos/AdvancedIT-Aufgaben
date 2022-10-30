package Testat2;

import java.io.*;
import java.net.*;

public class Client {
    public static final int PORT = 7777;

    public static void main(String[] args) {
        Socket client = null;


        try {

            while (true){
                client = new Socket("localhost", PORT);

                BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

                BufferedReader networkIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter networkOut = new PrintWriter(client.getOutputStream());
                System.out.print(">");
                String theLine = userIn.readLine();
                if(theLine.equals(":q")) break;
                networkOut.println(theLine);
                networkOut.flush();
                System.out.println(networkIn.readLine());

                client.close();
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }
}
