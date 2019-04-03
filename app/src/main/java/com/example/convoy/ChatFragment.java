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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.lang.Object;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatFragment extends Fragment {
    // Firebase instance variables
    private DatabaseReference usersRef , groupRef, groupKeyRef;
    private DatabaseReference rootRef;
    private DatabaseReference messageRef;
    private FirebaseAuth mAuth;
    private TextView messageTextView;

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
        messageTextView = getView().findViewById(R.id.group_chat_text);
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
        getUserInfo();

        sndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    sendMessageToDataBase();

                    messageToSend.setText("");
            }
        });

    }

    private void getUserInfo() {
        usersRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    currentUserName = dataSnapshot.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    @Override
    public void onStart() {
        super.onStart();

        groupRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists())
                {
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists())
                {
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void DisplayMessages(DataSnapshot dataSnapshot)
    {
        messageTextView = getView().findViewById(R.id.group_chat_text);

        Iterator iterator = dataSnapshot.getChildren().iterator();

        while(iterator.hasNext())
        {
            String chatDate = (String) ((DataSnapshot) iterator.next()).getValue();
            String chatMessage = (String) ((DataSnapshot) iterator.next()).getValue();
            String chatName = (String) ((DataSnapshot) iterator.next()).getValue();
            String chatTime = (String) ((DataSnapshot) iterator.next()).getValue();

            messageTextView.append(chatName + "\n" + chatMessage + "\n"+ chatTime + " " + chatDate +"\n\n");
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
