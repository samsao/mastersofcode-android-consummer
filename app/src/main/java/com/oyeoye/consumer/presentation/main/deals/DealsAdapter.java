package com.oyeoye.consumer.presentation.main.deals;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oyeoye.consumer.AppConstants;
import com.oyeoye.consumer.R;
import com.oyeoye.consumer.model.Deal;
import com.oyeoye.consumer.presentation.util.AspectRatioImageView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;

        @Bind(R.id.row_deal_image)
        public AspectRatioImageView mImage;
        @Bind(R.id.row_deal_discount_percentage)
        public TextView mDiscountPercentage;
        @Bind(R.id.row_deal_title)
        public TextView mDealTitle;
        @Bind(R.id.row_deal_description)
        public TextView mDealDescription;
        @Bind(R.id.row_deal_price)
        public TextView mDealPrice;
        @Bind(R.id.row_deal_quantity_icon)
        public ImageView mDealQuantityIcon;
        @Bind(R.id.row_deal_quantity)
        public TextView mDealQuantity;
        @Bind(R.id.row_deal_buy)
        public Button buyButton;
        @Bind(R.id.row_deal_masterpass)
        public Button masterpassButton;

        public ViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            ButterKnife.bind(this, view);
        }

        public void setup(final Deal deal) {
            Picasso.with(mContext).load(AppConstants.API_URL + "/" + deal.getImage()).into(mImage);
            mDiscountPercentage.setText(Integer.toString((int) (100.0 - (deal.getPrice() / deal.getOriginalPrice()) * 100.0)) + "% off");
            mDealTitle.setText(deal.getTitle());
            mDealDescription.setText(deal.getDescription());
            mDealPrice.setText("$" + new DecimalFormat("#.00").format(deal.getPrice()));
            mDealQuantityIcon.setImageDrawable(mContext.getResources().getDrawable(getQuantityIconId(deal.getQuantity())));
            mDealQuantity.setText(Integer.toString(deal.getQuantity()) + " left");
        }

        private int getQuantityIconId(int quantity) {
            switch (quantity) {
                case 1:
                    return R.drawable.ic_filter_1_black_24dp;
                case 2:
                    return R.drawable.ic_filter_2_black_24dp;
                case 3:
                    return R.drawable.ic_filter_3_black_24dp;
                case 4:
                    return R.drawable.ic_filter_4_black_24dp;
                case 5:
                    return R.drawable.ic_filter_5_black_24dp;
                case 6:
                    return R.drawable.ic_filter_6_black_24dp;
                case 7:
                    return R.drawable.ic_filter_7_black_24dp;
                case 8:
                    return R.drawable.ic_filter_8_black_24dp;
                case 9:
                    return R.drawable.ic_filter_9_black_24dp;
                default:
                    return R.drawable.ic_filter_9_plus_black_24dp;
            }
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

        holder.setup(deal);

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
