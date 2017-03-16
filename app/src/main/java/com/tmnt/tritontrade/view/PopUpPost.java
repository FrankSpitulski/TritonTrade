package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by omarkhawaja on 2/19/17.
 */

public class PopUpPost extends AppCompatActivity {
    private Toast t;
    private boolean cart_status = false;
    private User current_user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        t = new Toast(getApplicationContext());
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_post);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .85), (int) (height * .85));

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Post p = getIntent().getParcelableExtra("category");
        loadPost(p);
        updateSellingStatus(p);
        new GetSellerForProfile()
                .execute(p.getProfileID());



        if(!CurrentState.getInstance().isLoggedIn()){
            CurrentState.getInstance().killLogin(this);
            return;
        }

        current_user = CurrentState.getInstance().getCurrentUser();
        if (current_user.getCartIDs().contains(p.getPostID())) {
            cart_status = true;
            ((Button) findViewById(R.id.cart)).setText("REMOVE FROM CART");
        }else if(current_user.getProfileID() == p.getProfileID()){
            if(!p.getActive()) {
                ((Button) findViewById(R.id.cart)).setText("MARK AS ACTIVE");
            }else{
                ((Button) findViewById(R.id.cart)).setText("MARK AS SOLD");
            }
        }
        findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                Toast t = new Toast(getApplicationContext());
                if (cart_status) {
                    current_user.removeFromCart(p.getPostID());
                    new ModifyUserCart().execute(current_user);
                } else {
                    Log.d("DEBUG", "ADDING TO CART? " + b.getText());
                    if(b.getText().equals("ADD TO CART")) {
                        Log.d("DEBUG", "ADDING TO CART");
                        current_user.addToCart(p.getPostID());
                        new ModifyUserCart().execute(current_user);
                    }else if(b.getText().equals("MARK AS ACTIVE")){
                        p.setActive(true);
                        new ModifyPostState().execute(p);
                    }else if(b.getText().equals("MARK AS SOLD")){
                        p.setActive(false);
                        new ModifyPostState().execute(p);
                    }
                }

            }
        });
    }

    private void loadPost(Post p) {
        ArrayList<ImageView> photos = loadGallery(p.getPhotos());
        LinearLayout photoContainer = (LinearLayout) findViewById(R.id.gallery);
        photoContainer.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams photo_params =
                new LinearLayout.LayoutParams(250, 250);
        photo_params.gravity = Gravity.CENTER;
        for (int i = 0; i < photos.size(); i++) {
            final ImageView photoView = photos.get(i);

            photoView.setLayoutParams(photo_params);
            photoView.setPadding(0, 0, 15, 0);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView current_photo = (ImageView) findViewById(R.id.currentphoto);
                    current_photo.setImageDrawable(photoView.getDrawable());
                    Log.d("DEBUG", "clicked");
                }
            });
            photoContainer.addView(photos.get(i), i);
        }

        new DownloadPhotosAsyncTask(this, (ImageView) findViewById(R.id.currentphoto))
                .execute(p.getPhotos().get(0));

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(p.getProductName());

        TextView price = (TextView) findViewById(R.id.price);
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        price.setText(currency.format(p.getPrice()));

        TextView desc = (TextView) findViewById(R.id.description);
        desc.setText(p.getDescription());

        TextView time_ago = (TextView) findViewById(R.id.post_age_2);
        time_ago.setText(DateUtils.getRelativeTimeSpanString(p.getDateCreated().getTime()));


    }


    private ArrayList<ImageView> loadGallery(ArrayList<String> photos) {

        ArrayList<ImageView> iv_array = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            ImageView d = new de.hdodenhof.circleimageview.CircleImageView(getApplicationContext());
            new DownloadPhotosAsyncTask(this,d)
                    .execute(photos.get(i));
            iv_array.add(d);
        }
        return iv_array;
    }

    private class ModifyUserCart extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... params) {
            try {
                Server.modifyExistingUser(params[0]);
                return true;
            } catch (IOException e) {
                Log.d("DEBUG", e.toString());
                return false;
            } catch (IllegalArgumentException e2) {
                Log.d("DEBUG", e2.toString());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            t.cancel();
            if (bool) {
                if (cart_status) {
                    Toast.makeText(PopUpPost.this, "Item removed from cart", Toast.LENGTH_SHORT).show();
                    ((Button) findViewById(R.id.cart)).setText("ADD TO CART");
                } else {
                    t.cancel();
                    Toast.makeText(PopUpPost.this, "Item added to cart", Toast.LENGTH_SHORT).show();
                    ((Button) findViewById(R.id.cart)).setText("REMOVE FROM CART");
                }
                cart_status = !cart_status;
            } else {
                Toast.makeText(PopUpPost.this, "Bad connection to server or bad post ID", Toast.LENGTH_SHORT);
            }
        }
    }

    private class ModifyPostState extends AsyncTask<Post, Void, Post> {
        @Override
        protected Post doInBackground(Post... params) {
            try {
                Server.modifyExistingPost(params[0]);
                return params[0];
            } catch (IOException e) {
                Log.d("DEBUG", e.toString());
                return null;
            } catch (IllegalArgumentException e2) {
                Log.d("DEBUG", e2.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Post p) {
            t.cancel();
            if (p != null) {
                if (p.getActive()) {
                    Toast.makeText(PopUpPost.this, "Item marked as active", Toast.LENGTH_SHORT).show();
                    ((Button) findViewById(R.id.cart)).setText("MARK AS SOLD");
                } else {
                    t.cancel();
                    Toast.makeText(PopUpPost.this, "Item marked as sold", Toast.LENGTH_SHORT).show();
                    ((Button) findViewById(R.id.cart)).setText("MARK AS ACTIVE");
                }
                updateSellingStatus(p);
            } else {
                Toast.makeText(PopUpPost.this, "Bad connection to server or bad post ID", Toast.LENGTH_SHORT);
            }
        }
    }

    private void updateSellingStatus(Post p) {
        if(!p.getActive()){
            ((TextView) findViewById(R.id.postStatusPopup)).setText("SOLD");
            TextView text = (TextView) findViewById(R.id.postStatusPopup);
            text.setTextColor(Color.parseColor("#E50000"));
            text.setTypeface(null, Typeface.BOLD);
            TextView text2 = (TextView) findViewById(R.id.price);
            text2.setTextColor(Color.parseColor("#696969"));
        }else if(p.getSelling()){
            ((TextView) findViewById(R.id.postStatusPopup)).setText("SELLING");
        }else{
            ((TextView) findViewById(R.id.postStatusPopup)).setText("BUYING");
        }
    }

    private class GetSellerForProfile extends AsyncTask<Integer, User, User> {
        @Override
        protected User doInBackground(Integer... params) {
            try {
                return Server.searchUserIDs(params[0].intValue());
            } catch (IOException e) {
                Log.d("DEBUG", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(User user) {
            final User seller = user;
            TextView viewSeller = (TextView) findViewById(R.id.viewseller);
            viewSeller.setText(seller.getName());
            findViewById(R.id.viewseller).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent profileIntent = new Intent(getApplicationContext(), Profile_NonUser.class);
                    profileIntent.putExtra("Profile_NonUser", seller);
                    startActivity(profileIntent);
                }
            });
        }
    }
}
