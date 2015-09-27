package com.oyeoye.consumer.rest;

import com.google.gson.GsonBuilder;
import com.oyeoye.consumer.App;
import com.oyeoye.consumer.AppConstants;
import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.manager.SessionManager;
import com.oyeoye.consumer.rest.gson.DealTypeAdapterFactory;
import com.oyeoye.consumer.rest.gson.TransactionTypeAdapterFactory;
import com.oyeoye.consumer.rest.service.DealService;
import com.oyeoye.consumer.rest.service.TransactionService;
import com.oyeoye.consumer.rest.service.UserService;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Inject;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import timber.log.Timber;

/**
 * Created by lukasz on 01/12/14.
 */
@DaggerScope(App.class)
public class RestClient {

    private UserService userService;
    private TransactionService transactionService;
    private DealService dealService;

    @Inject
    public RestClient(final SessionManager sessionManager) {
        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(StationState.class, new StationStateDeserializer());

        OkHttpClient client = new OkHttpClient();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(AppConstants.API_URL)
                .setConverter(new GsonConverter(gsonBuilder
                        .registerTypeAdapterFactory(new TransactionTypeAdapterFactory())
                        .registerTypeAdapterFactory(new DealTypeAdapterFactory())
                        .create()))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        if (sessionManager.isLogged()) {
                            request.addHeader("Authorization", "Bearer " + sessionManager.getUser().getToken());
                            Timber.d("Add header auth: %s", sessionManager.getUser().getToken());
                        }
                    }
                });

        RestAdapter restAdapter = builder.build();

        userService = restAdapter.create(UserService.class);
        transactionService = restAdapter.create(TransactionService.class);
        dealService = restAdapter.create(DealService.class);
    }

    public UserService getUserService() {
        return userService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public DealService getDealService() {
        return dealService;
    }
}
