package com.example.srmhack1;

public class User {
    String email,username,password,password2,fullname,home_location,phone_number,vehicle_type,vehicle_number,status;
    int age;
    char gender;
    double rating,CO2,distance,fare_saved;
    public User () {}

    public User(String email, String username, String password, String password2, String fullname, String home_location, String phone_number,int age,char gender) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.password2 = password2;
        this.fullname = fullname;
        this.home_location = home_location;
        this.phone_number = phone_number;
        this.age=age;
        this.gender=gender;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getHome_location() {
        return home_location;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public String getStatus() {
        return status;
    }

    public int getAge() {
        return age;
    }

    public char getGender() {
        return gender;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setHome_location(String home_location) {
        this.home_location = home_location;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getCO2() {
        return CO2;
    }

    public void setCO2(double CO2) {
        this.CO2 = CO2;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
