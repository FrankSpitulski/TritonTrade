package com.tmnt.tritontrade.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.tmnt.tritontrade.R.id.bottom_cart;
import static com.tmnt.tritontrade.R.id.bottom_mainfeed;
import static com.tmnt.tritontrade.R.id.bottom_profile;
import static com.tmnt.tritontrade.R.id.bottom_upload;


public class Profile_NonUser extends AppCompatActivity {

    private ListView list;
    private int EDIT_PROFILE = 1;


    private Button forSale;
    private Button sold;


    private User currUser;


    ArrayList<Post> selling = new ArrayList<>();
    ArrayList<Post> productSold = new ArrayList<>();

    ProfileListAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__non_user);


        Intent intent = getIntent();

        try {
            currUser = intent.getParcelableExtra("Profile_NonUser");
        } catch (Exception e){
            e.printStackTrace();
        }

        /*
        new DownloadPhotosAsyncTask((ImageView) findViewById(R.id.userPic))
                .execute(currUser.getPhoto());
        */

        //Set the selling and for sale buttons
        forSale = (Button) findViewById(R.id.forSaleButton);
        forSale.setTextColor(Color.parseColor("#FFEE00"));

        sold = (Button) findViewById(R.id.soldButton);

        //populate the fields with the relevant info
        populateUserInfo(currUser);
        populateList();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if(item.getItemId() == bottom_mainfeed){
                            Intent in=new Intent(getBaseContext(),Mainfeed.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(in);
                            return true;
                        }
                        else if (item.getItemId() == bottom_cart){
                            Intent in=new Intent(getBaseContext(),Cart.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(in);
                            return true;

                        }
                        else if(item.getItemId() == bottom_upload){
                            Intent in=new Intent(getBaseContext(),Create_Post.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(in);
                            return true;
                        }
                        else if(item.getItemId() == bottom_profile){
                            Intent in=new Intent(getBaseContext(),Profile.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(in);
                            return true;
                        }
                        return false;
                    }
                }
        );

    }

    private class PopulateListTask extends AsyncTask<ArrayList<Integer>, Void, ArrayList<Post>> {
        private ProgressDialog dialog=new ProgressDialog(Profile_NonUser.this);
        protected ArrayList<Post> doInBackground(ArrayList<Integer>... params) {
            try {
                ArrayList<Post> posts = Server.searchPostIDs(currUser.getPostHistory());
                return posts;
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading");
            this.dialog.show();
        }

        protected void onPostExecute(ArrayList<Post> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }


            if(result != null){

                for(int i = 0; i < result.size(); i++){
                    if(result.get(i).getSelling()){
                        selling.add(result.get(i));
                    } else if(result.get(i).getSold()) {
                        productSold.add(result.get(i));
                    }
                }

                adapter = new ProfileListAdaptor(Profile_NonUser.this, selling, true);
                list.setAdapter(adapter);
            }

        }
    }

    public void onForSaleClick (View view){
        forSale.setTextColor(Color.parseColor("#FFEE00"));
        sold.setTextColor(Color.WHITE);

        //Update list to only show items for sale
        adapter = new ProfileListAdaptor(Profile_NonUser.this, selling, true);
        list.setAdapter(adapter);
        list.deferNotifyDataSetChanged();

    }

    public void onSoldClick (View view){
        sold.setTextColor(Color.parseColor("#FFEE00"));
        forSale.setTextColor(Color.WHITE);

        //Update list to only show Items that have been sold
        adapter = new ProfileListAdaptor(Profile_NonUser.this, productSold, true);
        list.setAdapter(adapter);
        list.deferNotifyDataSetChanged();
    }

    private void populateUserInfo(User user) {

        //Get references to all the relevant fields in the profle
        TextView username = (TextView) findViewById(R.id.username);
        TextView email = (TextView) findViewById(R.id.email);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView bio = (TextView) findViewById(R.id.bio);



        //populate the fields with the information obtained from the user

        try {
            new DownloadPhotosAsyncTask((ImageView) findViewById(R.id.userPic))
                    .execute(currUser.getPhoto());
        } catch (Exception e) {
            ImageView userPic = (ImageView) findViewById(R.id.userPic);
            userPic.setBackgroundResource(R.drawable.default_photo);
        }

        try {
            username.setText(user.getName());
        } catch (NullPointerException e) {
            username.setText("N/A");
        }

        try {
            email.setText(user.getEmail());
        } catch (NullPointerException e) {
            email.setText("N/A");
        }

        try {
            phone.setText(user.getMobileNumber().substring(6));
        } catch (NullPointerException e) {
            phone.setText("N/A");
        }

        try {
            bio.setText(user.getBio());
        } catch (NullPointerException e) {
            bio.setText("N/A");
        }





    }

    private void populateList() {

        list = (ListView) findViewById(R.id.list);
        //random posts
        ArrayList<Integer> postIds = new ArrayList<>();
        new PopulateListTask().execute(postIds);
    }

    static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }


}
