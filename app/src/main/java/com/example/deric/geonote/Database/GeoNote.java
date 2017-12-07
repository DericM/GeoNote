package com.example.deric.geonote.Database;

import android.location.Location;

/**
 * Created by Deric on 17/12/06.
 */

public class GeoNote {

    private int id;
    private String Note;
    private double Latitude;
    private double Longitude;
    private String DateTime;

    public GeoNote(){}

    public GeoNote(String Note, double Latitude, double Longitude, String DateTime) {
        this.Note = Note;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.DateTime = DateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Image [id=" + id + ", Note=" + Note + ", Latitude=" + Latitude
                + ", Longitude=" + Longitude + ", DateTime=" + DateTime
                + "]";
    }


}
