package com.oyeoye.consumer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class Deal implements Serializable {

    @SerializedName("_id")
    private String id;
    private String title;
    private String description;
    private String image;
    private double price;

    public Deal() {
    }

    public Deal(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
