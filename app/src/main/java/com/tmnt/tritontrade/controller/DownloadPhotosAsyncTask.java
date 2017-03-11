package com.tmnt.tritontrade.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Async task that downloads an image from the server with the given url, either http://... or just
 * the relative path
 */

public class DownloadPhotosAsyncTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadPhotosAsyncTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            if(!urldisplay.matches("^http.*$")){
                urldisplay = Server.getServerName() + urldisplay;
            }
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
}

