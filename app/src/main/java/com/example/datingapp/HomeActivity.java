package com.example.datingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;

import com.example.datingapp.adapter.HomeViewPagerAdapter;
import com.example.datingapp.fragment.ChatFragment;
import com.example.datingapp.fragment.DiscoverFragment;
import com.example.datingapp.fragment.ProfileFragment;
import com.example.datingapp.fragment.SignInFragment;
import com.example.datingapp.fragment.SignUpFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mHomeTab;
    private HomeViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mViewPager = findViewById(R.id.home_view_pager);
        mHomeTab = findViewById(R.id.home_tab);
        adapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProfileFragment(), "Profile"); //add fragment Profile
        adapter.addFragment(new DiscoverFragment(), "Discover"); //add fragment Chat
        adapter.addFragment(new ChatFragment(), "Chat"); //add fragment Chat

        mViewPager.setAdapter(adapter);
        mHomeTab.setupWithViewPager(mViewPager);

        //set image
        mHomeTab.getTabAt(0).setIcon(R.drawable.profile);
        mHomeTab.getTabAt(1).setIcon(R.drawable.discover);
        mHomeTab.getTabAt(2).setIcon(R.drawable.chat);

        mHomeTab.selectTab(mHomeTab.getTabAt(1)); //ambil yang pertama yaitu discover

        mHomeTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}