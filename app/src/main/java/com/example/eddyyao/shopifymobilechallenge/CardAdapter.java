package com.example.eddyyao.shopifymobilechallenge;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.Loader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private ArrayList<HashMap<String, String>> mDataset;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView serviceTitle;
        TextView serviceDescription;
        String id;
        String imgsrc;
        MyViewHolder(View v) {
            super(v);

            mCardView = v.findViewById(R.id.card_view);
            serviceTitle = v.findViewById(R.id.tv_text);
            serviceDescription = v.findViewById(R.id.tv_description);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardAdapter(ArrayList<HashMap<String, String>> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.serviceTitle.setText(mDataset.get(position).get("title"));
        holder.serviceDescription.setText(mDataset.get(position).get("body"));
        holder.id = mDataset.get(position).get("id");
        holder.imgsrc = mDataset.get(position).get("imgsrc");

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread thread = new Thread(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            LoaderActivity.api.getDetailJSON(mDataset.get(position).get("id"));
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                thread = new Thread(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            LoaderActivity.api.getProductJSON(DetailActivity.makeUrl(LoaderActivity.api.idList));
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
                try{
                    thread.join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

                DetailActivity.title = mDataset.get(position).get("title");
                DetailActivity.img = mDataset.get(position).get("img");
                DetailActivity.col_description = mDataset.get(position).get("body");

                Intent i = new Intent(view.getContext(), DetailActivity.class);
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}