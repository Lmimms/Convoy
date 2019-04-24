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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ChangeGroupFragment extends Fragment {

    private Button makeGroupBtn;
    private ScrollView groupScroller;
    private TextView groupInfo;
    private DatabaseReference rootRef;
    private DatabaseReference userGroupRef;
    private DatabaseReference groupNameRef;
    private FirebaseUser user;

    private ArrayList<Group> aGroupList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_change_group, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aGroupList = new ArrayList<>();
        rootRef = FirebaseDatabase.getInstance().getReference();


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
        userGroupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                aGroupList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    aGroupList.add(new Group(snapshot.getKey().toString(), snapshot.getValue().toString()));
                }
                makeRecylcerView(aGroupList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

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

    public void makeRecylcerView(ArrayList<Group> list){
        RecyclerView recyclerView = getView().findViewById(R.id.groupRecycler);
        GroupRecylcerAdapter adapter = new GroupRecylcerAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
