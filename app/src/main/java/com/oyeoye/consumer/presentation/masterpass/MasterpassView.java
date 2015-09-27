package com.oyeoye.consumer.presentation.masterpass;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.presentation.base.PresentedLinearLayout;
import com.oyeoye.consumer.presentation.masterpass.stackable.MasterpassStackableComponent;

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
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // show the webview only when the masterpass is starting to load
                if (url.startsWith("https://www.simplify.com/commerce/masterPass")) {

                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            webView.setVisibility(VISIBLE);
                            loadingView.setVisibility(GONE);
                        }
                    }, 2000);
                }

                Timber.d("START: %s", url);
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("file:///android_asset/masterpass.html");
    }
}
