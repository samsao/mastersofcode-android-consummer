package com.oyeoye.consumer.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.RootActivity;
import com.oyeoye.consumer.manager.SessionManager;
import com.oyeoye.consumer.service.RegistrationIntentService;

import javax.inject.Inject;

import autodagger.AutoExpose;
import mortar.Presenter;
import mortar.bundler.BundleService;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoExpose(RootActivity.class)
@DaggerScope(RootActivity.class)
public class RootActivityPresenter extends Presenter<RootActivityPresenter.Activity> {

    private SessionManager sessionManager;
    private SetupToolbarHandler toolbarHandler;

    @Inject
    public RootActivityPresenter(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    protected BundleService extractBundleService(RootActivityPresenter.Activity activity) {
        return BundleService.getBundleService(activity.getContext());
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        if (TextUtils.isEmpty(sessionManager.getGcmToken())) {
            // Start IntentService to register this application with GCM.
            Intent intentGoogleService = new Intent(getView().getContext(), RegistrationIntentService.class);
            ((RootActivity) getView()).startService(intentGoogleService);
        }
    }

    public android.app.Activity getActivity() {
        return (android.app.Activity) getView();
    }

    public void startActivity(Activity activity) {
//        getView().getContext().start
    }

    public void setupToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            ((RootActivity) getView()).setSupportActionBar(toolbar);
        }
    }

    /**
     * Set the toolbar title
     *
     * @param title
     */
    public void setToolbarTitle(String title) {
        if (title.isEmpty()) {
            ((RootActivity) getView()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        } else {
            ((RootActivity) getView()).getSupportActionBar().setDisplayShowTitleEnabled(true);
            ((RootActivity) getView()).getSupportActionBar().setTitle(title.toUpperCase());
        }
    }

    public void resetMenu(SetupToolbarHandler setupToolbarPresenter) {
        toolbarHandler = setupToolbarPresenter;
        ((RootActivity) getView()).invalidateOptionsMenu();
    }

    public boolean onCreateOptionsMenu(MenuInflater menuInflater, Menu menu) {
        ActionBar actionBar = ((RootActivity) getView()).getSupportActionBar();
        if (actionBar != null && toolbarHandler != null) {
            toolbarHandler.setupToolbarMenu(actionBar, menuInflater, menu);
            return true;
        }
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                if (toolbarHandler != null) {
                    return toolbarHandler.onOptionsItemSelected(item);
                } else {
                    return false;
                }
        }
    }

    public interface Activity {
        Context getContext();
    }
}
