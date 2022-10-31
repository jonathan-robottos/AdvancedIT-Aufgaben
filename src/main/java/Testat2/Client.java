package Testat2;

import java.io.*;
import java.net.*;
import java.security.*;

public class Client {
    public static final int PORT = 7777;

    private static KeyPair kp;
    public static  PublicKey pub;
    public static void main(String[] args) {
        Socket client = null;

        SecurityService secserv = new SecurityService();
        kp = secserv.gnerateKey();
        pub = kp.getPublic();
        PrivateKey priv = kp.getPrivate();

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

                //receive from server
                //byte[] response = networkIn.readLine().getBytes();
                System.out.println(networkIn.readLine());

                client.close();
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }
}
