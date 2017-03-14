package com.tmnt.tritontrade.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.InputStream;

/**
 * Async task that downloads an image from the server with the given url, either http://... or just
 * the relative path
 */

public class DownloadPhotosAsyncTask extends AsyncTask<String, Void, String> {
        ImageView bmImage;
        Context context1;

        public DownloadPhotosAsyncTask(Context context, ImageView bmImage) {
            this.bmImage = bmImage;
            this.context1=context;

        }

        protected String doInBackground(String... urls) {

            String urldisplay = urls[0];

            if(!urldisplay.matches("^http.*$")){
                urldisplay = Server.getServerName() + urldisplay;
            }

            return urldisplay;

          /*  Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
            */
        }

        protected void onPostExecute(String result) {
            Glide.with(context1).load(result).into(bmImage);
            //bmImage.setImageBitmap(Bitmap.createScaledBitmap(result, bmImage.getWidth(), bmImage.getHeight(), true));
            //final double sizeFactor = (double)bmImage.getWidth() / (double)result.getWidth();
            //final int width = (int) (result.getWidth() * sizeFactor);
            //final int height = (int) (result.getHeight() * sizeFactor);
            //bmImage.setImageBitmap(Bitmap.createScaledBitmap(result, width, height, true));
        }
}

