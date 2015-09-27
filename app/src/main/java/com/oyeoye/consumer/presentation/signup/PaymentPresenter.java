package com.oyeoye.consumer.presentation.signup;

import android.os.Bundle;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.manager.SessionManager;
import com.oyeoye.consumer.model.Deal;
import com.oyeoye.consumer.model.Transaction;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.presentation.ActivityContainerComponent;
import com.oyeoye.consumer.presentation.RootActivityPresenter;
import com.oyeoye.consumer.rest.RestClient;
import com.simplify.android.sdk.Simplify;
import com.simplify.android.sdk.model.Card;
import com.simplify.android.sdk.model.SimplifyError;
import com.simplify.android.sdk.model.Token;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import architect.Navigator;
import architect.robot.AutoStackable;
import architect.robot.FromPath;
import autodagger.AutoComponent;
import autodagger.AutoExpose;
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
    private final Simplify simplify;

    public PaymentPresenter(@FromPath Deal deal, RootActivityPresenter activityPresenter, SessionManager sessionManager, RestClient restClient, Simplify simplify) {
        this.deal = deal;
        this.activityPresenter = activityPresenter;
        this.sessionManager = sessionManager;
        this.restClient = restClient;
        this.simplify = simplify;
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
        simplify.createCardToken(card, new Simplify.CreateTokenListener() {
            @Override
            public void onSuccess(Token token) {
                if (!hasView()) return;
                Timber.d("Success card token: %s", token.getId());
                processToken(token);
            }

            @Override
            public void onError(SimplifyError error) {
                if (!hasView()) return;
                getView().showFailure();
            }
        });
    }

    private void processToken(Token token) {
        restClient.getTransactionService().add(deal.getId(), token.getId(), 1, new Callback<Transaction>() {
            @Override
            public void success(Result<Transaction> result) {
                if (!hasView()) return;
                getView().close();
                Navigator.get(getView()).back(true);
            }

            @Override
            public void failure(TwitterException e) {
                if (!hasView()) return;
                getView().showFailure();
            }
        });
    }
}
