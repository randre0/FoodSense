package com.cs125.foodsense;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


public class MenuActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigation;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mBottomNavigation = findViewById(R.id.navigationView);

        mBottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch(menuItem.getItemId()) {
                    case R.id.navigation_food_journal:
                        break;
                    case R.id.navigation_log_food:
                        fragment = new LogFoodFragment();
                        mFragmentTransaction.add(R.id.container, fragment);
                        mFragmentTransaction.commit();
                        break;
                    case R.id.navigation_user_profile:
                        break;
                    case R.id.navigation_health_state:
                        fragment = new HealthState();
                        mFragmentTransaction.add(R.id.container, fragment);
                        mFragmentTransaction.commit();
                    default:
                        break;
                }
                return true;
            }
        });

        mBottomNavigation.setSelectedItemId(R.id.navigation_user_profile);
    }


}
