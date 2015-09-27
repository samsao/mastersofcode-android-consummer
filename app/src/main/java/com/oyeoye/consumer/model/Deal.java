package com.oyeoye.consumer.model;

import java.io.Serializable;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class Deal implements Serializable {

    private String name;
    private double price;

    public Deal() {
    }

    public Deal(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
