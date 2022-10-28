package Aufgabe12;

public class Philosophen extends Thread{
    private int id;
    private Monitor table;

    //Konstruktor
    public Philosophen(int id, Monitor table){
        this.id = id;
        this.table = table;
    }

    public void run(){
        for(int k = 0; k < 4; k++){
            try {
                Thread.sleep(1000 + id * 200L);
            }catch(Exception e) {}

            table.startEating(id);

            try{
                Thread.sleep((1000 + id * 200L));
            }catch (Exception e){}

            table.stopEating(id);
        }
    }

    public static void main(String[] args) {
        int max = 5;
        Monitor table = new Monitor(max);
        Philosophen[] p = new Philosophen[max];

        for(int id = 0; id < max; id ++){
            p[id] = new Philosophen(id, table);
            p[id].start();
        }

        for(int id = 0; id < max; id++){
            try{
                p[id].join();
            }catch (Exception e){}
            System.out.println("Fertig: alle satt :)");
        }
    }
}
