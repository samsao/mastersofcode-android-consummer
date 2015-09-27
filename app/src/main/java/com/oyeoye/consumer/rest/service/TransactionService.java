package com.oyeoye.consumer.rest.service;

import com.oyeoye.consumer.model.Transaction;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by lukasz
 */
public interface TransactionService {

    @GET("/client/transaction")
    void load(Callback<List<Transaction>> callback);

    @POST("/client/transaction/add")
    @FormUrlEncoded
    void add(@Field("deal") String dealId, @Field("token") String cardToken, @Field("quantity") int quantity, Callback<Transaction> callback);
}
