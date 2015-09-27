package com.oyeoye.consumer.presentation.validation;

import android.content.Context;
import android.view.View;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.presentation.base.PresentedLinearLayout;
import com.oyeoye.consumer.presentation.validation.stackable.ValidationStackableComponent;

import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.ButterKnife;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoInjector(ValidationPresenter.class)
public class ValidationView extends PresentedLinearLayout<ValidationPresenter> {

    public ValidationView(final Context context) {
        super(context);
        DaggerService.<ValidationStackableComponent>get(context).inject(this);

        View view = View.inflate(context, R.layout.screen_validation, this);
        ButterKnife.bind(view);
    }
}
