package Testat3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Worker extends Thread {
    private static final String wdir = "D:\\Development\\AdvancedIT-Aufgaben\\src\\main\\java\\Testat3\\Messages\\"; //PATH for storing files
    private int id;
    private int port;
    private DatagramPacket dp;

    public Worker(int id, DatagramPacket dp, int port) {
        this.id = id;
        this.dp = dp;
        this.port = port;
    }

    public void run() {
        String dpData = "";
        String param[] = null;
        String param2[] = null;
        String filename = "";
        int lineNo = -1;
        MyFile f = null;
        String answer = "∗∗∗ ERROR 900: unknown error ";
        String newData = "";
        DatagramPacket dp2 = null;

        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        try {
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

            // send back result or error message
            try {
                dp2 = new DatagramPacket(answer.getBytes(), answer.getBytes().length,
                        dp.getAddress(), dp.getPort());
                ds.send(dp2);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
