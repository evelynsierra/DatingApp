package com.example.datingapp.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile extends ProfileId{

    private String name;

    private String imageUrl;

    private Integer age;

//    private String location;

    public Profile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
}
