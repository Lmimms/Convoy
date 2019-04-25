package com.example.convoy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MapFragment extends Fragment implements OnMapReadyCallback  {


    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location userLocation;
    private boolean firstUIUpdate;
    private boolean trackUser;
    private float zoom;



    private String groupID;
    private ArrayList<GroupMember> members;


    private DatabaseReference rootRef;
    private DatabaseReference membersRef;
    private FirebaseUser currentFirebaseUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("mapFragment", "used onCreateView method");

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
        }
        //vars for checing when to track user
        firstUIUpdate = true;
        trackUser = true;
        members = new ArrayList<GroupMember>();
        rootRef = FirebaseDatabase.getInstance().getReference();



        getChildFragmentManager().beginTransaction().replace(R.id.mapFragment, mapFragment).commit();

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        zoom = 17.5f;
        btnSetup();
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setFirebaseRefs();
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("My Location: ", location.toString());
                userLocation = location;
                updateMapUI();
                writeLocation();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,mLocationListener);
        userLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateMapUI();
        writeLocation();
    }

    //Updates the map UI by placing a new marker for every element in the Members array
    private void updateMapUI(){
        double lat = userLocation.getLatitude();
        double log = userLocation.getLongitude();
        LatLng currentLocation = new LatLng(lat, log);
        map.clear();

        for(int i = 0; i<members.size();i++){
            map.addMarker((new MarkerOptions().position(members.get(i).getLocation())
                    .title(members.get(i).getName())));

            //when adding a marker check to see if it is the current user and
            // move camera to that location
            if(members.get(i).getUserID() == currentFirebaseUser.getUid()){
                if(firstUIUpdate || trackUser) {
                    map.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                    map.animateCamera(CameraUpdateFactory.zoomTo(zoom));
                    firstUIUpdate = false;
                }
            }
        }

    }

    // Setup the firebase group reference to listen for changes in group members locations
    private void setFirebaseRefs(){
        groupID = ((NavActivity) getActivity()).getCurrentGroupID();
        membersRef = rootRef.child("groups").child(groupID).child("members");
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        membersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GroupMember member;
                members.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String id = snapshot.getKey();
                    Log.d("Map Frag", "User ID being added to array " + id);
                    String name = snapshot.child("name").getValue().toString();
                    member = new GroupMember(name,(double) snapshot.child("lat").getValue(),(double) snapshot.child("long").getValue(),snapshot.getKey());
                    members.add(member);
                }
                updateMapUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    // wtites the location of the user to the database
    private void writeLocation(){
        double lat = userLocation.getLatitude();
        double log = userLocation.getLongitude();
        membersRef.child(currentFirebaseUser.getUid()).child("lat").setValue(lat);
        membersRef.child(currentFirebaseUser.getUid()).child("long").setValue(log);
    }
    //setup for UI buttons
    private void btnSetup(){

        Switch locationToggle = getView().findViewById(R.id.btnLocationToggle);
        locationToggle.setChecked(true);
        locationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                trackUser = !trackUser;
            }
        });

        Button zoomIn = getView().findViewById(R.id.btnZoomIn);
        Button zoomOut = getView().findViewById(R.id.btnZoomOut);

        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom = zoom + 1;
                updateMapUI();
            }
        });

        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom = zoom -1;
                updateMapUI();
            }
        });



    }

}
