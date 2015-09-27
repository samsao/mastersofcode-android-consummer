package com.oyeoye.consumer.presentation.payment;

import android.os.Bundle;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.manager.SessionManager;
import com.oyeoye.consumer.model.Deal;
import com.oyeoye.consumer.model.Transaction;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.presentation.ActivityContainerComponent;
import com.oyeoye.consumer.presentation.RootActivityPresenter;
import com.oyeoye.consumer.rest.RestClient;
import com.simplify.android.sdk.Card;
import com.simplify.android.sdk.CardToken;
import com.simplify.android.sdk.Simplify;

import architect.Navigator;
import architect.robot.AutoStackable;
import architect.robot.FromPath;
import autodagger.AutoComponent;
import autodagger.AutoExpose;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoStackable(
        component = @AutoComponent(includes = ActivityContainerComponent.class),
        pathWithView = PaymentView.class
)
@DaggerScope(PaymentPresenter.class)
@AutoExpose(PaymentPresenter.class)
public class PaymentPresenter extends AbstractPresenter<PaymentView> {

    private final Deal deal;

    private final RootActivityPresenter activityPresenter;
    private final SessionManager sessionManager;
    private final RestClient restClient;

    public PaymentPresenter(@FromPath Deal deal, RootActivityPresenter activityPresenter, SessionManager sessionManager, RestClient restClient) {
        this.deal = deal;
        this.activityPresenter = activityPresenter;
        this.sessionManager = sessionManager;
        this.restClient = restClient;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        getView().show(deal);
    }

    public void closeClick() {
        Navigator.get(getView()).back();
    }

    public void chargeClick(Card card) {
        if (card == null) return;

        getView().showLoading();
        Simplify.createCardToken(card, new CardToken.Callback() {
            @Override
            public void onSuccess(CardToken token) {
                if (!hasView()) return;
                Timber.d("Success card token: %s", token.getId());
                processToken(token);
            }

            @Override
            public void onError(Throwable throwable) {
                if (!hasView()) return;
                getView().showFailure();
            }
        });
    }

    private void processToken(CardToken token) {
        Timber.d("Post with deal: %s", deal.getId());
        restClient.getTransactionService().add(deal.getId(), token.getId(), 1, new Callback<Transaction>() {

            @Override
            public void success(Transaction transaction, Response response) {
                if (!hasView()) return;
                Timber.d("SUCCESS: %s", transaction);
                getView().close();
                Navigator.get(getView()).back(true);
            }

            @Override
            public void failure(RetrofitError error) {
                if (!hasView()) return;
                Timber.d("Error: %s", error.getMessage());
                getView().showFailure();
            }
        });
    }
}
