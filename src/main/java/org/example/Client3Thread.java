package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class Client3Thread implements Runnable{
    @Override
    public void run() {
        try (Socket s = new Socket("localhost", 4443)) {

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String message;
            while ((message=in.readLine())!= null)
            {
                System.out.println(message);
            }

        }catch (IOException e)
        {
            System.out.println("Error in connestion, server might be down");
        }
    }
}
