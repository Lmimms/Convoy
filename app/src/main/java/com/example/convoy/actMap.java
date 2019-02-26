package com.example.convoy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class actMap extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private GoogleMap mMap;
    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private Location userLocation;
    private boolean firstUIUpdate;
    private boolean trackUser;


    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment;
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);

        final ImageButton btnLocation = findViewById(R.id.btnLocationButton);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!trackUser){
                    //need to add change of icon
                    trackUser = true;
                }
                else{
                    trackUser = false;
                }

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        firstUIUpdate = true;
        trackUser = true;
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            //
            //updates map with location on location change
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

        //Check Permissions and request if access not granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);

        }
        //acces granted request location
        else{
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,mLocationListener);
            userLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            updateMapUI();
            writeLocation();
        }
    }

    //checks permissions for location
    // skips missiong permission
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                    userLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    updateMapUI();
                    writeLocation();
                }
            }
        }
    }

    private void updateMapUI(){
        double lat = userLocation.getLatitude();
        double log = userLocation.getLongitude();
        LatLng currentLocation = new LatLng(lat, log);
        mMap.clear();
        mMap.addMarker((new MarkerOptions().position(currentLocation)
                .title("You are here")));
        if(firstUIUpdate || trackUser) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.5f));
            firstUIUpdate = false;
        }
    }

    private void writeLocation(){
        double lat = userLocation.getLatitude();
        double log = userLocation.getLongitude();
        mDatabase.child("user").child("jbarksdale").child("lat").setValue(lat);
        mDatabase.child("user").child("jbarksdale").child("log").setValue(log);

    }
}
