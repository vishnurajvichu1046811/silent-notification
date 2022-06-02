package com.example.silentnotification.models;

public class LocationData {
    public String userName;
    public String latitude;
    public String longitude;
    public String altitude;
    public String dateTime;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public LocationData(String userName, String latitude, String longitude,String altitude, String dateTime) {
        this.userName = userName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.dateTime = dateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
