package Testat3;

import java.net.*;
import java.io.*;

public class FileServer {
    public static final int MAXSIZE = 65507;
    private static int port = 5999;
    private static Dispatcher dispatcher;
    private static RingBuffer ringBuffer;
    private static int NUMBER_OF_THREADS = 4;


    public static void main(String[] args) {
        // decla re va ria ble s f i r s t :
        DatagramSocket ds = null;
        DatagramPacket dp = null;

        // setup server
        try { // 1
            ds = new DatagramSocket(port);
            ringBuffer = new RingBuffer(NUMBER_OF_THREADS);
            dispatcher = new Dispatcher();
            System.out.println("Server successfully started on port " + port);

            while (true) { // wait for and process client requests
                try { //2
                    dp = new DatagramPacket(new byte[MAXSIZE], MAXSIZE);
                    ds.receive(dp);

                    ringBuffer.append(dp);

                    dispatcher.allocate(ringBuffer, NUMBER_OF_THREADS, port);

                } catch (Exception e) { // try 2
                    e.printStackTrace();
                }

            }//while
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//main
}// class
