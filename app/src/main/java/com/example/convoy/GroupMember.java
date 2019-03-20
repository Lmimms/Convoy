package com.example.convoy;

import com.google.android.gms.maps.model.LatLng;

public class GroupMember {
    private String name;
    private LatLng location;

    public GroupMember(String userName, double lat, double lng) {
        this.name = userName;
        this.location = new LatLng(lat,lng);

    }

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
