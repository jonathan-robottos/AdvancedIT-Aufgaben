package Testat3;

import java.net.*;
import java.io.*;

public class FileServer {
    public static final int MAXSIZE = 65507;
    private static int port = 5999;
    private static final String wdir = "D:\\Development\\AdvancedIT-Aufgaben\\src\\main\\java\\Testat3\\Messages\\"; //PATH for storing files

    public static void main(String[] args) {
        // decla re va ria ble s f i r s t :
        String dpData = "";
        String answer = "∗∗∗ ERROR 900: unknown error ";
        String filename = "";
        String newData = "";
        int lineNo = -1;
        MyFile f = null;
        String param[] = null;
        String param2[] = null;
        DatagramSocket ds = null;
        DatagramPacket dp = null;
        DatagramPacket dp2 = null;

        // setup server
        try { // 1
            ds = new DatagramSocket(port);
            System.out.println("Server successfully started on port " + port);
            while (true) { // wait for and process client requests
                try { //2
                    dp = new DatagramPacket(new byte[MAXSIZE], MAXSIZE);
                    ds.receive(dp);
                    dpData = new String(dp.getData(), 0, dp.getLength()).trim();
                    if (dpData.startsWith("READ ")) {
                        try {
                            param = dpData.split(" ", 2);
                            param2 = param[1].split(",", 2);
                            filename = param2[0].trim();
                            lineNo = Integer.parseInt(param2[1].trim());
                            f = new MyFile(wdir + filename);
                            answer = f.read(lineNo);
                        } catch (Exception e) {
                            answer = "∗∗∗ ERROR 901: bad READ command ";
                            throw new Exception(e);
                        } // catch
                    } else if (dpData.startsWith("WRITE ")) {
                        try {
                            param = dpData.split(" ", 2);
                            param2 = param[1].split(",", 3);
                            filename = param2[0].trim();
                            lineNo = Integer.parseInt(param2[1].trim());
                            newData = param2[2];
                            f = new MyFile(wdir + filename);
                            answer = f.write(lineNo, newData);
                        } catch (Exception e) {
                            answer = "∗∗∗ ERROR 901: bad WRITE command";
                            throw new Exception(e);
                        } // catch
                    } else {
                        answer = "∗∗ ERROR 902: unknown command";
                        throw new Exception("Unknown Command");
                    }
                } catch (Exception e) { // try 2
                    e.printStackTrace();
                }
                // send back result or error message
                try {
                    dp2 = new DatagramPacket(answer.getBytes(), answer.getBytes().length,
                            dp.getAddress(), dp.getPort());
                    ds.send(dp2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }//while
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//main
}// class
