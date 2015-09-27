package com.oyeoye.consumer.presentation.masterpass;

import android.content.Context;
import android.view.View;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.presentation.base.PresentedLinearLayout;
import com.oyeoye.consumer.presentation.masterpass.stackable.MasterpassStackableComponent;

import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.ButterKnife;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoInjector(MasterpassPresenter.class)
public class MasterpassView extends PresentedLinearLayout<MasterpassPresenter> {


    public MasterpassView(final Context context) {
        super(context);
        DaggerService.<MasterpassStackableComponent>get(context).inject(this);

        View view = View.inflate(context, R.layout.screen_payment, this);
        ButterKnife.bind(view);
    }
}
