package com.oyeoye.consumer.presentation;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */

import com.oyeoye.consumer.AppDependencies;
import com.oyeoye.consumer.RootActivity;

import autodagger.AutoComponent;

@AutoComponent(dependencies = RootActivity.class, superinterfaces = AppDependencies.class)
public @interface ActivityContainerComponent {
}
