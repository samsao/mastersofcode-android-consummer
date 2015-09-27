package com.oyeoye.consumer.presentation.signup;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.manager.SessionManager;
import com.oyeoye.consumer.model.Deal;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.presentation.ActivityContainerComponent;
import com.oyeoye.consumer.presentation.RootActivityPresenter;
import com.oyeoye.consumer.presentation.SetupToolbarHandler;
import com.oyeoye.consumer.rest.RestClient;

import architect.robot.AutoStackable;
import architect.robot.FromPath;
import autodagger.AutoComponent;
import autodagger.AutoExpose;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoStackable(
        component = @AutoComponent(includes = ActivityContainerComponent.class),
        pathWithView = PaymentView.class
)
@DaggerScope(PaymentPresenter.class)
@AutoExpose(PaymentPresenter.class)
public class PaymentPresenter extends AbstractPresenter<PaymentView> implements SetupToolbarHandler {

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

    @Override
    public void setupToolbarMenu(ActionBar actionBar, MenuInflater menuInflater, Menu menu) {
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Oye Oye!");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onViewAttachedToWindow() {
        activityPresenter.setupToolbar(getView().toolbar);
        activityPresenter.resetMenu(this);
    }
}
