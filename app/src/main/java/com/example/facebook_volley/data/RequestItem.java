package com.example.facebook_volley.data;

import java.io.Serializable;

/**
 * Created by PHI LONG on 17/06/2015.
 */
public class RequestItem implements Serializable{
    private int id;
    private String name, profilePic,name_user;

    public RequestItem() {
    }

    public RequestItem(int id, String name, String profilePic) {
        super();
        this.id = id;
        this.name = name;
        this.profilePic = profilePic;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
