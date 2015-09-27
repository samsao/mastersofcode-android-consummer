package com.oyeoye.consumer.presentation.payment;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.model.Deal;
import com.oyeoye.consumer.presentation.base.PresentedLinearLayout;
import com.oyeoye.consumer.presentation.payment.stackable.PaymentStackableComponent;
import com.simplify.android.sdk.CardEditor;

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

    @Bind(R.id.screen_payment_checkout)
    protected Button checkoutButton;

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

        cardEditor.addOnStateChangedListener(new CardEditor.OnStateChangedListener() {
            @Override
            public void onStateChange(CardEditor cardEditor) {
                checkoutButton.setEnabled(cardEditor.isValid());
            }
        });
    }

    public void show(Deal deal) {
//        cardEditor.setAmount(deal.getPrice());
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

    @OnClick(R.id.screen_payment_checkout)
    void checkoutClick() {
        presenter.chargeClick(cardEditor.getCard());
    }

    @OnClick(R.id.screen_payment_fill)
    void fillClick() {
        // for debug purposes only
        EditText editText = (EditText) cardEditor.findViewById(com.simplify.android.sdk.R.id.simplify_number);
        editText.setText("5105105105105100");

        editText = (EditText) cardEditor.findViewById(com.simplify.android.sdk.R.id.simplify_cvc);
        editText.setText("123");

        editText = (EditText) cardEditor.findViewById(com.simplify.android.sdk.R.id.simplify_expiry);
        editText.setText("09/18");
    }
}
