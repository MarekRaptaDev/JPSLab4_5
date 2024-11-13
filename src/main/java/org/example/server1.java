package org.example;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class server1 {
    public static void start() {
        try (ServerSocket serverSocket = new ServerSocket(4441)) {
            System.out.println("Listening on port 4441");
            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDate = dateFormat.format(new Date());


                out.println("Current Date and Time: " + currentDate);


                Thread.sleep(1000);
                out.println("Come back again!");

                Thread.sleep(1000);
            }
            System.out.println("Finished");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        start();
    }
}
