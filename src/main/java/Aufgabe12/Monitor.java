package Aufgabe12;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Monitor {
    private boolean[] eating;
    private int max;

    public Monitor(int max){
        this.max = max;
        eating = new boolean[max];
        Arrays.fill(eating, false);
    }
    public synchronized void startEating(int id) {
        while(eating[(id + max -1) % max] || eating[(id + 1) % max] ){
            System.out.println("Philosoph " + id + " wartet");
            try{ wait();} catch (Exception e){}
        }

        eating[id] = true;
        System.out.println("Philosoph " + id + " isst");
    }

    public synchronized void stopEating(int id) {
        eating[id] = false;
        System.out.println("Philosoph " + id + " wartet");
        notifyAll();
    }

}
