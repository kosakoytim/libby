package com.goodhabitstudio.libbyapp.libbyapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddReadingPlanThirdPage extends AppCompatActivity {


    private FirebaseAuth mAuth;

    // TEXTVIEW & EDITTEXT INITIALIZATION
    private TextView bookTitleTV;
    private TextView numberOfPagesTV;
    private TextView readingDaysTV;
    private EditText startDateET;
    private Spinner readingTimesSpinner;

    // TEMPORARY VARIABLE
    private String bookTitle;
    private String bookAuthor;
    private String numberOfPages;
    private String readingDays;

    // READING DAYS VARIABLE
    private int MONDAY;
    private int TUESDAY;
    private int WEDNESDAY;
    private int THURSDAY;
    private int FRIDAY;
    private int SATURDAY;
    private int SUNDAY;
    private int numberOfReadingTimes;
    private int[] readingDaysArr;

    private Calendar calendar;
    private String calendarFormat = "EEE, d MMM yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(calendarFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reading_plan_third_page);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Barlow-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        mAuth = FirebaseAuth.getInstance();


        Bundle extras = getIntent().getExtras();
        bookTitle = extras.getString("title");
        bookAuthor = extras.getString("author");
        numberOfPages = extras.getString("pages");
        readingDaysArr = extras.getIntArray("readingdays");

        bookTitleTV = (TextView) findViewById(R.id.readingPlan3_bookTitle);
        numberOfPagesTV = (TextView) findViewById(R.id.readingPlan3_bookPages);
        readingDaysTV = (TextView) findViewById(R.id.readingPlan3_readingDays);
        startDateET = (EditText) findViewById(R.id.readingPlan3_startTime);
        readingTimesSpinner = (Spinner) findViewById(R.id.readingPlan3_readingTimeSpinner);

        MONDAY = readingDaysArr[0];
        TUESDAY = readingDaysArr[1];
        WEDNESDAY = readingDaysArr[2];
        THURSDAY = readingDaysArr[3];
        FRIDAY = readingDaysArr[4];
        SATURDAY = readingDaysArr[5];
        SUNDAY = readingDaysArr[6];

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.readingTime, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        readingTimesSpinner.setAdapter(adapter);

        numberOfReadingTimes = Integer.valueOf(readingTimesSpinner.getSelectedItem().toString());

        calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        startDateET.setInputType(InputType.TYPE_NULL);
        startDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddReadingPlanThirdPage.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        for(int item:readingDaysArr){
            Log.i("SecondActivity", String.valueOf(item));
        }

        bookTitleTV.setText(bookTitle);
        numberOfPagesTV.setText(numberOfPages);
        readingDaysTV.setText(getReadingDays(readingDaysArr));

    }
    public void returnToHome(View view){
        finish();
    }

    public void finishPlan(View view){
        if (readingTimesSpinner.getSelectedItemPosition() == 0){
            Toast.makeText(this, "Please choose how many times you like to read", Toast.LENGTH_SHORT).show();
        }else{
            String myFormat = calendarFormat; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            FirebaseUser user = mAuth.getCurrentUser();
            String uid = user.getUid();
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference bookRef = database.getReference("users/"+uid+"/books/list");
            String key =  bookRef.push().getKey();
            bookRef.child(key).child("author").setValue(bookAuthor);
            bookRef.child(key).child("title").setValue(bookTitle);
            bookRef.child(key).child("pages").setValue(Integer.parseInt(numberOfPages));
            bookRef.child(key).child("start_date").setValue(sdf.format(calendar.getTime()));
            bookRef.child(key).child("status").setValue("not_done");
            bookRef.child(key).child("progress_page").setValue(0);
            bookRef.child(key).child("display_reading_days").setValue(getReadingDays(readingDaysArr));
            bookRef.child(key).child("display_reading_time").setValue("09.00, 12.00");
            bookRef.child(key).child("id").setValue(key);

            bookRef.child(key).child("days").child("monday").setValue(readingDaysArr[0]);
            bookRef.child(key).child("days").child("tuesday").setValue(readingDaysArr[1]);
            bookRef.child(key).child("days").child("wednesday").setValue(readingDaysArr[2]);
            bookRef.child(key).child("days").child("thursday").setValue(readingDaysArr[3]);
            bookRef.child(key).child("days").child("friday").setValue(readingDaysArr[4]);
            bookRef.child(key).child("days").child("saturday").setValue(readingDaysArr[5]);
            bookRef.child(key).child("days").child("sunday").setValue(readingDaysArr[6]);

            for(int i = 0; i < numberOfReadingTimes; i++)
            {

            }

            bookRef.child(key).child("times_read_a_day").setValue(numberOfReadingTimes);



            Intent intent = new Intent(AddReadingPlanThirdPage.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public String getReadingDays(int[] arr){
        String temp = "";
        if (arr[0] == 1){
            temp = temp + "Mon ";
        }
        if (arr[1] == 1){
            temp = temp + "Tue ";
        }
        if (arr[2] == 1){
            temp = temp + "Wed ";
        }
        if (arr[3] == 1){
            temp = temp + "Thu ";
        }
        if (arr[4] == 1){
            temp = temp + "Fri ";
        }
        if (arr[5] == 1){
            temp = temp + "Sat ";
        }
        if (arr[6] == 1){
            temp = temp + "Sun";
        }
        return temp;
    }

    private void updateLabel() {
        String myFormat = calendarFormat; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        startDateET.setText(sdf.format(calendar.getTime()));
    }

    public void previousPage(View view){
        finish();
    }
}
