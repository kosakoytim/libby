package com.goodhabitstudio.libbyapp.libbyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class QuickSurvey1Page extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String yearTarget;
    private EditText yearTargetField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_quick_survey1_page);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Barlow-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        yearTargetField = (EditText) findViewById(R.id.number_of_books);


    }

    public void next(View view)
    {
        yearTarget = yearTargetField.getText().toString();


        if(yearTarget==null)
        {
            Toast.makeText(this, "Mohon isi survey dengan lengkap", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent myIntent = new Intent(this, QuickSurvey2Page.class);
            myIntent.putExtra("yearTarget",yearTarget);
            startActivity(myIntent);
        }

    }

}
