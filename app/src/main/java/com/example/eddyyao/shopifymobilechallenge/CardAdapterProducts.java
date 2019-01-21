package com.example.eddyyao.shopifymobilechallenge;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class CardAdapterProducts extends RecyclerView.Adapter<CardAdapterProducts.MyViewHolder> {
    private ArrayList<HashMap<String, String>> mDataset;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView serviceTitle;
        TextView serviceVariations;
        ImageView imageView;
        String description;
        MyViewHolder(View v) {
            super(v);

            mCardView = v.findViewById(R.id.card_view);
            serviceTitle = v.findViewById(R.id.tv_text);
            serviceVariations = v.findViewById(R.id.tv_variations);
            imageView = v.findViewById(R.id.iv_image);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardAdapterProducts(ArrayList<HashMap<String, String>> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardAdapterProducts.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.serviceTitle.setText(mDataset.get(position).get("name") + "\n" + mDataset.get(position).get("vendor"));
        holder.serviceVariations.setText(mDataset.get(position).get("variants") + "   |   " + mDataset.get(position).get("inventory") + " left in stock");
        holder.description = mDataset.get(position).get("description");
        Picasso.get().load(DetailActivity.img).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}