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
import com.example.convoy.SettingsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {


    private EditText userMail, userPassword;
    private Button btnLogin;
    private Button btnRegister2;
    private ProgressBar loginProgress;
    private FirebaseAuth mAuth;
    private Intent MainActivity;
    private ImageView loginPhoto2;
    private DatabaseReference rootRef;


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
        rootRef = FirebaseDatabase.getInstance().getReference();
        //change whats below to change what opens after login
        MainActivity = new Intent(this, com.example.convoy.NavActivity.class);
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

                if(emptyString(mail) || emptyString(password))
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

    private void signIn(final String mail, String password) {

            mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        loginProgress.setVisibility(View.INVISIBLE);
                        btnLogin.setVisibility(View.VISIBLE);

                        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(null == (dataSnapshot.child("user").child(FirebaseAuth.getInstance().getUid()).getValue()))
                                    {
                                        HashMap<String, String> userInfoMap = new HashMap<>();
                                        userInfoMap.put("name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                                        userInfoMap.put("email", mail);
                                        FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).setValue(userInfoMap);
                                    }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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
        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (null != ( dataSnapshot.child("user").child(currentFirebaseUser.getUid()).child("name").getValue())){
                    startActivity(MainActivity);
                    finish();

                    }

                else{ startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void showMessage(String text) {

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

    public static boolean emptyString (String str)
    {
        if(str.isEmpty())
        {
            return true;
        }
        else {
            for (int i = 0; i < str.length(); i++) {
                if ( Character.isLetter(str.charAt(i))) {
                    return false;
                }

            }
            return true;
        }

    }

}
