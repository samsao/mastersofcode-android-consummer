package com.oyeoye.consumer.presentation;

import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public interface SetupToolbarHandler {
    void setupToolbarMenu(ActionBar actionBar, MenuInflater menuInflater, Menu menu);
    boolean onOptionsItemSelected(MenuItem item);
}
