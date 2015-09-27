package com.oyeoye.consumer.rest.service;

import com.oyeoye.consumer.model.Transaction;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by lukasz
 */
public interface TransactionService {

    @POST("/transaction/add")
    @FormUrlEncoded
    void add(@Field("deal") String dealId, @Field("token") String cardToken, @Field("quantity") int quantity, Callback<Transaction> callback);
}
