package com.example.convoy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.convoy.AccountActivity.LoginActivity;
import com.example.convoy.AccountActivity.RegisterActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    if(savedInstanceState==null) {//FIXME change to login
     /*   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ChatFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_chat);*/


        Intent mapIntent = new Intent(this, LoginActivity.class);
        startActivity(mapIntent);
    }}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_map://Fixme how to change to map
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                  //      new actMap()).commit();
                break;
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChatFragment()).commit();
                break;
            case R.id.nav_game:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GameFragment()).commit();
                break;
            case R.id.nav_logout: //FIXME how to change to login
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
               // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                 //       new LoginActivity()).commit();
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
}
