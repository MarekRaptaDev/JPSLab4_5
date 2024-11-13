package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class serverWWW {
    private final static String wwwFolder = System.getProperty("user.dir") + File.separator + "www"+File.separator;
    private final static String error404 = wwwFolder+"error404.html";
    private static AtomicInteger clientIdCounter = new AtomicInteger(1);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server listening on port 8080");

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

                String Request = in.readLine();
                if (Request != null && Request.startsWith("GET")) {


                    String[] partsOfRequest =Request.split(" ");
                    String[] filePath = partsOfRequest[1].split("/");
                    String requestedFileName ="";
                    if(filePath.length>1){
                         requestedFileName = filePath[filePath.length-1];
                    }else {
                        requestedFileName = "index.html";
                    }
                    File file = new File(wwwFolder + requestedFileName );
                    if (file.exists()&&file.isFile()){

                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-Type: text/html");
                        out.println("Content-Length: " + file.length());
                        out.println();
                        Files.copy(file.toPath(),clientSocket.getOutputStream());
                        clientSocket.getOutputStream().flush();
                    }else {

                        File errorFile = new File(error404);
                        out.println("HTTP/1.1 404 Not Found");
                        out.println("Content-Type: text/html");
                        out.println("Content-Length: "+errorFile.length());
                        out.println();
                        Files.copy(errorFile.toPath(),clientSocket.getOutputStream());
                        clientSocket.getOutputStream().flush();
                    }
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



