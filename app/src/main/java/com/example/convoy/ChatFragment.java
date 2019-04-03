package com.example.convoy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import java.text.SimpleDateFormat;
import java.lang.Object;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatFragment extends Fragment {
    // Firebase instance variables
    private DatabaseReference usersRef , groupRef, groupKeyRef;
    private DatabaseReference rootRef;
    private DatabaseReference messageRef;
    private FirebaseAuth mAuth;

    //Array for messages
    private ArrayList<MessageClass> messages;


    //The button and text
    private Button sndBtn;
    private EditText messageToSend;
    private ScrollView msgScroll;

    //the txt message itself
    //to hold member data
    private String currentUserId, currentUserName, currentDate, currentTime, currentGroup;
    private GroupMember memberData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sudo code to get current group
        //currentGroup = getIntent;
        //Toast.makeText(ChatFragment.this,currentGroup,Toast.LENGTH_SHORT);
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initilize everything
        //set up the xml objects
        sndBtn = getView().findViewById(R.id.sendBtn);
        messageToSend = getView().findViewById(R.id.txtMessage);
        msgScroll = getView().findViewById(R.id.msgScroller);
        //setup firebase  references
        rootRef = FirebaseDatabase.getInstance().getReference();
        //get firbase needed references
                                                                                                //needed to add child messages
        groupRef = FirebaseDatabase.getInstance().getReference().child("groups").child("group1").child("messages");//FIXME replace group1 with current group
        mAuth = FirebaseAuth.getInstance();
        if(mAuth == null)
        {
            //Intent error = com.example.convoy.AccountActivity.LoginActivity.class;
           // startActivity( com.example.convoy.AccountActivity.LoginActivity.class);

        }
        currentUserId = mAuth.getInstance().getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("user");

        ////////////////////////////////


        sndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    sendMessageToDataBase();

                    messageToSend.setText("");
            }
        });

    }

    private void sendMessageToDataBase() {
        String msg = messageToSend.getText().toString();
        String messageKey = groupRef.push().getKey();

        if(TextUtils.isEmpty(msg))
        {
            //Toast.makeText(this, "Please write message first", Toast.LENGTH_SHORT);
        }
        else
        {
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat( "MMM dd, yyyy");
            currentDate = currentDateFormat.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat( "hh:mm a");
            currentTime = currentTimeFormat.format(calForTime.getTime());

            HashMap<String, Object> groupMessageKey = new HashMap();
            groupRef.updateChildren(groupMessageKey);

            groupKeyRef = groupRef.child(messageKey);

            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("name", currentUserName);
            // had messageToSend and no the msg String
            messageInfoMap.put("message", msg);
            messageInfoMap.put("date", currentDate);
            messageInfoMap.put("time", currentTime);

            groupKeyRef.updateChildren(messageInfoMap);
        }

    }


/*    private void updateFirebaseRefs()
    {
        messageRef = rootRef.child("groups").child("group1").child("messages");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MessageClass oldMessage;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String message = snapshot.child("message").getValue().toString();
                    String msgUser = snapshot.child("user").getValue().toString();
                    long time = Long.parseLong(snapshot.child("timestamp").getValue().toString());
                    oldMessage = MessageClass();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        })
    }
*/
}
