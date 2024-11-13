package org.example;

import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONObject;
import org.json.JSONArray;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.io.*;
import java.net.URL;
import java.util.Scanner;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ClientConnect {
    public static void SocetCreate(String addres) {
        try (Socket s = new Socket(addres, 13)) {
            Scanner in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8);
            while (in.hasNextLine()) {
                System.out.println(in.nextLine());

                System.out.println(s.getInetAddress());
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ConnectionTest(String addres) throws IOException {
        //zad2
        URL url = new URL(addres);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        conn.setRequestProperty("Content-Type", "text/html");
        conn.connect();
        try (Scanner in = new Scanner(conn.getInputStream())) {
//            System.out.println("Saving to the file.");
//            try(FileWriter fw = new FileWriter("homepage.html")) {
//                while(in.hasNextLine()) {
//                    fw.write(in.nextLine());
//                }
            while (in.hasNextLine()) {
                System.out.println(in.nextLine());
            }
        }
    }

    public static void getPosts(String addres) throws IOException {
        URL url = new URL(addres + "/posts");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        conn.connect();
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner s = new Scanner(new InputStreamReader(conn.getInputStream()));
            while (s.hasNextLine()) {
                System.out.println(s.nextLine());
            }
        } else {
            System.out.println("Connection error. Please try again.");
            return;
        }


    }

    public static void PostAndReturn(String addres, String jsonInputString) throws IOException {
        URL url = new URL(addres);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        try (OutputStream ou = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            ou.write(input, 0, input.length);
        }
        System.out.println(conn.getResponseMessage());
    }

    public static void KrakowAirCond() throws IOException {

        URL url = new URL("https://api.open-meteo.com/v1/forecast?latitude=50.0614&longitude=19.9366&hourly=temperature_2m&timeformat=unixtime");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder sb = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();


            JSONObject jsonObj = new JSONObject(sb.toString());
            JSONObject hourly = jsonObj.getJSONObject("hourly");
            JSONArray times = hourly.getJSONArray("time");
            JSONArray temperatures = hourly.getJSONArray("temperature_2m");


            long unixTimestamp = Instant.now().getEpochSecond();
            LocalDateTime now = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimestamp), ZoneId.of("UTC"));


            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH");
            String currentTimeString = now.format(format);


            double currTemp = 0.0;


            for (int i = 0; i < times.length(); i++) {

                long timestamp = times.getLong(i);
                LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of("UTC"));


                String timeString = time.format(format);

                if (timeString.equals(currentTimeString)) {
                    currTemp = temperatures.getDouble(i);
                    break;
                }
            }


            System.out.println("Temperature in Krakow now (" + currentTimeString + ") is " + currTemp + "°C");

        } else {
            System.out.println("Connection error. Please try again.");
        }

    }

    public static void Downloader(String addres) throws IOException {

        URL url = new URL(addres);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        String[] getName = addres.split(String.valueOf("/"));
        String fileName = getName[getName.length - 1];

        String contentDisposition = connection.getHeaderField("Content-Disposition");


        String savePath = System.getProperty("user.dir") + File.separator + fileName;


        try (InputStream inputStream = connection.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(savePath)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File saved at: " + savePath);
        } catch (IOException e) {
            System.err.println("During downloading of file error: " + e.getMessage() + " has occurred");
        } finally {
            connection.disconnect();
        }
    }

    public static void Mapper(HomeAddres home) throws IOException {
        final String USER_AGENT = "YourAppName/1.0 (your_email@example.com)";
        double lat = 91;
        double lon = 181;
        String address = "";
        String url = "https://nominatim.openstreetmap.org/search?country=Poland&city=Warsaw&street=Krakowskie+Przedmieście+15&format=json";
        if (!(home.getHouseNumber().isBlank() && home.getStreet().isBlank() && home.getTown().isBlank() && home.getCountry().isBlank())) {
            String encodedStreet = URLEncoder.encode(home.getStreet() + " " + home.getHouseNumber(), "UTF-8");
            String encodedTown = URLEncoder.encode(home.getTown(), "UTF-8");
            String encodedCountry = URLEncoder.encode(home.getCountry(), "UTF-8");
            url = "https://nominatim.openstreetmap.org/search?country=" + encodedCountry + "&city=" + encodedTown + "&street=" + encodedStreet + "&format=json";
            address = home.getCountry() + "_" + home.getTown() + "_" + home.getStreet() + "_" + home.getHouseNumber();
        } else if (!(home.getHouseNumber().isBlank() && home.getTown().isBlank() && home.getCountry().isBlank())) {
            String encodedStreet = URLEncoder.encode(home.getStreet() + " " + home.getHouseNumber(), "UTF-8");
            String encodedTown = URLEncoder.encode(home.getTown(), "UTF-8");
            String encodedCountry = URLEncoder.encode(home.getCountry(), "UTF-8");
            url = "https://nominatim.openstreetmap.org/search?country=" + encodedCountry + "&city=" + encodedTown + "&street=" + encodedStreet + "&format=json";
            address = home.getCountry() + "_" + home.getTown() + "_" + home.getHouseNumber();
        } else {
            url = "https://nominatim.openstreetmap.org/reverse?lat=" + home.getLatitude() + "&lon=" + home.getLongtitude() + "&format=json";
            address = "lat=" + home.getLatitude() + " lon=" + home.getLongtitude();
        }
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Response from API: " + response.toString());

            JSONArray jsonArray = new JSONArray(response.toString());
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                lat = jsonObject.getDouble("lat");
                lon = jsonObject.getDouble("lon");
            }
            if (lon >= -180 && lon <= 180 && lat >= -90 && lat <= 90) {
                String htmlContent = "<!DOCTYPE html>\n<html>\n<head>\n" +
                        "<title>Map for " + address + "</title>\n" +
                        "<meta charset=\"UTF-8\">\n" +
                        "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.css\" />\n" +
                        "<script src=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.js\"></script>\n" +
                        "</head>\n<body>\n" +
                        "<h1>Map for " + address + "</h1>\n" +
                        "<div id=\"map\" style=\"width: 100%; height: 500px;\"></div>\n" +
                        "<script>\n" +
                        "var map = L.map('map').setView([" + lat + ", " + lon + "], 15);\n" +
                        "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {\n" +
                        "    attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'\n" +
                        "}).addTo(map);\n" +
                        "L.marker([" + lat + ", " + lon + "]).addTo(map);\n" +
                        "</script>\n" +
                        "</body>\n</html>";
                String filePath = System.getProperty("user.dir") + File.separator + "map.html";
                try (FileWriter fileWriter = new FileWriter(filePath)) {
                    fileWriter.write(htmlContent);
                    System.out.println("HTML map saved to " + filePath);
                }
            }else {
                System.out.println("Something went wrong, please try again.");
            }
        } else {
                System.out.println("During connection error has occurred");
            }
        }
    }

