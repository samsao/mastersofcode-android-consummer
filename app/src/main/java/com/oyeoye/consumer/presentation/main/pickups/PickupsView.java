package com.oyeoye.consumer.presentation.main.pickups;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.model.Transaction;
import com.oyeoye.consumer.presentation.base.PresentedFrameLayout;

import java.util.List;

import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoInjector(PickupsPresenter.class)
public class PickupsView extends PresentedFrameLayout<PickupsPresenter> {

    @Bind(R.id.screen_pickups_recyclerview)
    public RecyclerView recyclerView;

    @Bind(R.id.screen_pickups_progress)
    public ProgressBar progressBar;

    public PickupsView(Context context) {
        super(context);
        DaggerService.<PickupsStackableComponent>get(context).inject(this);

        View view = View.inflate(context, R.layout.screen_pickups, this);
        ButterKnife.bind(view);

        setTag("pickups_tab");
    }

    public void show(List<Transaction> transactions) {
        progressBar.setVisibility(GONE);
        recyclerView.setVisibility(VISIBLE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        PickupsAdapter adapter = new PickupsAdapter(transactions, null);
        recyclerView.setAdapter(adapter);
    }

    public void reload() {
        presenter.load();
    }

    public void showLoading() {
        progressBar.setVisibility(VISIBLE);
        recyclerView.setVisibility(GONE);
    }
}
