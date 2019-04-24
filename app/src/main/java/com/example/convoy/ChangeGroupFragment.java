package com.example.convoy;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.String.valueOf;

/*
   This class is to change the Current Group or make a new group

 */
public class ChangeGroupFragment extends Fragment {
    //vars
    private TextView txtAddMember;
    private Button btnAddMember;
    private Button makeGroupBtn;
    private DatabaseReference rootRef;
    private DatabaseReference userGroupRef;
    private FirebaseUser user;
    private ArrayList<Group> aGroupList;

    private String id, userEmail,newName;
    boolean exist = false;
    public static String groupName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_change_group, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //instantiate some variables
        aGroupList = new ArrayList<>();
        rootRef = FirebaseDatabase.getInstance().getReference();
        txtAddMember = getView().findViewById(R.id.txtAddMember);
        btnAddMember = getView().findViewById(R.id.btnAddMember);
        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtAddMember.getText().toString();
                getEmail(email);
                if(exist) {
                    addMember();
                    Toast.makeText(getContext(), email + " added to " + NavActivity.getCurrentGroupID(), Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getContext(), "That user does not exist", Toast.LENGTH_LONG).show();

                exist = false;


            }
        });

        makeGroupBtn = getView().findViewById(R.id.btnMakeGroup);
        makeGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent makeGroupActivity = new Intent(getActivity(),CreateGroupActivity.class);
                startActivity(makeGroupActivity);
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        userGroupRef = rootRef.child("user").child(user.getUid()).child("groups");

        // This listener retrieves all the groups of the current user
        userGroupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                aGroupList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.getKey().equals(NavActivity.getCurrentGroupID()))
                        groupName = snapshot.getValue().toString();
                    aGroupList.add(new Group(snapshot.getKey().toString(), snapshot.getValue().toString()));
                }
                makeRecylcerView(aGroupList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void addMember() {
        DatabaseReference group = rootRef.child("groups").child(NavActivity.getCurrentGroupID());
        group.child("members").child(id).child("name").setValue(newName);
        group.child("members").child(id).child("lat").setValue(1.01);
        group.child("members").child(id).child("long").setValue(1.01);

        DatabaseReference user = rootRef.child("user");
        user.child(id).child("groups").child(NavActivity.getCurrentGroupID()).setValue("the group name");

    }

    // a testing function for the Group ArrayList and adapter
    public void makeFakeGroups(){
        aGroupList.add(new Group("1234", "group Name"));
        aGroupList.add(new Group("12344", "group Name"));
        aGroupList.add(new Group("1245w435", "group Name"));
        aGroupList.add(new Group("1234", "group Name"));
        aGroupList.add(new Group("12344", "group Name"));
        aGroupList.add(new Group("1245w435", "group Name"));
        aGroupList.add(new Group("1234", "group Name"));
        aGroupList.add(new Group("12344", "group Name"));
        aGroupList.add(new Group("1245w435", "group Name"));
        aGroupList.add(new Group("1234", "group Name"));
        aGroupList.add(new Group("12344", "group Name"));
        aGroupList.add(new Group("1245w435", "group Name"));
        aGroupList.add(new Group("1234", "group Name"));
        aGroupList.add(new Group("12344", "group Name"));
        aGroupList.add(new Group("1245w435", "group Name"));
    }

    //Intializing the recycler view
    public void makeRecylcerView(ArrayList<Group> list){
        RecyclerView recyclerView = getView().findViewById(R.id.groupRecycler);
        GroupRecylcerAdapter adapter = new GroupRecylcerAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void getEmail(final String email){

        DatabaseReference users = rootRef.child("user");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String e = snapshot.child("email").getValue().toString();
                    if(e.equals(email)){
                        //Toast.makeText(getContext(),"it exist",Toast.LENGTH_LONG).show();
                        exist = true;
                        userEmail = e;
                        id = snapshot.getKey();
                        newName = snapshot.child("name").getValue().toString();
                        break;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
