package com.oyeoye.consumer.presentation.main;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.oyeoye.consumer.AppDependencies;
import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.RootActivity;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.presentation.RootActivityPresenter;
import com.oyeoye.consumer.presentation.SetupToolbarHandler;

import architect.robot.AutoStackable;
import autodagger.AutoComponent;
import autodagger.AutoExpose;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoStackable(
        component = @AutoComponent(dependencies = RootActivity.class, superinterfaces = AppDependencies.class),
        pathWithView = MainView.class
)
@DaggerScope(MainPresenter.class)
@AutoExpose(MainPresenter.class)
public class MainPresenter extends AbstractPresenter<MainView> implements SetupToolbarHandler {

    private final RootActivityPresenter mRootActivityPresenter;

    public MainPresenter(RootActivityPresenter mainActivityPresenter) {
        mRootActivityPresenter = mainActivityPresenter;
    }

    public void resetMenu(Toolbar toolbar) {
        mRootActivityPresenter.setupToolbar(getView().mToolbar);
        mRootActivityPresenter.resetMenu(this);
    }

    @Override
    public void setupToolbarMenu(ActionBar actionBar, MenuInflater menuInflater, Menu menu) {
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("TEMPLATE PROJECT");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

}
