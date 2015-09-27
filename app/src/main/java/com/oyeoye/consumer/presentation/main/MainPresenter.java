package com.oyeoye.consumer.presentation.main;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.presentation.ActivityContainerComponent;
import com.oyeoye.consumer.presentation.RootActivityPresenter;
import com.oyeoye.consumer.presentation.SetupToolbarHandler;
import com.oyeoye.consumer.presentation.main.deals.stackable.DealsStackable;
import com.oyeoye.consumer.presentation.main.pickups.stackable.PickupsStackable;

import architect.ReceivesResult;
import architect.robot.AutoStackable;
import autodagger.AutoComponent;
import autodagger.AutoExpose;
import timber.log.Timber;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoStackable(
        component = @AutoComponent(includes = ActivityContainerComponent.class),
        pathWithView = MainView.class
)
@DaggerScope(MainPresenter.class)
@AutoExpose(MainPresenter.class)
public class MainPresenter extends AbstractPresenter<MainView> implements SetupToolbarHandler, ReceivesResult<Boolean> {

    private final RootActivityPresenter activityPresenter;

    private final DealsStackable dealsStackable = new DealsStackable();
    private final PickupsStackable pickupsStackable = new PickupsStackable();

    public MainPresenter(RootActivityPresenter mainActivityPresenter) {
        activityPresenter = mainActivityPresenter;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        getView().show(dealsStackable, pickupsStackable);
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

    @Override
    public void onReceivedResult(Boolean result) {
        Timber.d("Receives result: %b", result);
        getView().tabLayout.getTabAt(1).select();
    }
}
