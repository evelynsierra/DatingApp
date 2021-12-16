package com.example.datingapp.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile extends ProfileId{

    private String name;

    private String img_url;

    private Integer age;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public Profile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
