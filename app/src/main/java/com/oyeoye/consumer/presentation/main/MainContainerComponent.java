package com.oyeoye.consumer.presentation.main;

import com.oyeoye.consumer.AppDependencies;
import com.oyeoye.consumer.RootActivity;

import autodagger.AutoComponent;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoComponent(
        dependencies = RootActivity.class,
        superinterfaces = AppDependencies.class
)
public @interface MainContainerComponent {

}
