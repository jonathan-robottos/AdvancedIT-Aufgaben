package Testat2;

import java.net.*;
import java.io.*;
import java.security.*;
import java.math.BigInteger;
import java.util.Random;

public class Server {
   public static final int PORT = 7777;
   public static final String PATH = "D:\\Development\\AdvancedIT-Aufgaben\\src\\main\\java\\Testat2\\Messages\\";

   public static void main(String[] args) {

      //declare variables
      Socket client = null;

      try {
         //server setup
         ServerSocket server = new ServerSocket(PORT);
         System.out.println("** Waiting for client at " + server.getLocalPort());

         while (true){
            try{
               //connect with client
               client = server.accept();
               System.out.println("** Connected to client: " + client.getRemoteSocketAddress());

               //receive from client
               BufferedReader clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
               PrintWriter clientOut = new PrintWriter(client.getOutputStream());

               String request = clientIn.readLine();
               String[] split = request.split(" ");

               if(split.length > 0){

                  if(split[0].equals("SAVE")){
                     //create key
                     String hash = null;
                     String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*(),.<>;:/?=+-_";
                     StringBuilder sb = new StringBuilder();
                     Random random = new Random();
                     int length = 16;
                     for (int i = 0; i < length; i++) {
                        int index = random.nextInt(symbols.length());
                        char randomChar = symbols.charAt(index);
                        sb.append(randomChar);
                     }
                     hash = hashKey(sb.toString());

                     //send key to client
                     String key = sb.toString();
                     clientOut.println("KEY " + key);

                     //create File
                     File myFile = new File(PATH + hash);
                     FileWriter myWriter = new FileWriter(myFile);
                     myWriter.write(request.substring(5));
                     myWriter.close();
                  } else if (split[0].equals("GET")) {
                     String key = split[1];
                     String hash = hashKey(key);
                     String fileUrl = PATH + hash;

                     File myFile = new File(fileUrl);

                     if(myFile.exists()){
                        BufferedReader myReader = new BufferedReader(new FileReader(myFile));
                        clientOut.println("OK " + myReader.readLine());
                     }else{
                        clientOut.println("FAILED");
                     }
                  } else {
                     clientOut.println("Command not found, please check your spelling");
                  }

                  clientOut.flush();

               }
            } catch (NoSuchAlgorithmException e) {
               throw new RuntimeException(e);
            } catch (SocketException e){
               System.out.println("Socket Error");
            } finally {
               if(client != null) client.close();
            }
         }
      }catch (IOException e) {
         throw new RuntimeException(e);
      } finally {
         if (client != null) {
            try {
               client.close();
            } catch (IOException e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   public static String hashKey(String input) throws NoSuchAlgorithmException {
      MessageDigest md = MessageDigest.getInstance("SHA-256");

      byte [] messageDigest = md.digest(input.getBytes());

      BigInteger bigInt = new BigInteger(1, messageDigest);

      return bigInt.toString(16);
   }
}
