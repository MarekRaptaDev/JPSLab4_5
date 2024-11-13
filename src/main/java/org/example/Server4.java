package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class Server4 {
    private static AtomicInteger clientIdCounter = new AtomicInteger(1);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4444)) {
            System.out.println("Server listening on port 4444");

            while (true) {

                Socket clientSocket = serverSocket.accept();

                int clientId = clientIdCounter.getAndIncrement();


                new Thread(new ClientHandler(clientSocket, clientId)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private int clientId;

        public ClientHandler(Socket clientSocket, int clientId) {
            this.clientSocket = clientSocket;
            this.clientId = clientId;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                out.println("Hello client #" + clientId);
                String message;
                while ((message = in.readLine()) != null) {
                    out.println("Client #" + clientId + ": " + message);
                }

            } catch (IOException e) {
                System.out.println("Connection with client #" + clientId + " closed.");
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


