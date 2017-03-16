package com.tmnt.tritontrade.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.tmnt.tritontrade.R;

/**
 * Created by terrancegriffith on 3/11/17.
 * Screen to update the current users post in their "selling" list
 */

public class EditSellingPost extends AppCompatActivity {

    private Button editText;
    private Button editDescription;
    private Button editPicture;
    private Button submitBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post_popup);

        editText = (Button)findViewById(R.id.change_title_btn);
        editDescription = (Button)findViewById(R.id.change_description_btn);
        editPicture = (Button)findViewById(R.id.change_picture_btn);
        submitBtn = (Button)findViewById(R.id.submit_btn);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);

    }
}
