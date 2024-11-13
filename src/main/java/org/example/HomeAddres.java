package org.example;

import javax.print.attribute.HashPrintJobAttributeSet;

public class HomeAddres {
    private String Country = "", town = "", street="", houseNumber="";
    private double latitude, longtitude;

    public String getCountry() {
        return Country;
    }

    public String getTown() {
        return town;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public HomeAddres(String c, String t, String s, String hN)
    {
        this.Country = c;
        this.town = t;
        this.street = s;
        this.houseNumber = hN;
    }
    public HomeAddres(String c, String t, String hN)
    {
        this.Country = c;
        this.town = t;
        this.houseNumber = hN;
    }
    public HomeAddres(double lat, double lon)
    {
        this.latitude = lat;
        this.longtitude = lon;
    }
}
