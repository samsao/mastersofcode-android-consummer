package com.oyeoye.consumer.presentation.login;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.oyeoye.consumer.R;
import com.oyeoye.consumer.presentation.base.PresentedLinearLayout;
import com.oyeoye.consumer.presentation.login.stackable.LoginStackableComponent;

import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoInjector(LoginPresenter.class)
public class LoginView extends PresentedLinearLayout<LoginPresenter> {

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.screen_login_auth)
    protected DigitsAuthButton authButton;

    @Bind(R.id.screen_login_overlay)
    protected View overlay;

    public LoginView(Context context) {
        super(context);
        DaggerService.<LoginStackableComponent>get(context).inject(this);

        View view = View.inflate(context, R.layout.screen_login, this);
        ButterKnife.bind(view);
    }

    public void configure(AuthCallback callback) {
        authButton.setCallback(callback);
    }

    public void showLoading() {
        overlay.setVisibility(VISIBLE);
    }

    public void hideLoading() {
        overlay.setVisibility(GONE);
    }
}
