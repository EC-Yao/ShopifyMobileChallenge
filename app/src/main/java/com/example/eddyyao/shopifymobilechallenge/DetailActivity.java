package com.example.eddyyao.shopifymobilechallenge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class DetailActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static String id;
    public static String title;
    public static String col_description;
    public static String img;
    private TextView description;

    private final ArrayList<ArrayList<String>> test = new ArrayList<ArrayList<String>>(Arrays.asList(new ArrayList<String>(
            Arrays.asList("Buenos Aires", "Córdoba", "La Plata")), new ArrayList<String>(
            Arrays.asList("Buenos Aires", "Córdoba", "La Plata")), new ArrayList<String>(
            Arrays.asList("Buenos Aires", "Córdoba", "La Plata"))));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        description = findViewById(R.id.collection_description);

        mRecyclerView = (RecyclerView) findViewById(R.id.collections_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CardAdapterProducts(ApiManager.productList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setTitle(DetailActivity.title);
        description.setText(col_description);
        Log.wtf("Image", img);
    }

    public static String makeUrl(ArrayList<String> ids){
        String output = "https://shopicruit.myshopify.com/admin/products.json?ids=";

        for (int i = 0; i < ids.size(); i++){
            output = output + ids.get(i) + ",";
        }


        return output.substring(0, output.length() - 1) + "&page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";
    }
}
