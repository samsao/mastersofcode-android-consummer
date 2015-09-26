package com.oyeoye.consumer.presentation.main.pickups;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.presentation.base.PresentedFrameLayout;
import com.oyeoye.consumer.presentation.main.pickups.stackable.PickupsStackableComponent;

import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.ButterKnife;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoInjector(PickupsPresenter.class)
public class PickupsView extends PresentedFrameLayout<PickupsPresenter> {


    public PickupsView(Context context) {
        super(context);
        DaggerService.<PickupsStackableComponent>get(context).inject(this);

        View view = View.inflate(context, R.layout.screen_pickups, this);
        ButterKnife.bind(view);
        setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setup();
    }

    private void setup() {

    }
}
