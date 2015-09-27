package com.oyeoye.consumer.presentation.main.pickups;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.model.Transaction;
import com.oyeoye.consumer.presentation.base.PresentedFrameLayout;

import java.util.List;

import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoInjector(PickupsPresenter.class)
public class PickupsView extends PresentedFrameLayout<PickupsPresenter> {

    @Bind(R.id.screen_pickups_recyclerview)
    public RecyclerView recyclerView;

    @Bind(R.id.screen_pickups_refreshlayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.screen_pickups_progress)
    public ProgressBar progressBar;

    @Bind(R.id.screen_pickups_retry)
    public Button retryButton;

    public PickupsView(Context context) {
        super(context);
        DaggerService.<PickupsStackableComponent>get(context).inject(this);

        View view = View.inflate(context, R.layout.screen_pickups, this);
        ButterKnife.bind(view);

        setTag("pickups_tag");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.load();
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    public void show(List<Transaction> transactions) {
        progressBar.setVisibility(GONE);
        retryButton.setVisibility(GONE);
        swipeRefreshLayout.setVisibility(VISIBLE);
        swipeRefreshLayout.setRefreshing(false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) != 0) {
                    outRect.top = (int) getResources().getDimension(R.dimen.spacing);
                } else {
                    super.getItemOffsets(outRect, view, parent, state);
                }
            }
        });

        PickupsAdapter adapter = new PickupsAdapter(transactions, new PickupsAdapter.Listener() {
            @Override
            public void onTransactionValidateClick(Transaction transaction) {
                presenter.validateClick(transaction);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void reload() {
        presenter.load();
    }

    public void showLoading() {
        progressBar.setVisibility(VISIBLE);
        swipeRefreshLayout.setVisibility(GONE);
        retryButton.setVisibility(GONE);
        swipeRefreshLayout.setRefreshing(true);
    }

    public void showError() {
        progressBar.setVisibility(GONE);
        swipeRefreshLayout.setVisibility(GONE);
        retryButton.setVisibility(VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @OnClick(R.id.screen_pickups_retry)
    void retryClick() {
        presenter.load();
    }
}
