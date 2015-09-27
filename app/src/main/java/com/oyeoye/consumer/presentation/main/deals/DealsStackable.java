package com.oyeoye.consumer.presentation.main.deals;

import android.content.Context;
import android.view.ViewGroup;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.RootActivityComponent;
import com.oyeoye.consumer.presentation.RootActivityPresenter;
import com.oyeoye.consumer.presentation.main.MainContainerComponent;
import com.oyeoye.consumer.rest.RestClient;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import architect.NavigatorServices;
import architect.StackablePath;
import architect.robot.DaggerService;
import autodagger.AutoComponent;
import dagger.Provides;
import mortar.MortarScope;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoComponent(
        includes = MainContainerComponent.class,
        target = DealsPresenter.class,
        modules = DealsStackable.Module.class
)
@Parcel(
        parcelsIndex = false
)
@DaggerScope(DealsPresenter.class)
public class DealsStackable implements StackablePath {
    @ParcelConstructor
    public DealsStackable() {
    }

    @Override
    public void configureScope(MortarScope.Builder builder, MortarScope parentScope) {
        RootActivityComponent component = NavigatorServices.getService(parentScope, DaggerService.SERVICE_NAME);

        builder.withService(DaggerService.SERVICE_NAME, DaggerDealsStackableComponent.builder()
                .rootActivityComponent(component)
                .module(new Module())
                .build());
    }

    @Override
    public DealsView createView(Context context, ViewGroup parent) {
        return new DealsView(context);
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(DealsPresenter.class)
        public DealsPresenter providesPresenter(RootActivityPresenter activityPresenter, RestClient restClient) {
            return new DealsPresenter(activityPresenter, restClient);
        }
    }
}
