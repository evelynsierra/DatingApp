package com.example.datingapp.util;

public class ProfileId {
    String profileId;
    public <T extends ProfileId> T withId(String s){
        this.profileId = s;
        return (T) this;
    }
}
