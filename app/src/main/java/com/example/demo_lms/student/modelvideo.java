package com.example.demo_lms.student;

public class modelvideo {
    public modelvideo()
    {}
    String name, Location;

    public modelvideo(String name, String location,String Course) {
        this.name = name;
        Location = location;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }


}
