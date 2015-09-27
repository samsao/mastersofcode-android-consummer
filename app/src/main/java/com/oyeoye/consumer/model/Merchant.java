package com.oyeoye.consumer.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class Merchant {

    @SerializedName("_id")
    private String id;

    private Place place;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
