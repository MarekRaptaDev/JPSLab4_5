package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client4Thread implements Runnable{
    @Override
    public void run() {
        try (Socket s = new Socket("localhost", 4444)) {

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(),true);
            Scanner scan = new Scanner(System.in);
            Thread listen = new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Server connection closed");
                }
            });
            Thread post = new Thread(()->{
                while (true) {
                    String outMessage = scan.nextLine();
                    out.println(outMessage);
                    if (outMessage.equalsIgnoreCase("exit")) {
                        try {
                            s.close();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        post.start();
        listen.start();
        post.join();
        listen.join();
        }catch (IOException e)
        {
            System.out.println("Error in connestion, server might be down");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
