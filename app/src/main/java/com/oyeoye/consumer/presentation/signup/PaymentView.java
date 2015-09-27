package com.oyeoye.consumer.presentation.signup;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.model.Deal;
import com.oyeoye.consumer.presentation.base.PresentedLinearLayout;
import com.oyeoye.consumer.presentation.signup.stackable.PaymentStackableComponent;
import com.simplify.android.sdk.view.CardEditor;

import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoInjector(PaymentPresenter.class)
public class PaymentView extends PresentedLinearLayout<PaymentPresenter> {

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.screen_payment_overlay)
    protected View overlay;

    @Bind(R.id.screen_payment_cardeditor)
    protected CardEditor cardEditor;

    public PaymentView(Context context) {
        super(context);
        DaggerService.<PaymentStackableComponent>get(context).inject(this);

        View view = View.inflate(context, R.layout.screen_payment, this);
        ButterKnife.bind(view);
    }

    public void show(Deal deal) {
        cardEditor.setAmount(deal.getPrice());
    }

    public void showLoading() {
        overlay.setVisibility(VISIBLE);
    }

    public void hideLoading() {
        overlay.setVisibility(GONE);
    }
}
