package com.oyeoye.consumer;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.digits.sdk.android.Digits;
import com.oyeoye.consumer.manager.SessionManager;
import com.simplify.android.sdk.Simplify;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import architect.robot.DaggerService;
import autodagger.AutoComponent;
import dagger.Provides;
import io.fabric.sdk.android.Fabric;
import mortar.MortarScope;
import timber.log.Timber;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoComponent(
        modules = App.Module.class,
        superinterfaces = AppDependencies.class
)
@DaggerScope(App.class)
public class App extends Application {

    private MortarScope scope;

    @Override
    public Object getSystemService(String name) {
        return (scope != null && scope.hasService(name)) ? scope.getService(name) : super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent component = DaggerAppComponent.builder().module(new Module()).build();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(AppConstants.TWITTER_KEY, AppConstants.TWITTER_SECRET);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Fabric.with(this, new TwitterCore(authConfig), new Digits());
        } else {
            Fabric.with(this, new Crashlytics(), new TwitterCore(authConfig), new Digits());
        }

        scope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, component)
                .build("root");
    }

    @dagger.Module
    public class Module {

        @Provides
        @DaggerScope(App.class)
        public SharedPreferences provideSharedPreferences() {
            return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        }

        @Provides
        @DaggerScope(App.class)
        public SessionManager provideSessionManager(SharedPreferences sharedPreferences) {
            return new SessionManager(sharedPreferences);
        }

        @Provides
        @DaggerScope(App.class)
        public Simplify provideSimplify() {
            return new Simplify(AppConstants.SIMPLIFY_PUBLIC);
        }


    }
}
