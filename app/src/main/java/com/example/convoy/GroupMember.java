package com.example.convoy;

import com.google.android.gms.maps.model.LatLng;

public class GroupMember {
    private String name;
    private LatLng location;
    private String userID;

    public GroupMember(String userName, double lat, double lng ,String ID) {
        this.name = userName;
        this.location = new LatLng(lat,lng);
        this.userID = ID;

    }

    public String getUserID() {return userID;}

    public void setUserID(String ID) {this.userID = ID; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }




}
