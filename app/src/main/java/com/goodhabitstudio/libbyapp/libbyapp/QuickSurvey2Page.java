package com.goodhabitstudio.libbyapp.libbyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuickSurvey2Page extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String finalYearTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        Intent myIntent = getIntent();
        String yearTarget = myIntent.getStringExtra("yearTarget");
        finalYearTarget = yearTarget;

        setContentView(R.layout.activity_quick_survey2_page);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Barlow-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

    }

    public void back(View view)
    {
        Intent i = new Intent(this, QuickSurvey1Page.class);
        startActivity(i);
    }

    public void finish(View view)
    {
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users/"+uid);
        userRef.child("yearTarget").setValue(finalYearTarget);
        userRef.child("bookRead").setValue("0");

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
