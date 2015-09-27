package com.oyeoye.consumer.presentation.main.pickups;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oyeoye.consumer.AppConstants;
import com.oyeoye.consumer.R;
import com.oyeoye.consumer.model.Deal;
import com.oyeoye.consumer.model.Transaction;
import com.oyeoye.consumer.presentation.util.AspectRatioImageView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class PickupsAdapter extends RecyclerView.Adapter<PickupsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;

        @Bind(R.id.row_transaction_image)
        public AspectRatioImageView mImage;
        @Bind(R.id.row_transaction_name)
        public TextView mDealTitle;
        @Bind(R.id.row_transaction_description)
        public TextView mDealDescription;
        @Bind(R.id.row_transaction_price)
        public TextView mDealPrice;
        @Bind(R.id.row_transaction_validate)
        public Button mValidateButton;
        @Bind(R.id.row_transaction_validate_layout)
        public LinearLayout mValidateLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void setup(final Transaction transaction) {
            final Deal deal = transaction.getDeal();
            Picasso.with(mContext).load(AppConstants.API_URL + "/" + deal.getImage()).into(mImage);
            mDealTitle.setText(deal.getTitle());
            mDealDescription.setText(deal.getDescription());
            mDealPrice.setText("1 bought for $" + new DecimalFormat("#.00").format(deal.getPrice()));
            if (transaction.getStatus() == 0) {
                mValidateLayout.setVisibility(View.VISIBLE);
            } else {
                mValidateLayout.setVisibility(View.GONE);
            }
        }
    }

    public interface Listener {
        void onTransactionValidateClick(Transaction transaction);
    }

    private final List<Transaction> items;
    private final Listener listener;

    public PickupsAdapter(List<Transaction> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public PickupsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Transaction transaction = items.get(position);
        holder.setup(transaction);
        holder.mValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTransactionValidateClick(transaction);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
