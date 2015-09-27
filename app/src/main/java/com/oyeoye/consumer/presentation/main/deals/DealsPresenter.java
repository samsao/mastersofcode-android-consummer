package com.oyeoye.consumer.presentation.main.deals;

import android.os.Bundle;

import com.oyeoye.consumer.model.Deal;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.presentation.RootActivityPresenter;
import com.oyeoye.consumer.presentation.masterpass.stackable.MasterpassStackable;
import com.oyeoye.consumer.presentation.payment.stackable.PaymentStackable;
import com.oyeoye.consumer.rest.RestClient;

import java.util.List;

import architect.Navigator;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class DealsPresenter extends AbstractPresenter<DealsView> {

    private final RootActivityPresenter activityPresenter;
    private final RestClient restClient;

    public DealsPresenter(RootActivityPresenter activityPresenter, RestClient restClient) {
        this.activityPresenter = activityPresenter;
        this.restClient = restClient;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        restClient.getDealService().load(new Callback<List<Deal>>() {
            @Override
            public void success(List<Deal> deals, Response response) {
                if (!hasView()) return;
                getView().show(deals);
            }

            @Override
            public void failure(RetrofitError error) {
                if (!hasView()) return;
                Timber.d("Error: %s", error.getMessage());
            }
        });
    }

    public void dealClick(Deal deal, int buyMode) {
        if (buyMode == DealsView.BUY_NORMAL) {
            Navigator.get(getView()).show(new PaymentStackable(deal));
        } else {
            Navigator.get(getView()).show(new MasterpassStackable(deal));
        }
    }
}
