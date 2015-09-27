package com.oyeoye.consumer.presentation.main;

import android.content.Context;

import architect.StackablePath;
import architect.commons.adapter.StackablePagerAdapter;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class MainPagerAdapter extends StackablePagerAdapter {

    public MainPagerAdapter(Context context, StackablePath... paths) {
        super(context, paths);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "Deals" : "Pickups";
    }
}
