package com.example.eddyyao.shopifymobilechallenge;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ApiManager {

    private Context context;

    public static String collection;
    public static String collectionDetail;
    public static ArrayList<HashMap<String, String>> collectionList;
    public static ArrayList<String> idList;
    public static ArrayList<HashMap<String, String>> productList;

    public ApiManager(Context c) {
        context = c;
        initialize();
    }

    public void initialize() {
        new CollectionJsonTask().execute("https://shopicruit.myshopify.com/admin/custom_collections.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6");
    }

    public void getDetailJSON(String id) throws ExecutionException, InterruptedException {
        detailJsonArray(new DetailJsonTask().execute("https://shopicruit.myshopify.com/admin/collects.json?collection_id=" + id + "&page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6").get());
    }

    public void getProductJSON(String url) throws ExecutionException, InterruptedException {
        productJsonArray(new ProductJsonTask().execute(url).get());
    }

    private void collectionJsonArray(String json){
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        collectionList = new ArrayList<>();

        try {
            jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray("custom_collections");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                String id = c.getString("id");
                String title = c.getString("title");
                String body = c.getString("body_html");
                String img = c.getJSONObject("image").getString("src");

                // tmp hash map for single contact
                HashMap<String, String> coll = new HashMap<>();

                // adding each child node to HashMap key => value
                coll.put("id", id);
                coll.put("title", title);
                coll.put("body", body);
                coll.put("img", img);

                collectionList.add(coll);
            }
            Log.wtf("List", collectionList.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void detailJsonArray(String json){

        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        idList = new ArrayList<>();

        try {
            jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray("collects");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                idList.add(c.getString("product_id"));
            }
            Log.wtf("List", idList.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void productJsonArray(String json){

        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        productList = new ArrayList<>();

        try {
            jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray("products");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                String name = (c.getString("title"));
                String description = (c.getString("body_html"));
                String vendor = (c.getString("vendor"));
                JSONArray variantList = c.getJSONArray("variants");
                int inventory = 0;
                String variants = "";

                for (int j = 0; j < variantList.length(); j++){
                    JSONObject d = variantList.getJSONObject(j);
                    inventory += d.getInt("inventory_quantity");
                    variants = variants + d.getString("title") + ", ";
                }

                HashMap<String, String> products = new HashMap<>();

                products.put("name", name);
                products.put("description", description);
                products.put("vendor", vendor);
                products.put("inventory", String.valueOf(inventory));
                products.put("variants", variants.substring(0, variants.length() - 2));

                productList.add(products);
            }

            Log.wtf("List", productList.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class CollectionJsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            collection = result;
            collectionJsonArray(collection);
        }
    }

    private class DetailJsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.wtf("JSON Detail", collectionDetail);
        }
    }

    private class ProductJsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}