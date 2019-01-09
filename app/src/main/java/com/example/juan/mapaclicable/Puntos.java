package com.example.juan.mapaclicable;

public class Puntos {
    String puntos, lat, lon;

    public Puntos(String puntos, String lat, String lon) {
        this.puntos = puntos;
        this.lat = lat;
        this.lon = lon;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
