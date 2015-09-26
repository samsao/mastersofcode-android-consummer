package com.oyeoye.consumer.presentation.main.pickups;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.presentation.main.MainContainerComponent;

import architect.robot.AutoStackable;
import autodagger.AutoComponent;
import autodagger.AutoExpose;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoStackable(
        component = @AutoComponent(includes = MainContainerComponent.class),
        pathWithView = PickupsView.class
)
@DaggerScope(PickupsPresenter.class)
public class PickupsPresenter extends AbstractPresenter<PickupsView> {
}
