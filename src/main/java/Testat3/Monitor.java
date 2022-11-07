package Testat3;

public class Monitor {
    private int readerCounter;
    private boolean writing;
    private int activeWriter;

    public Monitor(){
        readerCounter = 0;
        writing = false;
        activeWriter = 0;
    }

    public synchronized void startRead(){
        while(writing || (activeWriter > 0)){ //warten, sollte jemand schreiben oder jemand schreiben will
            try {
                wait();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        readerCounter++;
    }

    public synchronized void endRead(){
        readerCounter--;
        notifyAll();
    }

    public synchronized void startWrite(){
        activeWriter++;
        while (readerCounter > 0 || writing){ //warten, sollte bereits geschrieben oder gelesen werden
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        activeWriter--;
        writing = true;
    }

    public synchronized void endWrite(){
        writing = false;
        notifyAll();
    }
}
