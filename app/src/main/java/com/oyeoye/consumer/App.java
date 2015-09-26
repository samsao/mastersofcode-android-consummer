package com.oyeoye.consumer;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.samsao.projecttemplate.BuildConfig;
import com.samsao.projecttemplate.DaggerMainApplicationComponent;
import com.samsao.projecttemplate.MainApplicationComponent;

import architect.robot.DaggerService;
import autodagger.AutoComponent;
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
    private MortarScope mScope;
    public static String SCOPE_NAME = "root";

    @Override
    public Object getSystemService(String name) {
        return (mScope != null && mScope.hasService(name)) ? mScope.getService(name) : super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Fabric.with(this, new Crashlytics());
        }

        MainApplicationComponent component = DaggerMainApplicationComponent.builder().module(new Module()).build();

        mScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, component)
                .build(SCOPE_NAME);
    }

    @dagger.Module
    public class Module {


    }
}
