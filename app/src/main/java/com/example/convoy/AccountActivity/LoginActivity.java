package com.example.convoy.AccountActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.convoy.MainActivity;
import com.example.convoy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;

public class LoginActivity extends AppCompatActivity {


    private EditText userMail, userPassword;
    private Button btnLogin;
    private Button btnRegister2;
    private ProgressBar loginProgress;
    private FirebaseAuth mAuth;
    private Intent MainActivity;
    private ImageView loginPhoto2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.convoy.R.layout.activity_login);

        userMail = findViewById(R.id.loginEmail);
        userPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister2 = findViewById(R.id.btnRegister);
        loginProgress = findViewById(R.id.loginProgress);
        mAuth = FirebaseAuth.getInstance();
        //change whats below to change what opens after login
        MainActivity = new Intent(this, com.example.convoy.actMap.class);
        loginPhoto2 = findViewById(R.id.loginPhoto);

        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(registerActivity);
                finish();
            }
        });

        loginProgress.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgress.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);

                final String mail = userMail.getText().toString();
                final String password = userPassword.getText().toString();

                if(mail.isEmpty() || password.isEmpty())
                {
                    showMessage("please Verify Email and Password");
                    btnLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                }
                else
                {
                    signIn(mail,password);
                }
            }
        });


    }

    private void signIn(String mail, String password) {

            mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        loginProgress.setVisibility(View.INVISIBLE);
                        btnLogin.setVisibility(View.VISIBLE);
                        updateUI();
                    }
                    else
                    {
                        showMessage(task.getException().getMessage());
                        btnLogin.setVisibility(View.VISIBLE);
                        loginProgress.setVisibility(View.INVISIBLE);

                    }
                }
            });

    }

    private void updateUI() {

        startActivity(MainActivity);
        finish();

    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null)
        {
            //user is already connected
            updateUI();

        }
    }
}
