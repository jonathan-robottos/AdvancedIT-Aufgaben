package Aufgabe14;
import java.io.*;

public class MyFile {
    public String read(String filename, int lineNo) throws FileNotFoundException, IOException {
        BufferedReader fileIn = new BufferedReader(
                new FileReader(filename));

        String zeile = null;

        for(int i = 1; i <= lineNo; i++)
            zeile = fileIn.readLine();

        return zeile;
    }

    public void write(String filename, int lineNo, String data) throws IOException{
        PrintWriter fileOut = new PrintWriter(
                new FileWriter(filename));

        String zeile = null;

        for(int i = 1; i <= lineNo; i++)
            fileOut.println(data);
    }


}
