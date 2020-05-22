import java.net.*;
import java.io.*;

public class Socket {

     public static void main(String[] args) {
         try (ServerSocket serverSocket = new ServerSocket(666)) {

             System.out.println("Server is listening on port " + 666);

             while (true) {
                 java.net.Socket socket = serverSocket.accept();

                 // read data from the client

                 // send data to the client


                 socket.close();
             }


         }
         catch(IOException ex){
                 System.out.println("Server exception: " + ex.getMessage());
                 ex.printStackTrace();
         }
     }

}
