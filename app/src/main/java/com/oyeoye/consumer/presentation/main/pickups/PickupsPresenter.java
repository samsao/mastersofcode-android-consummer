package com.oyeoye.consumer.presentation.main.pickups;

import android.os.Bundle;

import com.oyeoye.consumer.model.Transaction;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.rest.RestClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class PickupsPresenter extends AbstractPresenter<PickupsView> {

    private final RestClient restClient;

    public PickupsPresenter(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        load();
    }

    public void load() {
        getView().showLoading();
        restClient.getTransactionService().load(new Callback<List<Transaction>>() {
            @Override
            public void success(List<Transaction> transactions, Response response) {
                if (!hasView()) return;
                getView().show(transactions);
            }

            @Override
            public void failure(RetrofitError error) {
                if (!hasView()) return;
            }
        });
    }
}
