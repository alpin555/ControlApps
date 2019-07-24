package com.example.controlapps2.Model;

public class User3 {
    private String volt;
    private String am1;
    private String am2;
    private String am3;
    private String Date1;

    public User3(String v, String w1, String w2, String w3, String d){
        volt = v;
        am1 = w1;
        am2 = w2;
        am3 = w3;
        Date1 = d;
    }

    public User3(){

    }

    public String getVolt() {
        return volt;
    }

    public String getAm1() {
        return am1;
    }

    public String getAm2() {
        return am2;
    }

    public void setVolt(String volt) {
        this.volt = volt;
    }

    public void setAm1(String am1) {
        this.am1 = am1;
    }

    public void setAm2(String am2) {
        this.am2 = am2;
    }

    public String getDate1() {
        return Date1;
    }

    public void setDate1(String date1) {
        Date1 = date1;
    }

    public String getAm3() {
        return am3;
    }

    public void setAm3(String am3) {
        this.am3 = am3;
    }
}
