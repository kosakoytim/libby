package com.goodhabitstudio.libbyapp.libbyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AddReadingPlanFirstPage extends AppCompatActivity {

    private EditText bookTitleEditText;
    private EditText bookAuthorEditText;
    private EditText numberOfPagesEditText;
    private String bookTitle;
    private String bookAuthor;
    private String numberOfPages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reading_plan_first_page);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Barlow-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        bookTitleEditText = (EditText) findViewById(R.id.readingPlan1_bookTitle);
        bookAuthorEditText = (EditText) findViewById(R.id.readingPlan1_author);
        numberOfPagesEditText = (EditText) findViewById(R.id.readingPlan1_numOfPages);

    }
    public void returnToHome(View view){
        finish();
    }

    public void toSecondPage(View view){
        if (bookTitleEditText.getText() == null || bookTitleEditText.equals("") || bookAuthorEditText.getText() == null || bookAuthorEditText.getText().equals("") || numberOfPagesEditText.getText() == null || numberOfPagesEditText.getText().equals("")){
            Toast.makeText(getApplicationContext(), "Please complete all the form available", Toast.LENGTH_SHORT).show();
        }else{
            bookTitle = bookTitleEditText.getText().toString();
            bookAuthor = bookAuthorEditText.getText().toString();
            numberOfPages = numberOfPagesEditText.getText().toString();

            Intent intent = new Intent(AddReadingPlanFirstPage.this, AddReadingPlanSecondPage.class);
            intent.putExtra("title", bookTitle);
            intent.putExtra("author", bookAuthor);
            intent.putExtra("pages", numberOfPages);

            startActivity(intent);
        }
    }
}
