package com.goodhabitstudio.libbyapp.libbyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

public class ReadingPlanPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_plan_page);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Barlow-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
    }
}
