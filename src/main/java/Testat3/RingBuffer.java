package Testat3;

import java.net.*;

public class RingBuffer {
    DatagramPacket[] buffer;
    int nextFree;
    int ctr;
    int nextFull;
    //Semaphore --> Monitor gedöns

    public RingBuffer(int size){
        this.buffer = new DatagramPacket[size];
        nextFree = 0;
        ctr = 0;
        nextFull = 0;
        //mutex semaphore --> Monitor
    }

    public synchronized void append(DatagramPacket dp){
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(ctr == buffer.length){
            buffer[nextFree] = dp;
            nextFree = (nextFree + 1) % buffer.length;
            ctr ++;
        }
        notify();
    }

    public synchronized DatagramPacket remove(){
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        DatagramPacket dp = null;
        if(ctr == 0){
            dp = buffer[nextFree];
            nextFull = (nextFull + 1) % buffer.length;
            ctr ++;
        }
        notify();
        return dp;
    }
}
