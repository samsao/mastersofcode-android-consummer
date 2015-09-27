package com.oyeoye.consumer.presentation.masterpass;

import android.os.Bundle;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.manager.SessionManager;
import com.oyeoye.consumer.model.Deal;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.presentation.ActivityContainerComponent;
import com.oyeoye.consumer.presentation.RootActivityPresenter;
import com.oyeoye.consumer.rest.RestClient;
import com.simplify.android.sdk.CardToken;

import architect.Navigator;
import architect.robot.AutoStackable;
import architect.robot.FromPath;
import autodagger.AutoComponent;
import autodagger.AutoExpose;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoStackable(
        component = @AutoComponent(includes = ActivityContainerComponent.class),
        pathWithView = MasterpassView.class
)
@DaggerScope(MasterpassPresenter.class)
@AutoExpose(MasterpassPresenter.class)
public class MasterpassPresenter extends AbstractPresenter<MasterpassView> {

    private final Deal deal;

    private final RootActivityPresenter activityPresenter;
    private final SessionManager sessionManager;
    private final RestClient restClient;

    public MasterpassPresenter(@FromPath Deal deal, RootActivityPresenter activityPresenter, SessionManager sessionManager, RestClient restClient) {
        this.deal = deal;
        this.activityPresenter = activityPresenter;
        this.sessionManager = sessionManager;
        this.restClient = restClient;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        getView().show(deal);
    }

    public void paymentSuccess(CardToken token) {
        Navigator.get(getView()).back(true);
    }
}
