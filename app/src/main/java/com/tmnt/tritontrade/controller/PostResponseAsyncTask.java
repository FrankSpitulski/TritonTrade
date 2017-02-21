package com.tmnt.tritontrade.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.tmnt.tritontrade.controller.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class PostResponseAsyncTask extends AsyncTask<String, Void, String> {

    private ProgressDialog progressDialog;

    private AsyncResponse delegate;
    private Context context;
    private HashMap<String, String> postData =
            new HashMap<String, String>();
    private String loadingMessage = "Loading...";


    //Constructor
    public PostResponseAsyncTask(AsyncResponse delegate){
        this.delegate = delegate;
        this.context = (Context)delegate;
    }

    public PostResponseAsyncTask(AsyncResponse delegate,
                                 HashMap<String, String> postData){
        this.delegate = delegate;
        this.context = (Context)delegate;
        this.postData = postData;
    }


    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(loadingMessage);
        progressDialog.show();
        super.onPreExecute();
    }//onPreExecute

    @Override
    protected String doInBackground(String... urls){

        String result = "";

        return result;
    }//doInBackground


    @Override
    protected void onPostExecute(String result) {

        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }

        result = result.trim();

        delegate.processFinish(result);
    }//onPostExecute

    //Setter and Getter
    public String getLoadingMessage() {
        return loadingMessage;
    }

    public void setLoadingMessage(String loadingMessage) {
        this.loadingMessage = loadingMessage;
    }

    public HashMap<String, String> getPostData() {
        return postData;
    }

    public void setPostData(HashMap<String, String> postData) {
        this.postData = postData;
    }

    public Context getContext() {
        return context;
    }

    public AsyncResponse getDelegate() {
        return delegate;
    }

    //End Setter & Getter
}