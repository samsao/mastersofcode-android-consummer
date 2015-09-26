package com.oyeoye.consumer.presentation.main;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.presentation.base.PresentedFrameLayout;
import com.oyeoye.consumer.presentation.main.deals.stackable.DealsStackable;
import com.oyeoye.consumer.presentation.main.pickups.stackable.PickupsStackable;
import com.oyeoye.consumer.presentation.main.stackable.MainStackableComponent;

import architect.commons.adapter.StackablePagerAdapter;
import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoInjector(MainPresenter.class)
public class MainView extends PresentedFrameLayout<MainPresenter> {

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.screen_main_tablayout)
    protected TabLayout tabLayout;

    @Bind(R.id.screen_main_pager)
    protected ViewPager pager;

    public MainView(Context context) {
        super(context);
        DaggerService.<MainStackableComponent>get(context).inject(this);

        View view = View.inflate(context, R.layout.screen_main, this);
        ButterKnife.bind(view);
        setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    public void show(DealsStackable dealsStackable, PickupsStackable pickupsStackable) {
        tabLayout.addTab(tabLayout.newTab().setText("Deals"));
        tabLayout.addTab(tabLayout.newTab().setText("Pickups"));

        StackablePagerAdapter adapter = new StackablePagerAdapter(getContext(), dealsStackable, pickupsStackable);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.onViewAttachedToWindow();
    }
}
