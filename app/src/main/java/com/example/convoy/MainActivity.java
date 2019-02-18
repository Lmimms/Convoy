package com.example.convoy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.convoy.AccountActivity.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent mapIntent = new Intent(this, RegisterActivity.class);
        startActivity(mapIntent);
    }
}
