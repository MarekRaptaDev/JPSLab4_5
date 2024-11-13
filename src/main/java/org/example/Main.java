package org.example;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {



        public static void main(String[] args) throws IOException {
            //ClientConnect.SocetCreate("time-a.nist.gov"); //zad1 klient
            //ClientConnect.ConnectionTest("https://ii.uken.krakow.pl/"); //zad2 klient
           // ClientConnect.getPosts("https://jsonplaceholder.typicode.com/");//zad3 klient
//            ClientConnect.PostAndReturn("https://jsonplaceholder.typicode.com/posts",
//                "{\"title\": \"foo\", \"body\": \"bar\"}"); //zad 4 klient
//                ClientConnect.KrakowAirCond();
//            ClientConnect.Downloader("http://mbprywata.ugu.pl/PythonGeany.pdf");
//            ClientConnect.Mapper(new HomeAddres("Poland","Warsaw","Krakowskie Przedmieście","15"));
//            try (Socket s = new Socket("127.0.0.1", 4441)) {
//                Scanner in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8);
//                while (in.hasNextLine()) {
//                    System.out.println(in.nextLine());
//                }
//            }catch (SocketException e)
//            {
//                System.out.println("Error in connestion, server might be down");
//            }
//            try (Socket s = new Socket("127.0.0.1",4442)){
//                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
//
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
//
//                Scanner scanner = new Scanner(System.in);
//                System.out.print("Insert message: ");
//                String message = scanner.nextLine();
//
//
//                out.println(message);
//
//
//                String response = in.readLine();
//                System.out.println("Response: " + response);
//
//            }catch (SocketException e)
//            {
//                System.out.println("Error in connestion, server might be down");
//            }
//            ExecutorService executorService = Executors.newFixedThreadPool(10);
//            for(int i = 0 ; i<10;i++)
//            {
//
//                executorService.execute(new Client3Thread());
//            }
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            for(int i = 0 ; i<10;i++)
            {

                executorService.execute(new Client4Thread());
            }



            }
        }



//        Napisz klienta który doda nowy POST { "title": "foo", "body": "bar" } i
//        wyświetli informacje zwrotną z serwera.
//
//        Napisz klienta który wyświetli aktualne informacje pogodowe dla Twojego miasta
//        (lub poziom zanieczyszczeń w powietrzu).
//
//        Napisz klienta do pobierania plików. Użytkownik powinien móc wprowadzić ścieżkę
//        URL do pliku do pobrania. Pliki zapisz w domyślnej lokalizacji.
//
//🏠 Projekt do domu: Napisz klienta który na podstawie informacji o adresie stworzy mapę.
// Adres i mapę zapisz w pliku html. Do zadania spróbuj wykorzystać dostępne OpenStreetMap API.
// (Ewentualnie zamiast adresu mogą być współrzędne).

