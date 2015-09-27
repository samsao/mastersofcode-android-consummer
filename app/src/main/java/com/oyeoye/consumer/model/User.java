package com.oyeoye.consumer.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class User {

    @SerializedName("_id")
    private String id;
    private String phone;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
