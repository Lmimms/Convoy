package com.example.convoy;

public class Group {

    private String id;
    private String name;

    public Group(){}

    public Group(String i, String n){
        this.id = i;
        this.name = n;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

