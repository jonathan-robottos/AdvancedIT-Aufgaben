package Testat3;

import java.net.*;

public class RingBuffer {
    DatagramPacket[] buffer;
    int nextFree;
    int nextFull;

    public RingBuffer(int size){
        this.buffer = new DatagramPacket[size];
        this.nextFree = 0;
        this.nextFull = 0;
    }

    public synchronized void append(DatagramPacket dp){

        while (nextFree == ((nextFull + 1) % buffer.length)){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        buffer[nextFree] = dp;
        nextFree = (nextFree + 1) % buffer.length;

        notifyAll();
    }

    public synchronized DatagramPacket remove(){

        while (nextFull == nextFree){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        DatagramPacket dp = buffer[nextFull];
        nextFull = (nextFull + 1) % buffer.length;

        notifyAll();
        return dp;
    }
}
