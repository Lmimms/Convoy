package com.example.convoy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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


public class ProfileFragment extends Fragment {

    private DatabaseReference rootRef;
    private FirebaseUser currentFirebaseUser;
    private Button changeNameButton;

    TextView emailTxt, nameTxt;
    String name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Setting up Firebase
        rootRef = FirebaseDatabase.getInstance().getReference();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        emailTxt = (TextView) getView().findViewById(R.id.txtProfileEmail);
        nameTxt = (TextView) getView().findViewById(R.id.txtUserName);
        emailTxt.setText(currentFirebaseUser.getEmail().toString());

        changeNameButton = (Button) getView().findViewById(R.id.btnChangeName);
        //reading the users name from Firebase and adding to textView
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object test;
                if (null != (test = dataSnapshot.child("user").child(currentFirebaseUser.getUid()).child("name").getValue())){
                String name = test.toString();
                nameTxt.setText(name);}

                else startActivity(new Intent(getActivity(), SettingsActivity.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    changeNameButton.setOnClickListener(new View.OnClickListener() {
      @Override
       public void onClick(View v) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));

        }
    });
    }
}
