package com.oyeoye.consumer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.oyeoye.consumer.manager.SessionManager;
import com.oyeoye.consumer.presentation.RootActivityPresenter;
import com.oyeoye.consumer.presentation.login.stackable.LoginStackable;
import com.oyeoye.consumer.presentation.main.stackable.MainStackable;
import com.oyeoye.consumer.presentation.masterpass.MasterpassView;
import com.oyeoye.consumer.presentation.payment.PaymentView;

import javax.inject.Inject;

import architect.Navigator;
import architect.NavigatorView;
import architect.TransitionsMapping;
import architect.commons.ActivityArchitector;
import architect.commons.Architected;
import architect.commons.transition.Config;
import architect.commons.transition.FadeModalTransition;
import architect.commons.transition.LateralViewTransition;
import architect.robot.DaggerService;
import autodagger.AutoComponent;
import autodagger.AutoInjector;
import butterknife.Bind;
import butterknife.ButterKnife;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import timber.log.Timber;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoComponent(
        dependencies = App.class,
        superinterfaces = AppDependencies.class
)
@AutoInjector
@DaggerScope(RootActivity.class)
public class RootActivity extends AppCompatActivity implements RootActivityPresenter.Activity {

    private MortarScope scope;
    private Navigator navigator;

    @Inject
    protected RootActivityPresenter activityPresenter;

    @Inject
    protected SessionManager sessionManager;

    @Bind(R.id.navigator_container)
    protected NavigatorView navigatorView;

    @Override
    public Object getSystemService(String name) {
        if (scope != null && scope.hasService(name)) {
            return scope.getService(name);
        }
        return super.getSystemService(name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);

        scope = ActivityArchitector.onCreateScope(this, savedInstanceState, new Architected() {
            @Override
            public Navigator createNavigator(MortarScope scope) {
                Navigator navigator = Navigator.create(scope, null, new Navigator.Config().dontRestoreStackAfterKill(true));
                navigator.transitions().register(new TransitionsMapping()
                                .byDefault(new LateralViewTransition(new Config().duration(300)))
                                .show(PaymentView.class).withTransition(new FadeModalTransition())
                                .show(MasterpassView.class).withTransition(new FadeModalTransition())
                );
                return navigator;
            }

            @Override
            public void configureScope(MortarScope.Builder builder, MortarScope parentScope) {
                RootActivityComponent component = DaggerRootActivityComponent.builder()
                        .appComponent(parentScope.<AppComponent>getService(DaggerService.SERVICE_NAME))
                        .build();
                builder.withService(DaggerService.SERVICE_NAME, component);
            }
        });

        DaggerService.<RootActivityComponent>get(this).inject(this);
        activityPresenter.takeView(this);

        Timber.d("Auto-login? %b", sessionManager.isLogged());
        navigator = ActivityArchitector.onCreateNavigator(this, savedInstanceState, navigatorView, sessionManager.isLogged() ? new MainStackable() : new LoginStackable());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        navigator.delegate().onNewIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(scope).onSaveInstanceState(outState);
        navigator.delegate().onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigator.delegate().onStart();
    }

    @Override
    protected void onStop() {
        navigator.delegate().onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        activityPresenter.dropView(this);

        navigator.delegate().onDestroy();
        navigator = null;

        if (isFinishing() && scope != null) {
            scope.destroy();
            scope = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (navigator.delegate().onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return activityPresenter.onCreateOptionsMenu(getMenuInflater(), menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return activityPresenter.onOptionsItemSelected(item);
    }

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    public void setToolbarTitle(@StringRes int resId) {
        setToolbarTitle(getString(resId));
    }

    public void setToolbarTitle(String title) {
        if (title.isEmpty()) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

}
