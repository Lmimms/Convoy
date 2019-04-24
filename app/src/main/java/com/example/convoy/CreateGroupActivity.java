package com.example.convoy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;

public class CreateGroupActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;

    private Button btnMakeGroup;
    private EditText txtGroupName;

    private String userID;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
       // Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_LONG).show();
        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference("groups");
        userRef = database.getReference("user");

        btnMakeGroup = findViewById(R.id.btnMakeGroup);
        txtGroupName = findViewById(R.id.txtGroupNameInput);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.child(userID).child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnMakeGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = txtGroupName.getText().toString();
                String key = rootRef.push().getKey();
                rootRef.child(key).child("name").setValue(groupName);
                rootRef.child(key).child("members").child(userID).child("name").setValue(userName);
                rootRef.child(key).child("members").child(userID).child("lat").setValue(0.0);
                rootRef.child(key).child("members").child(userID).child("long").setValue(0.0);

                userRef.child(userID).child("groups").child(key).setValue(groupName);
                finish();
            }
        });


    }






}
