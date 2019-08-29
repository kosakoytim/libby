package com.goodhabitstudio.libbyapp.libbyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddReadingPlanSecondPage extends AppCompatActivity {

    private TextView bookTitleTextView;
    private TextView numberOfPagesTextView;
    private String bookTitle;
    private String bookAuthor;
    private String numberOfPages;
    int[] readingDaysArray = {0,0,0,0,0,0,0};
    CheckBox MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reading_plan_second_page);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Barlow-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        Bundle extras = getIntent().getExtras();

        bookTitleTextView = (TextView) findViewById(R.id.readingPlan2_bookTitle);
        numberOfPagesTextView = (TextView) findViewById(R.id.readingPlan2_bookPages);

        MONDAY = findViewById(R.id.monday);
        TUESDAY = findViewById(R.id.tuesday);
        WEDNESDAY = findViewById(R.id.wednesday);
        THURSDAY = findViewById(R.id.thursday);
        FRIDAY = findViewById(R.id.friday);
        SATURDAY = findViewById(R.id.saturday);
        SUNDAY = findViewById(R.id.sunday);

        MONDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MONDAY.isChecked()){
                    readingDaysArray[0] = 1;
                } else {
                    readingDaysArray[0] = 0;
                }
            }
        });

        TUESDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TUESDAY.isChecked()){
                    readingDaysArray[1] = 1;
                } else {
                    readingDaysArray[1] = 0;
                }
            }
        });

        WEDNESDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (WEDNESDAY.isChecked()){
                    readingDaysArray[2] = 1;
                } else {
                    readingDaysArray[2] = 0;
                }
            }
        });

        THURSDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (THURSDAY.isChecked()){
                    readingDaysArray[3] = 1;
                } else {
                    readingDaysArray[3] = 0;
                }
            }
        });

        FRIDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FRIDAY.isChecked()){
                    readingDaysArray[4] = 1;
                } else {
                    readingDaysArray[4] = 0;
                }
            }
        });

        SATURDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SATURDAY.isChecked()){
                    readingDaysArray[5] = 1;
                } else {
                    readingDaysArray[5] = 0;
                }
            }
        });

        SUNDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SUNDAY.isChecked()){
                    readingDaysArray[6] = 1;
                } else {
                    readingDaysArray[6] = 0;
                }
            }
        });


        bookTitle = extras.getString("title");
        bookAuthor = extras.getString("author");
        numberOfPages = extras.getString("pages");

        bookTitleTextView.setText(bookTitle);
        numberOfPagesTextView.setText(numberOfPages);
    }
    public void returnToHome(View view){
        finish();
    }

    public void toThirdPage(View view){
        if (!MONDAY.isChecked() && !TUESDAY.isChecked() && !WEDNESDAY.isChecked() && !THURSDAY.isChecked() && !FRIDAY.isChecked() && !SATURDAY.isChecked() && !SUNDAY.isChecked()){
            Toast.makeText(getApplicationContext(), "Please choose at least 1 day for reading day", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(AddReadingPlanSecondPage.this, AddReadingPlanThirdPage.class);
            intent.putExtra("title", bookTitle);
            intent.putExtra("author", bookAuthor);
            intent.putExtra("pages", numberOfPages);
            intent.putExtra("readingdays", readingDaysArray);
            intent.putExtra("readingarray", readingDaysArray.length);

            Log.v("Monday", Integer.toString(readingDaysArray[0]));
            startActivity(intent);
        }
    }

    public void previousPage(View view){
        finish();
    }
}
