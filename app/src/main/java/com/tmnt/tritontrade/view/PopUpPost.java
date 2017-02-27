package com.tmnt.tritontrade.view;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;
import com.tmnt.tritontrade.controller.Post;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static de.hdodenhof.circleimageview.R.styleable.CircleImageView;


/**
 * Created by omarkhawaja on 2/19/17.
 */

public class PopUpPost extends AppCompatActivity {
    private boolean cart_status = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_post);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .8));

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (cart_status) {
                    b.setText("ADD TO CART");
                    Toast.makeText(PopUpPost.this, "Item removed from cart", Toast.LENGTH_SHORT).show();
                } else {
                    b.setText("REMOVE FROM CART");
                    Toast.makeText(PopUpPost.this, "Item added to cart", Toast.LENGTH_SHORT).show();
                }
                cart_status = !cart_status;
            }
        });

        ArrayList<String> dummyPhotos = new ArrayList<>();
        dummyPhotos.add("http://xiostorage.com/wp-content/uploads/2015/10/test.png");
        dummyPhotos.add("http://barkpost-assets.s3.amazonaws.com/wp-content/uploads/2013/11/dogelog.jpg");
        dummyPhotos.add("https://pbs.twimg.com/profile_images/378800000822867536/3f5a00acf72df93528b6bb7cd0a4fd0c.jpeg");
        dummyPhotos.add("http://barkpost-assets.s3.amazonaws.com/wp-content/uploads/2013/11/dogelog.jpg");
        dummyPhotos.add("https://pbs.twimg.com/profile_images/378800000822867536/3f5a00acf72df93528b6bb7cd0a4fd0c.jpeg");

        Post dummy = new Post("Doge", dummyPhotos, "Good Doge 4 u.", 12.42f, null, 0, 0, false, false, null, null, false);

        //Post p = getIntent().getParcelableExtra("category");
        loadPost(dummy);


    }

    public void loadPost(Post p) {
        ArrayList<ImageView> photos = loadPhotos(p.getPhotos());
        LinearLayout photoContainer = (LinearLayout) findViewById(R.id.gallery);
        LinearLayout.LayoutParams photo_params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < photos.size(); i++) {
            final ImageView photoView = new ImageView(getApplicationContext());
            photoContainer.addView(photoView, i);
            photoView.setImageDrawable(photos.get(i).getDrawable());
            photoView.setLayoutParams(photo_params);
            photoView.setAdjustViewBounds(true);
            photoView.setPadding(0,0,15,0);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView current_photo = (ImageView) findViewById(R.id.currentphoto);
                    current_photo.setImageDrawable(photoView.getDrawable());
                }
            });
        }

        ImageView current_photo = (ImageView) findViewById(R.id.currentphoto);
        current_photo.setImageDrawable(photos.get(1).getDrawable());

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(p.getProductName());

        TextView price = (TextView) findViewById(R.id.price);
        price.setText("$" + String.valueOf(p.getPrice()));

        TextView desc = (TextView) findViewById(R.id.description);
        desc.setText(p.getDescription());
    }


    public ArrayList<ImageView> loadPhotos(ArrayList<String> photos) {

        ArrayList<ImageView> iv_array = new ArrayList<>();
        for (String u : photos) {
            ImageView d = new ImageView(getApplicationContext());
            new DownloadPhotosAsyncTask(d)
                    .execute(u);
            iv_array.add(d);
        }
        return iv_array;

    }


}
