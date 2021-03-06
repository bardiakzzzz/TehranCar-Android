package com.myapps.tc_android.view.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.myapps.tc_android.R;
import com.myapps.tc_android.view.fragments.HomeFragment;
import com.myapps.tc_android.view.fragments.ProfileFragment;
import com.myapps.tc_android.view.fragments.RentFragment;
import com.myapps.tc_android.view.fragments.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.frame_layout_container)
    FrameLayout frameLayoutContainer;
    @BindView(R.id.bottom_navigation_menu_home_home)
    BottomNavigationView bottomNavigationMenuHomeHome;
    private Fragment homeFragment,searchFragment, rentFragment, profileFragment, active;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        initFragmnets();
        bottomNavigationMenuHomeHome.setOnNavigationItemSelectedListener(this);
        bottomNavigationMenuHomeHome.setSelectedItemId(R.id.navigation_home);
    }

    private void initFragmnets() {
        fm = getSupportFragmentManager();
        homeFragment = HomeFragment.newInstance();
        rentFragment = RentFragment.newInstance();
        searchFragment = SearchFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();
        active = homeFragment;
        fm.beginTransaction().add(R.id.frame_layout_container, profileFragment, "4").hide(profileFragment).commit();
        fm.beginTransaction().add(R.id.frame_layout_container, rentFragment, "3").hide(rentFragment).commit();
        fm.beginTransaction().add(R.id.frame_layout_container, searchFragment, "2").hide(searchFragment).commit();
        fm.beginTransaction().add(R.id.frame_layout_container, homeFragment, "1").commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fm.beginTransaction().hide(active).show(homeFragment).commit();
                active = homeFragment;
                return true;
            case R.id.navigation_rent:
                fm.beginTransaction().hide(active).show(rentFragment).commit();
                active = rentFragment;
                return true;
            case R.id.navigation_search:
                fm.beginTransaction().hide(active).show(searchFragment).commit();
                active = searchFragment;
                return true;
            case R.id.navigation_profile:
                fm.beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                return true;
        }
        return false;
    }
}
