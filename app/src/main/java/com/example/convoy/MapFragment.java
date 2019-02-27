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
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback  {


    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location userLocation;
    private boolean firstUIUpdate;
    private boolean trackUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
        }

        firstUIUpdate = true;
        trackUser = true;

        getChildFragmentManager().beginTransaction().replace(R.id.mapFragment, mapFragment).commit();

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        ImageButton btnLocation = getView().findViewById(R.id.btnLocationButton);
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
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("My Location: ", location.toString());
                userLocation = location;
                updateMapUI();

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

    }

    private void updateMapUI(){
        double lat = userLocation.getLatitude();
        double log = userLocation.getLongitude();
        LatLng currentLocation = new LatLng(lat, log);
        map.clear();
        map.addMarker((new MarkerOptions().position(currentLocation)
                .title("You are here")));
        if(firstUIUpdate || trackUser) {
            map.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            map.animateCamera(CameraUpdateFactory.zoomTo(17.5f));
            firstUIUpdate = false;
        }
    }

}
