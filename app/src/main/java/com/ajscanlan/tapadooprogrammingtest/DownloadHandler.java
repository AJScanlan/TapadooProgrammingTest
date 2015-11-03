package com.ajscanlan.tapadooprogrammingtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ajscanlan.tapadooprogrammingtest.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User1 on 03/11/2015.
 */
public class DownloadHandler {

    static String BASE_URL = "http://private-anon-fd8149e4b-tpbookserver.apiary-mock.com/books";
    static String ID_URL = "http://private-anon-fd8149e4b-tpbookserver.apiary-mock.com/book/";

    private Context mContext;
    private DownloadCallback mCallback;
    private List<Book> books;

    private static final String DEBUG_TAG = "DOWNLOAD_DEBUG_TAG";

    public DownloadHandler(Context c, DownloadCallback callback, String url){
        mContext = c;
        mCallback = callback;

        new DownloadTask().execute(url);
    }

    private String downloadUrl(String param) throws IOException {
        InputStream inputStream = null;

        try{
            //Setting up URL and getting connection object
            URL myUrl = new URL(param);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();

            //set timeout time
            connection.setConnectTimeout(15000); //15 seconds
            connection.setReadTimeout(10000);    //10 seconds
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            // Starts the query
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);

            //get the stream and read
            inputStream = connection.getInputStream();

            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder(inputStream.available());
            String line;

            //read in json data as string
            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            return total.toString();
        } finally {
            if(inputStream != null) inputStream.close(); //close input stream
        }
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //progress dialog for feedback
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("Fetching books..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return downloadUrl(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "A problem has occurred";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray myJsonArray = new JSONArray(s);
                books = new ArrayList<>();

                //temp values for initialising book objects
                int id;
                int price;
                String author;
                String title;
                String ISBN;
                String currencyCode;
                String description;

                //iterate through json array
                for(int i = 0; i < myJsonArray.length(); ++i){
                    JSONObject myJsonObject = myJsonArray.getJSONObject(i);

                    //get the instance variables
                    id = myJsonObject.getInt("id");
                    price = myJsonObject.getInt("price");
                    author = myJsonObject.getString("author");
                    title = myJsonObject.getString("title");
                    ISBN = myJsonObject.getString("isbn");
                    currencyCode = myJsonObject.getString("currencyCode");


                    //add book to list
                    books.add(new Book(id, price, author, title, ISBN, currencyCode));
                }

                dialog.dismiss();

                mCallback.finished(new ArrayList<>(books));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
