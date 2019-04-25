package com.example.convoy.AccountActivity;

import android.Manifest;
//import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
//import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
//import android.widget.ImageView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.convoy.MainActivity;
import com.example.convoy.MapFragment;
import com.example.convoy.NavActivity;
import com.example.convoy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.lang.*;
import java.util.HashMap;
//import com.example.convoy.R;

public class RegisterActivity extends AppCompatActivity {

    android.widget.ImageView regUserPhoto;
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImgUri;


    private EditText regMail, regPassword, confPassword;
    private ProgressBar loadingProgress;
    private Button btnRegister;
    private Button btnBack;

    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;

    Intent mainIntenet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //inu views
        regMail = findViewById(R.id.regMail);
        regPassword = findViewById(R.id.regPassword);
        confPassword = findViewById(R.id.confPassword);
        btnBack = findViewById(R.id.btnBackLogin);
        loadingProgress = findViewById(R.id.loadingProgress);
        btnRegister = findViewById(R.id.btnRegister);
        loadingProgress.setVisibility(View.INVISIBLE);



        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        mainIntenet = new Intent(this, LoginActivity.class);



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backLogin = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(backLogin);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                      btnRegister.setVisibility(View.INVISIBLE);
                      loadingProgress.setVisibility(View.VISIBLE);
                      final String email = regMail.getText().toString();
                      final String password = regPassword.getText().toString();
                      final String password2 = confPassword.getText().toString();
                      final String name = "a";

                      if( fieldsAreEmpty(email,name,password,password2))
                      {
                          //display error
                          showMessage("Please fill all fields");
                          btnRegister.setVisibility(View.VISIBLE);
                          loadingProgress.setVisibility(View.INVISIBLE);
                      }
                      else if ( !(matchPass(password, password2)))
                      {
                          showMessage("Please match passwords");
                          btnRegister.setVisibility(View.VISIBLE);
                          loadingProgress.setVisibility(View.INVISIBLE);
                      }
                      else if (!validPass(password))
                      {
                          showMessage("Please enter valid passwords");
                          btnRegister.setVisibility(View.VISIBLE);
                          loadingProgress.setVisibility(View.INVISIBLE);
                      }
                      else//this will be where we make the account after we do more checking
                      {
                                CreateUserAccount(email, name, password);
                                mAuth.signOut();
                                startActivity(mainIntenet);
                                finish();
                      }

                                           }
        });



        regUserPhoto = findViewById(com.example.convoy.R.id.userPhoto);
        regUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= 22)
                {
                    checkAndRequestForPermission();
                }
                else
                {
                    openGallery();
                }


            }
        });


    }

    public static boolean fieldsAreEmpty(String e, String n, String p1, String p2){
        if(e.isEmpty() || n.isEmpty() || p1.isEmpty() || p2.isEmpty())
            return true;
        else
            return false;
    }

    private void CreateUserAccount(final String email, final String name, String password)
    {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                           ///update database
                            //showMessage("ACCOUNT IS ADDING NOWWWW??????????????????????????");
                           // final String currentNewId = mAuth.getCurrentUser().getUid();
                            //rootRef = FirebaseDatabase.getInstance().getReference();

                           // showMessage("Account Created!");
                          //  updateUserInfo( name, pickedImgUri, mAuth.getCurrentUser());
                        }
                        else
                        {
                            showMessage("Account creation failed " + task.getException().getMessage());
                            btnRegister.setVisibility(View.VISIBLE);
                            loadingProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });

        //String currentNewId = mAuth.getCurrentUser().getUid();
        //HashMap<String, Object> userInfoMap = new HashMap<>();
        //userInfoMap.put("name", name);
        //userInfoMap.put("email", email);
        //rootRef.child("user").child(currentNewId);
        //UserTemplate newUser = new UserTemplate(name, email);
        //rootRef.child("user").child(currentNewId).setValue(userInfoMap);
    }

    private void updateUserInfo(final String name, Uri pickedURI, final FirebaseUser currentUser)
    {
       // StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
     /*   final StorageReference imageFilePath = mStorage.child(pickedURI.getLastPathSegment());



        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
*/
                // image uploaded succesfully
       //         imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
         //           @Override
           //         public void onSuccess(Uri uri) {
                        //uri contains user image url

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build();

                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if( task.isSuccessful())
                                        {
                                            showMessage("Register Complete");
                                            updateUI();
                                        }
                                    }
                                });
/*
                    }
                });

            }
        });*/



    }

    //this function will take us to our home page
    private void updateUI() {
        Intent MainActivity = new Intent(getApplicationContext(), com.example.convoy.AccountActivity.LoginActivity.class);
        startActivity(MainActivity);
        mAuth.signOut();
        finish();
    }

    private void showMessage(String p)
    {
        Toast.makeText(getApplicationContext(), p, Toast.LENGTH_LONG).show();
    }


    private void openGallery() {

            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void checkAndRequestForPermission() {

        if(ContextCompat.checkSelfPermission( RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                Toast.makeText(RegisterActivity.this, "PLease accept for required permission", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                        PReqCode);
            }
        }
        else
        {
            openGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null)
        {
            //user has successfllt picked an image &&  saved for later use
            pickedImgUri = data.getData();
            regUserPhoto.setImageURI(pickedImgUri);
        }

    }

    public static boolean validPass(String pass)
    {
        if(pass.length() < 6)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public static boolean matchPass(String passOne, String passTwo)
    {
        return  (passOne.equals(passTwo));
    }
}
