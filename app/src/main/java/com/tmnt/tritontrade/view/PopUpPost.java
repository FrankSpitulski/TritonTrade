package com.tmnt.tritontrade.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;
import com.tmnt.tritontrade.controller.Post;

import java.util.ArrayList;


/**
 * Created by omarkhawaja on 2/19/17.
 */

public class PopUpPost extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_popup_post);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .8));

        ArrayList<String> dummyPhotos = new ArrayList<>();
        dummyPhotos.add("https://pbs.twimg.com/profile_images/378800000822867536/3f5a00acf72df93528b6bb7cd0a4fd0c.jpeg");
        dummyPhotos.add("http://barkpost-assets.s3.amazonaws.com/wp-content/uploads/2013/11/dogelog.jpg");
        dummyPhotos.add("https://pbs.twimg.com/profile_images/378800000822867536/3f5a00acf72df93528b6bb7cd0a4fd0c.jpeg");
        dummyPhotos.add("http://barkpost-assets.s3.amazonaws.com/wp-content/uploads/2013/11/dogelog.jpg");
        dummyPhotos.add("https://pbs.twimg.com/profile_images/378800000822867536/3f5a00acf72df93528b6bb7cd0a4fd0c.jpeg");

        Post dummy = new Post("Doge", dummyPhotos, "Good Doge 4 u.", 12.42f, null, 0, 0, false, false, null, null, false);
        loadPost(dummy);






    }

    public void loadPost(Post p) {
        ArrayList<ImageView> photos = loadPhotos(p.getPhotos());
        LinearLayout photoContainer = (LinearLayout) findViewById(R.id.gallery);
        LinearLayout.LayoutParams photo_params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < photos.size(); i++) {
            final ImageView photoView = photos.get(i);
            photoView.setLayoutParams(photo_params);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView current_photo = (ImageView) findViewById(R.id.currentphoto);
                    current_photo.setImageDrawable(photoView.getDrawable());
                }
            });
            photoContainer.addView(photoView);
        }

        ImageView current_photo = (ImageView) findViewById(R.id.currentphoto);
        current_photo.setImageDrawable(photos.get(0).getDrawable());

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(p.getProductName());

        TextView price = (TextView) findViewById(R.id.price);
        price.setText(String.valueOf(p.getPrice()));

        TextView desc = (TextView) findViewById(R.id.description);
        desc.setText(p.getDescription());
    }


    public ArrayList<ImageView> loadPhotos(ArrayList<String> photos) {

        ArrayList<ImageView> iv_array = new ArrayList<>();
        for (String u:photos) {
            ImageView d = new ImageView(getApplicationContext());
            new DownloadPhotosAsyncTask(d)
                    .execute(u);
            iv_array.add(d);
        }
        return iv_array;
    }
}
