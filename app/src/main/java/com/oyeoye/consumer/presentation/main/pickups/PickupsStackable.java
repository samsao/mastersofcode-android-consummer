package com.oyeoye.consumer.presentation.main.pickups;

import android.content.Context;
import android.view.ViewGroup;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.RootActivityComponent;
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
        target = PickupsPresenter.class,
        modules = PickupsStackable.Module.class
)
@Parcel(
        parcelsIndex = false
)
@DaggerScope(PickupsPresenter.class)
public class PickupsStackable implements StackablePath {
    @ParcelConstructor
    public PickupsStackable() {
    }

    @Override
    public void configureScope(MortarScope.Builder builder, MortarScope parentScope) {
        RootActivityComponent component = NavigatorServices.getService(parentScope, DaggerService.SERVICE_NAME);

        builder.withService(DaggerService.SERVICE_NAME, DaggerPickupsStackableComponent.builder()
                .rootActivityComponent(component)
                .module(new Module())
                .build());
    }

    @Override
    public PickupsView createView(Context context, ViewGroup parent) {
        return new PickupsView(context);
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(PickupsPresenter.class)
        public PickupsPresenter providesPresenter(RestClient restClient) {
            return new PickupsPresenter(restClient);
        }
    }
}
