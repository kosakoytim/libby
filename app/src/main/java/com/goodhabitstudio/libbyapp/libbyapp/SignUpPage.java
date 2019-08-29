package com.goodhabitstudio.libbyapp.libbyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wang.avi.AVLoadingIndicatorView;

public class SignUpPage extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private String name;
    private String email;
    private String password;
    private EditText nameText;
    private EditText emailText;
    private EditText passwordText;

    //Loading
    private LinearLayout loading;
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_sign_up_page);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Barlow-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        nameText = (EditText) findViewById(R.id.signup_name);
        emailText = (EditText) findViewById(R.id.signup_email);
        passwordText = (EditText) findViewById(R.id.signup_password);

        loading = (LinearLayout) findViewById(R.id.loading_layout);
        loading.setVisibility(View.GONE);
    }



    public void signUp(View view){
        name = nameText.getText().toString();
        email = emailText.getText().toString();
        password = passwordText.getText().toString();

        loading.setVisibility(View.VISIBLE);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        startAnim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference userRef = database.getReference("users/"+uid);
                            userRef.child("name").setValue(name);
                            userRef.child("email").setValue(email);

                            Intent i = new Intent(SignUpPage.this, QuickSurvey1Page.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            loading.setVisibility(View.GONE);

                        }

                        // ...
                    }
                });
    }

    void startAnim(){
        avi.smoothToShow();
    }

    public void closePage(View v){
        finish();
    }
}
