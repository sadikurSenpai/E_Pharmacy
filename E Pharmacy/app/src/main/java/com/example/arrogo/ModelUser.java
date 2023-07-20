package com.example.arrogo;

public class ModelUser {
    String name,occupation,location;

    public ModelUser(String name, String occupation,String location) {
        this.name = name;
        this.occupation = occupation;
        this.location=location;
    }

    public ModelUser() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
