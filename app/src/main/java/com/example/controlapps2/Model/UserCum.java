package com.example.controlapps2.Model;

public class UserCum {
    private Float wHour1;
    private Float wHour2;
    private Float wHour3;
    private String Date1;

    public UserCum(Float w1,Float w2, Float w3, String d) {
        wHour1 = w1;
        wHour2 = w2;
        wHour3 = w3;
        Date1 = d;
    }

    public UserCum() {
    }

    public Float getwHour1() {
        return wHour1;
    }

    public void setwHour1(Float wHour1) {
        this.wHour1 = wHour1;
    }

    public Float getwHour2() {
        return wHour2;
    }

    public void setwHour2(Float wHour2) {
        this.wHour2 = wHour2;
    }

    public Float getwHour3() {
        return wHour3;
    }

    public void setwHour3(Float wHour3) {
        this.wHour3 = wHour3;
    }

    public String getDate1() {
        return Date1;
    }

    public void setDate1(String date1) {
        Date1 = date1;
    }
}
