package com.oyeoye.consumer.presentation.main.deals;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.presentation.main.MainContainerComponent;

import architect.robot.AutoStackable;
import autodagger.AutoComponent;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoStackable(
        component = @AutoComponent(includes = MainContainerComponent.class),
        pathWithView = DealsView.class
)
@DaggerScope(DealsPresenter.class)
public class DealsPresenter extends AbstractPresenter<DealsView> {
}
