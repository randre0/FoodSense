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
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mBottomNavigation = findViewById(R.id.navigationView);

        mBottomNavigation.setSelectedItemId(R.id.navigation_user_profile);
        mCurrentFragment = new UserProfileFragment();
        mFragmentTransaction.add(R.id.container, mCurrentFragment);
        mFragmentTransaction.commit();

        mBottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.remove(mCurrentFragment).commit();
                switch(menuItem.getItemId()) {
                    case R.id.navigation_food_journal:
                        break;
                    case R.id.navigation_log_food:
                        mCurrentFragment = new LogFoodFragment();
                        break;
                    case R.id.navigation_user_profile:
                        mCurrentFragment = new UserProfileFragment();
                        break;
                    case R.id.navigation_health_state:
                        mCurrentFragment = new HealthState();
                        break;
                    default:
                        break;
                }
                mFragmentTransaction.add(R.id.container, mCurrentFragment);
                return true;
            }
        });
    }

}
