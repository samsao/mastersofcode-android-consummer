package com.oyeoye.consumer;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.oyeoye.consumer.manager.SessionManager;

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

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Fabric.with(this, new Crashlytics());
        }

        AppComponent component = DaggerAppComponent.builder().module(new Module()).build();

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
    }
}
