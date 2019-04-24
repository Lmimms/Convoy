package com.example.convoy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {

    private Button updateButton;
    private EditText name;
    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_settings);
        initializeFields();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              UpdateSettings();
            }
        });
    }

    private void UpdateSettings() {
        String newName = name.getText().toString();

        if (TextUtils.isEmpty(newName)){
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
        }
        else{
            rootRef.child("user").child(mAuth.getCurrentUser().getUid()).child("name").setValue(newName);
            startActivity(new Intent(getApplicationContext(), NavActivity.class));
            finish();
        }
    }

    private void initializeFields() {
    updateButton = (Button) findViewById(R.id.update_username_button);
    name = (EditText) findViewById(R.id.set_user_name);
    }
}
