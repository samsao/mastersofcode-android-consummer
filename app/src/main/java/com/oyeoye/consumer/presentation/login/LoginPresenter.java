package com.oyeoye.consumer.presentation.login;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.manager.SessionManager;
import com.oyeoye.consumer.model.User;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.presentation.ActivityContainerComponent;
import com.oyeoye.consumer.presentation.RootActivityPresenter;
import com.oyeoye.consumer.presentation.SetupToolbarHandler;
import com.oyeoye.consumer.presentation.main.stackable.MainStackable;
import com.oyeoye.consumer.rest.RestClient;

import architect.Navigator;
import architect.robot.AutoStackable;
import autodagger.AutoComponent;
import autodagger.AutoExpose;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    private final RestClient restClient;

    private AuthCallback authCallback = new AuthCallback() {
        @Override
        public void success(DigitsSession digitsSession, String res) {
            if (!hasView()) return;
            processPhone(digitsSession.getPhoneNumber());
        }

        @Override
        public void failure(DigitsException e) {
            if (!hasView()) return;

        }
    };

    public LoginPresenter(RootActivityPresenter activityPresenter, SessionManager sessionManager, RestClient restClient) {
        this.activityPresenter = activityPresenter;
        this.sessionManager = sessionManager;
        this.restClient = restClient;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        getView().configure(authCallback);
    }

    private void processPhone(final String phone) {
        getView().showLoading();

        String gcm = sessionManager.getGcmToken();
        if (gcm == null) {
            gcm = "invalid";
        }

        restClient.getUserService().connect(phone, gcm, new Callback<User>() {

            @Override
            public void success(User user, Response response) {
                if (!hasView() || user == null) return;
                getView().hideLoading();

                sessionManager.authenticate(user);
                Navigator.get(getView()).push(new MainStackable());
            }

            @Override
            public void failure(RetrofitError error) {
                if (!hasView()) return;
                getView().hideLoading();
            }
        });
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
