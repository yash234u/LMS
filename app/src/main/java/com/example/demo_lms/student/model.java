package com.example.demo_lms.student;

public class model {

    public model()
    {}
    String name, Location,Course;

    public model(String name, String location,String Course) {
        this.name = name;
        Location = location;
        this.Course=Course;
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

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }
}
