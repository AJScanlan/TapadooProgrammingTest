package com.ajscanlan.tapadooprogrammingtest;

import android.content.Context;
import android.os.AsyncTask;

import com.ajscanlan.tapadooprogrammingtest.model.Book;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Alexander Scanlan on 03/11/2015
 */
public class DownloadDescriptionHandler {

    private Context mContext;
    private DownloadCallback mCallback;

    public DownloadDescriptionHandler(Context context, DownloadCallback callback, Book book){
        mCallback = callback;
        mContext = context;

        new DownloadDescTask().execute(book);
    }

    //The task used to fetch descriptions
    private class DownloadDescTask extends AsyncTask<Book, Void, String> {

        Book mBook;

        @Override
        protected String doInBackground(Book... params) {
            try {
                mBook = params[0];

                return DownloadHandler.downloadUrl(DownloadHandler.ID_URL + mBook.getId());
            } catch (IOException e) {
                e.printStackTrace();
                return "A problem has occurred";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject myJsonObject = new JSONObject(s);
                String description = myJsonObject.getString("description");

                mBook.setDescription(description);

                mCallback.finished(description);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
