package com.example.convoy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.convoy.AccountActivity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NavActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private String currentGroupID;


    public String getCurrentGroupID(){ return currentGroupID; }
    public void setCurrentGroupID(String ID){ this.currentGroupID = ID; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        currentGroupID = "group1";

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Check Permissions and request if access not granted
        getPermissions();


        if(savedInstanceState==null) {//FIXME change to login
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ChatFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_chat);

          }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            /*case R.id.nav_map://Fixme how to change to map
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,

                break;*/
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChatFragment()).commit();
                break;
            case R.id.nav_poll:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PollFragment()).commit();
                break;
            case R.id.nav_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MapFragment()).commit();
                break;
            case R.id.nav_game:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GameFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_logout: //FIXME how to change to login
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                //       new LoginActivity()).commit();
                Intent registerActivity2;
                registerActivity2 = new Intent(getApplicationContext(),LoginActivity.class);
                FirebaseAuth what = FirebaseAuth.getInstance();
                what.signOut();
                startActivity(registerActivity2);
                finish();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

    private void getPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);

        }
    }

}
