package com.goodhabitstudio.libbyapp.libbyapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Button readingTargetDialogUpdateButton;
    private Button readingTargetDialogCancelButton;
    private Button readingFinishedDialogUpdateButton;
    private Button readingFinishedDialogCancelButton;
    private Button changeTargetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Barlow-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.rootLayout, HomeFragment.newInstance());
        transaction.commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment currentFragment = null;
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        currentFragment = HomeFragment.newInstance();
                        break;
                    case R.id.menu_mylibrary:
                        currentFragment = MyLibraryFragment.newInstance();
                        break;
                    case R.id.menu_profile:
                        currentFragment = ProfileFragment.newInstance();
                        break;
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.rootLayout, currentFragment);
                transaction.commit();
                return true;

            }
        });
    }

    public void showReadingTargetDialog(View v){
        final Dialog readingTargetDialog = new Dialog(this);
        readingTargetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        readingTargetDialog.setContentView(R.layout.reading_target_dialogbox);
        readingTargetDialog.show();

//        readingTargetDialogCancelButton = findViewById(R.id.targetCancelButton);
//        readingTargetDialogUpdateButton = findViewById(R.id.targetUpdateButton);
//
//        readingTargetDialogCancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                readingTargetDialog.dismiss();
//            }
//        });
//
//        readingTargetDialogUpdateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                readingTargetDialog.dismiss();
//            }
//        });
        readingTargetDialog.setCanceledOnTouchOutside(true);
    }

    public void toReadingPlanPage(View view){
        Intent intent = new Intent(MainActivity.this, AddReadingPlanFirstPage.class);
        startActivity(intent);
    }

}
