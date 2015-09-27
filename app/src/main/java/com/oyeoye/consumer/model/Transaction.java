package com.oyeoye.consumer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class Transaction implements Serializable {

    public static final String PAYMENT_STATUS_APPROVED = "APPROVED";

    public static final int TRANSACTION_STATUS_PAID = 0;
    public static final int TRANSACTION_STATUS_PICKED_UP = 1;
    public static final int TRANSACTION_STATUS_MERCHANT_PAID = 2;

    @SerializedName("_id")
    private String id;

    @SerializedName("client")
    private String clientId;

    private String paymentAuthorizationId;
    private String paymentStatus;
    private String key;
    private Deal deal;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPaymentAuthorizationId() {
        return paymentAuthorizationId;
    }

    public void setPaymentAuthorizationId(String paymentAuthorizationId) {
        this.paymentAuthorizationId = paymentAuthorizationId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
