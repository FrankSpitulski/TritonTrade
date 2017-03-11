package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.tmnt.tritontrade.R;

public class Bottom_Tool_Bar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom__tool__bar);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

//        bottomNavigationView.setOnNavigationItemSelectedListener
//                (new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        Fragment selectedFragment = null;
//                        Intent in;
//                        switch (item.getItemId()) {
//                            case R.id.bottom_mainfeed:
//                                //in=new Intent(getBaseContext(),Mainfeed.class);
//                                //in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                                //startActivity(in);
//                                break;
//                            case R.id.bottom_edit_category:
////                                selectedFragment = EditCategoryFragment.newInstance();
//                                in =new Intent(getBaseContext(),Edit_Categories.class);
//                                in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                                startActivity(in);
//                                return true;
//                            case R.id.bottom_upload:
////                                selectedFragment = CreatePostFragment.newInstance();
//                                in =new Intent(getBaseContext(),Create_Post.class);
//                                in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                                startActivity(in);
//                                return true;
//                            case R.id.bottom_cart:
//                                selectedFragment = CartFragment.newInstance();
//                                break;
//                            case R.id.bottom_profile:
//                                selectedFragment = ProfileFragment.newInstance();
//                                break;
//                        }
//                        FragmentTransaction transaction = getSupportFragmentManager().
//                                beginTransaction();
//                        transaction.replace(R.id.frame_layout, selectedFragment);
//                        transaction.commit();
//                        return true;
//                    }
//                });
//        FragmentTransaction transaction = getSupportFragmentManager().
//                beginTransaction();
//        transaction.replace(R.id.frame_layout, MainFeedFragment.newInstance());
//        transaction.commit();

    }
}