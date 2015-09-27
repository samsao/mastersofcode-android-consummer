package com.oyeoye.consumer.presentation.main.deals;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.model.Deal;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.row_deal_name)
        public TextView nameTextView;

        @Bind(R.id.row_deal_buy)
        public Button buyButton;

        @Bind(R.id.row_deal_masterpass)
        public Button masterpassButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Listener {
        void onDealBuyClick(Deal deal);

        void onDealMasterpassClick(Deal deal);
    }

    private final List<Deal> items;
    private final Listener listener;

    public DealsAdapter(List<Deal> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public DealsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_deal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Deal deal = items.get(position);
        holder.nameTextView.setText(deal.getTitle());

        holder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDealBuyClick(deal);
            }
        });

        holder.masterpassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDealMasterpassClick(deal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
