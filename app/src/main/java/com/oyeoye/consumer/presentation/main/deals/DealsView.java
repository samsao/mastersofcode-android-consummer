package com.oyeoye.consumer.presentation.main.deals;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.model.Deal;
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
@AutoInjector(DealsPresenter.class)
public class DealsView extends PresentedFrameLayout<DealsPresenter> {

    public static final int BUY_NORMAL = 1;
    public static final int BUY_MASTER = 2;

    @Bind(R.id.screen_deals_recyclerview)
    public RecyclerView recyclerView;

    @Bind(R.id.screen_deals_refreshlayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.screen_deals_progress)
    public ProgressBar progressBar;

    @Bind(R.id.screen_deals_retry)
    public Button retryButton;

    public DealsView(Context context) {
        super(context);
        DaggerService.<DealsStackableComponent>get(context).inject(this);

        View view = View.inflate(context, R.layout.screen_deals, this);
        ButterKnife.bind(view);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.load();
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    public void show(List<Deal> deals) {
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

        DealsAdapter adapter = new DealsAdapter(deals, new DealsAdapter.Listener() {
            @Override
            public void onDealBuyClick(Deal deal) {
                presenter.dealClick(deal, BUY_NORMAL);
            }

            @Override
            public void onDealMasterpassClick(Deal deal) {
                presenter.dealClick(deal, BUY_MASTER);
            }
        });
        recyclerView.setAdapter(adapter);
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

    @OnClick(R.id.screen_deals_retry)
    void retryClick() {
        presenter.load();
    }
}
