package com.oyeoye.consumer.presentation.main.pickups;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oyeoye.consumer.R;
import com.oyeoye.consumer.model.Transaction;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class PickupsAdapter extends RecyclerView.Adapter<PickupsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.row_deal_name)
        public TextView nameTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Listener {

    }

    private final List<Transaction> items;
    private final Listener listener;

    public PickupsAdapter(List<Transaction> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public PickupsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_deal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Transaction transaction = items.get(position);
        holder.nameTextView.setText("BLAH");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}