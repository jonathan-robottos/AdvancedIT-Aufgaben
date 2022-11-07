package Testat3;

import java.net.*;
import java.io.*;

public class FileServer {
    public static final int MAXSIZE = 65507;
    public static int port = 5999;
    private static Dispatcher dispatcher;
    public static RingBuffer ringBuffer;
    private static int NUMBER_OF_THREADS = 4;


    public static void main(String[] args) {
        //declare variables
        DatagramSocket ds = null;
        DatagramPacket dp = null;


        //setup server
        try {
            ds = new DatagramSocket(port);
            ringBuffer = new RingBuffer(NUMBER_OF_THREADS);
            //dispatcher = new Dispatcher();
            //workers = new Worker[NUMBER_OF_THREADS];
            System.out.println("Server successfully started on port " + port);


            while (true) { //server loop
                try {
                    dp = new DatagramPacket(new byte[MAXSIZE], MAXSIZE);
                    ds.receive(dp);

                    ringBuffer.append(dp); //Add dp to ring buffer

                    for(int i = 0; i < NUMBER_OF_THREADS - 1; i++){
                        Worker worker = new Worker(i);
                        worker.start();
                    }
                    //dispatcher.initWorkers(NUMBER_OF_THREADS);

                    //System.out.println("Add dp to ring buffer");
                    //System.out.println(ringBuffer.buffer.length);

                } catch (Exception e) { // try 2
                    e.printStackTrace();
                }

            }//while
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//main
}// class
