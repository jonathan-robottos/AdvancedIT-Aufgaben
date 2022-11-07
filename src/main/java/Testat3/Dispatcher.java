package Testat3;

import java.net.DatagramPacket;

public class Dispatcher {

    public void allocate(RingBuffer ringBuffer, int numberOfThreads, int port){
        if (ringBuffer.buffer.length != 0){
            for(int i = 0; i < numberOfThreads; i++){
                DatagramPacket dp = ringBuffer.remove();
                Worker worker = new Worker(i, dp, port);
                worker.start();
            }
        }

    }
}
