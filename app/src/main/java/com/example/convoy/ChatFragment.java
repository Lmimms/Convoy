package com.example.convoy;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


import com.example.convoy.AccountActivity.MessageClass;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    private DatabaseReference messageRef;
    private FirebaseUser currentUser;
    private ArrayList<MessageClass> messages;


    //The button and text
    private Button sndBtn;
    private EditText messageToSend;

    //the txt message itself
    private String msg;
    //to hold member data
    private GroupMember memberData;

 ////
 ////


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //

        sndBtn = getView().findViewById(R.id.sendBtn);
        messageToSend = getView().findViewById(R.id.txtMessage);

        sndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    msg = messageToSend.getText().toString();
                    if (msg.isEmpty())
                    {
                    }
                    else
                    {

                    }
            }
        });
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }


    private void updateFirebaseRefs()
    {
    }

}
