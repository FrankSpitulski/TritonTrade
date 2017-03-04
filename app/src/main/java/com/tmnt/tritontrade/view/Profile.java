package com.tmnt.tritontrade.view;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.User;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;

public class Profile extends AppCompatActivity {

    private ListView list;
    private int EDIT_PROFILE = 1;

    private User currUser;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        currUser = new User("User", "http://farm7.staticflickr.com/6047/7036787275_951cb768fe.jpg", 0,
                "This is the bio for the user", "(510) 696-6969", "email@lol.edu",
                "jkdsf", "sdfs", null, false, null, "sdfs", false);

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
                        switch (item.getItemId()) {
                            case R.id.bottom_mainfeed:
                                startActivity(new Intent(getApplicationContext(), Mainfeed.class));
                                break;
                            case R.id.bottom_cart:
                                startActivity(new Intent(getApplicationContext(), Cart.class));
                                break;
                            case R.id.bottom_upload:
                                startActivity(new Intent(getApplicationContext(), Create_Post.class));
                                break;
                            case R.id.bottom_profile:
                                startActivity(new Intent(getApplicationContext(), Profile.class));
                                break;
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
        Post[] posts = new Post[10];

        for (int i = 0; i < posts.length; i++) {


            Post p = new Post("Product Title " + i, null, "Description of product. This is going to probably be a multiple line " +
                    "description. What were to happen if I added more lines to this shit? " +
                    "Description # " + i, 69, null, 0, 0, false, false, null, "hi", false);


            posts[i] = p;


        }

        //update the list
        list.setAdapter(new ProfileListAdaptor(this, posts));

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