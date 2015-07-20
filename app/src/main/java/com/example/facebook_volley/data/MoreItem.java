package com.example.facebook_volley.data;

/**
 * Created by PHI LONG on 13/07/2015.
 */
public class MoreItem {
    String name;
    int avata;
public MoreItem(int avata, String name){
    this.avata=avata;
    this.name=name;

    }
    public int getAvata() {
        return avata;
    }

    public void setAvata(int avata) {
        this.avata = avata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
