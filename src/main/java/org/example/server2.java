package org.example;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class server2 {
    public static void start() {
        try (ServerSocket serverSocket = new ServerSocket(4442)) {
            System.out.println("Listening on port 4442");
            while (true)
            {
                try (Socket clientSocket = serverSocket.accept();

                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));



                    String messageFromClient = in.readLine();
                    String response = new StringBuilder(messageFromClient).reverse().toString();
                    out.println(response);



                    Thread.sleep(1000);
                    out.println("Come back again!");

                    Thread.sleep(1000);
                }
                System.out.println("Finished");
            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        start();
    }
}
