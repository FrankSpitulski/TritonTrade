package com.tmnt.tritontrade.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.User;


public class Profile_NonUser extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__non_user);


        User user = CurrentState.getInstance().getCurrentUser();

        new DownloadPhotosAsyncTask((ImageView) findViewById(R.id.userPic))
                .execute(user.getPhoto());

        populateUserInfo(user);

        populateList();


    }


    private void populateUserInfo(User user){

        //Get references to all the relevant fields in the profle
        TextView username = (TextView) findViewById(R.id.username);
        TextView email = (TextView) findViewById(R.id.email);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView bio = (TextView) findViewById(R.id.description);

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

        for(int i = 0; i < posts.length; i++){


            Post p = new Post("Product Title " + i , null, "Description of product. This is going to probably be a multiple line " +
                    "description. What were to happen if I added more lines to this shit? " +
                    "Description # " + i, 69, null, 0, 0, false, false, null, "hi", false);


            posts[i] = p;


        }

        //update the list
        //list.setAdapter(new ProfileListAdaptor(this, posts));

    }
}
