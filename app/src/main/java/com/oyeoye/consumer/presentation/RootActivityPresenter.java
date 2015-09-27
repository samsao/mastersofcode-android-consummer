package com.oyeoye.consumer.presentation;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.RootActivity;

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

    private SetupToolbarHandler toolbarHandler;

    @Inject
    public RootActivityPresenter() {

    }

    @Override
    protected BundleService extractBundleService(RootActivityPresenter.Activity activity) {
        return BundleService.getBundleService(activity.getContext());
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
