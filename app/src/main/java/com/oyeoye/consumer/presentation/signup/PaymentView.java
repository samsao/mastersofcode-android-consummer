package com.oyeoye.consumer.presentation.signup;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.model.Deal;
import com.oyeoye.consumer.presentation.base.PresentedLinearLayout;
import com.oyeoye.consumer.presentation.signup.stackable.PaymentStackableComponent;
import com.simplify.android.sdk.view.CardEditor;

import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoInjector(PaymentPresenter.class)
public class PaymentView extends PresentedLinearLayout<PaymentPresenter> {

//    @Bind(R.id.toolbar)
//    protected Toolbar toolbar;

//    @Bind(R.id.screen_payment_overlay)
//    protected View overlay;

    @Bind(R.id.screen_payment_cardeditor)
    protected CardEditor cardEditor;

    @Bind(R.id.screen_payment_message)
    protected TextView messageTextView;

    @Bind(R.id.screen_payment_loading)
    protected View loadingView;

    @Bind(R.id.screen_payment_cardeditor_overlay)
    protected View cardEditorOverlay;

    public PaymentView(final Context context) {
        super(context);
        DaggerService.<PaymentStackableComponent>get(context).inject(this);

        View view = View.inflate(context, R.layout.screen_payment, this);
        ButterKnife.bind(view);

        cardEditor.setOnCloseClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
                presenter.closeClick();
            }
        });

        cardEditor.setOnChargeClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.chargeClick(cardEditor.getCard());
            }
        });
    }

    public void show(Deal deal) {
        cardEditor.setAmount(deal.getPrice());
    }

    public void showFailure() {
        cardEditor.setEnabled(true);
        messageTextView.setVisibility(VISIBLE);
        loadingView.setVisibility(GONE);
        cardEditorOverlay.setVisibility(GONE);
    }

    public void showLoading() {
        messageTextView.setVisibility(GONE);
        loadingView.setVisibility(VISIBLE);
        cardEditorOverlay.setVisibility(VISIBLE);
    }

    public void close() {
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick(R.id.screen_payment_cardeditor_overlay)
    void cardEditorOverlayClick() {
        // block the click
    }
}
