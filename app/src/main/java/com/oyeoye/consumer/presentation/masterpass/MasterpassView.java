package com.oyeoye.consumer.presentation.masterpass;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.model.Deal;
import com.oyeoye.consumer.presentation.base.PresentedLinearLayout;
import com.oyeoye.consumer.presentation.masterpass.stackable.MasterpassStackableComponent;
import com.simplify.android.sdk.CardToken;

import org.apache.commons.lang3.StringUtils;

import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoInjector(MasterpassPresenter.class)
public class MasterpassView extends PresentedLinearLayout<MasterpassPresenter> {

    @Bind(R.id.screen_masterpass_webview)
    public WebView webView;

    @Bind(R.id.screen_masterpass_loading)
    public View loadingView;

    public MasterpassView(final Context context) {
        super(context);
        DaggerService.<MasterpassStackableComponent>get(context).inject(this);

        View view = View.inflate(context, R.layout.screen_masterpass, this);
        ButterKnife.bind(view);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Temp bug/faking because Masterpass is not working :(
                if (StringUtils.startsWithIgnoreCase(url, "https://www.simplify.com/commerce/masterPass/callback")) {
//                    Card card = new Card();
//                    card.setNumber("5204740009900014");
//                    card.setCvc("123");
//                    card.setExpYear("18");
//                    card.setExpMonth("9");
                    presenter.paymentSuccess(new CardToken());
                }
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // show the webview only when the masterpass is starting to load
                if (StringUtils.startsWithIgnoreCase(url, "https://www.simplify.com/commerce/masterPass")) {
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            webView.setVisibility(VISIBLE);
                            loadingView.setVisibility(GONE);
                        }
                    }, 2000);
                }
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.loadUrl("file:///android_asset/masterpass.html");
    }

    public void show(Deal deal) {

    }
}
