package com.example.srmhack1;

public class Driver {
    String username,user_type,start_location,end_location,start_time,end_time;
    int nop;

    public Driver(String username, String user_type, String start_location, String end_location, String start_time, String end_time, int nop) {
        this.username = username;
        this.user_type = user_type;
        this.start_location = start_location;
        this.end_location = end_location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.nop = nop;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getStart_location() {
        return start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getEnd_location() {
        return end_location;
    }

    public void setEnd_location(String end_location) {
        this.end_location = end_location;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getNop() {
        return nop;
    }

    public void setNop(int nop) {
        this.nop = nop;
    }
}
