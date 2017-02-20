package com.tmnt.tritontrade.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.tmnt.tritontrade.R;

public class Bottom_Tool_Bar extends AppCompatActivity {

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom__tool__bar);

        mBottomBar.setItems(R.menu.bottom_nav_items);


        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {

            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                showToast(menuItemId, false);
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                showToast(menuItemId, true);
            }
        });
    }

    private void showToast(int menuId, boolean isReselected) {
        if (menuId == R.id.bottom_mainfeed) {

        } else if (menuId == R.id.bottom_upload) {

        } else if (menuId == R.id.bottom_cart) {

        } else if (menuId == R.id.bottom_profile){

        }
    }

    private void show(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }
}