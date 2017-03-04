package com.tmnt.tritontrade.view;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;

import java.io.IOException;
import java.util.ArrayList;

import static com.tmnt.tritontrade.R.id.bottom_cart;
import static com.tmnt.tritontrade.R.id.bottom_mainfeed;
import static com.tmnt.tritontrade.R.id.bottom_profile;
import static com.tmnt.tritontrade.R.id.bottom_upload;

public class Profile extends AppCompatActivity {

    private ListView list;
    private int EDIT_PROFILE = 1;


    private Button forSale;
    private Button sold;


    private User currUser;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private class GetPostsTask extends AsyncTask<ArrayList<Integer>, Void, ArrayList<Post>> {

        ArrayList<Post> posts;

        @Override
        protected ArrayList<Post> doInBackground(ArrayList<Integer>... params) {
            try {
                posts = Server.searchPostIDs(params[0]);
            }
            catch(IOException e){
                Log.d("DEBUG", e.toString());
            }
            return posts;
        }
/*
        @Override
        protected void onPostExecute(Void result){
            startActivity(new Intent(getApplicationContext(), Mainfeed.class));
        }*/
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        currUser = CurrentState.getInstance().getCurrentUser();
        if(currUser == null) {
            throw new NullPointerException("The User is Null");
        }


        forSale = (Button) findViewById(R.id.forSaleButton);
        forSale.setTextColor(Color.parseColor("#FFEE00"));

        sold = (Button) findViewById(R.id.soldButton);

        new DownloadPhotosAsyncTask((ImageView) findViewById(R.id.userPic))
                .execute(currUser.getPhoto());


        populateUserInfo(currUser);
        populateList();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //bottom tool bar

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if(item.getItemId() == bottom_mainfeed){
                            Intent in=new Intent(getBaseContext(),Mainfeed.class);
                            startActivity(in);
                        }
                        else if (item.getItemId() == bottom_cart){
                            Intent in=new Intent(getBaseContext(),Cart.class);
                            startActivity(in);
                        }
                        else if(item.getItemId() == bottom_upload){
                            Intent in=new Intent(getBaseContext(),Create_Post.class);
                            startActivity(in);
                        }
                        else if(item.getItemId() == bottom_profile){
                            Intent in=new Intent(getBaseContext(),Profile.class);
                            startActivity(in);
                        }
                        return false;
                    }
                }
        );

    }


    public void sendToEditProfile(View view) {

        Intent intent = new Intent(this, EditProfile.class);

        intent.putExtra("user", currUser);

        startActivityForResult(intent, EDIT_PROFILE);

    }

    public void onForSaleClick (View view){
        forSale.setTextColor(Color.parseColor("#FFEE00"));
        sold.setTextColor(Color.WHITE);

    }

    public void onSoldClick (View view){
        sold.setTextColor(Color.parseColor("#FFEE00"));
        forSale.setTextColor(Color.WHITE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_PROFILE){
            if(resultCode == Activity.RESULT_OK) {
                currUser = data.getParcelableExtra("updatedUser");
                populateUserInfo(currUser);
            }
        }
    }


    private void populateUserInfo(User user) {

        //Get references to all the relevant fields in the profle
        TextView username = (TextView) findViewById(R.id.username);
        TextView email = (TextView) findViewById(R.id.email);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView bio = (TextView) findViewById(R.id.bio);

        //populate the fields with the information obtained from the user

        username.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getMobileNumber());
        bio.setText(user.getBio());

    }


    private void populateList() {

        list = (ListView) findViewById(R.id.list);

        //random posts
        ArrayList<Integer> postIds = currUser.getPostHistory();
        ArrayList<Post> posts;
        /*
        for (int i = 0; i < posts.size(); i++) {


        }

        //update the list
        //list.setAdapter(new ProfileListAdaptor(this, posts));
        */

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Profile Page")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


}