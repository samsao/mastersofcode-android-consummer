package com.oyeoye.consumer.presentation.main.deals;

import android.os.Bundle;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.model.Deal;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.presentation.main.MainContainerComponent;
import com.oyeoye.consumer.presentation.signup.stackable.PaymentStackable;

import java.util.ArrayList;
import java.util.List;

import architect.Navigator;
import architect.StackablePath;
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

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        List<Deal> deals = new ArrayList<>();
        deals.add(new Deal("Deal 1", 50.));
        deals.add(new Deal("Deal 2", 20.));
        getView().show(deals);
    }

    public void onDealClick(Deal deal, int buyMode) {
        StackablePath path;
        if (buyMode == DealsView.BUY_NORMAL) {
            path = new PaymentStackable(deal);
        } else {
            path = new PaymentStackable(deal);
        }

        Navigator.get(getView()).push(path);
    }
}
