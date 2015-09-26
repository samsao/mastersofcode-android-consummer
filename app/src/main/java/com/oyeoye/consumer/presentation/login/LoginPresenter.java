package com.oyeoye.consumer.presentation.login;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.manager.SessionManager;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.presentation.ActivityContainerComponent;
import com.oyeoye.consumer.presentation.RootActivityPresenter;
import com.oyeoye.consumer.presentation.SetupToolbarHandler;

import architect.robot.AutoStackable;
import autodagger.AutoComponent;
import autodagger.AutoExpose;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoStackable(
        component = @AutoComponent(includes = ActivityContainerComponent.class),
        pathWithView = LoginView.class
)
@DaggerScope(LoginPresenter.class)
@AutoExpose(LoginPresenter.class)
public class LoginPresenter extends AbstractPresenter<LoginView> implements SetupToolbarHandler {

    private final RootActivityPresenter activityPresenter;
    private final SessionManager sessionManager;

    public LoginPresenter(RootActivityPresenter activityPresenter, SessionManager sessionManager) {
        this.activityPresenter = activityPresenter;
        this.sessionManager = sessionManager;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {

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
