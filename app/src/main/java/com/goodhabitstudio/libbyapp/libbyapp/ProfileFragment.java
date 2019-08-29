package com.goodhabitstudio.libbyapp.libbyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.goodhabitstudio.libbyapp.libbyapp.R.id.container;

/**
 * Created by dondi-mac on 15/11/17.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView name;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        //Set Profile Name
        name = (TextView) v.findViewById(R.id.profile_name);


        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users/"+uid);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String name_value = dataSnapshot.child("name").getValue(String.class);
                name.setText(name_value);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        TextView b = (TextView) v.findViewById(R.id.logout);
        b.setOnClickListener(this);
        return v;
    }


    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), LandingPage.class);
                startActivity(i);
                break;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null)
        {
            Intent i = new Intent(getActivity(), LandingPage.class);
            startActivity(i);
        }
    }
}
