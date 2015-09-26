package com.oyeoye.consumer.presentation.main.deals;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.presentation.base.PresentedFrameLayout;
import com.oyeoye.consumer.presentation.main.MainPresenter;
import com.oyeoye.consumer.presentation.main.deals.stackable.DealsStackableComponent;

import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoInjector(DealsPresenter.class)
public class DealsView extends PresentedFrameLayout<DealsPresenter> {



    public DealsView(Context context) {
        super(context);
        DaggerService.<DealsStackableComponent>get(context).inject(this);

        View view = View.inflate(context, R.layout.screen_deals, this);
        ButterKnife.bind(view);
        setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setup();
    }

    private void setup() {

    }
}
