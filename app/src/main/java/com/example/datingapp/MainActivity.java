package com.example.datingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.datingapp.adapter.viewPagerAdapter;
import com.example.datingapp.fragment.SignInFragment;
import com.example.datingapp.fragment.SignUpFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabs;
    private viewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.viewPager);
        mTabs = findViewById(R.id.tabLayout);

        adapter = new viewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignInFragment(), "Sign In"); //add fragment Sign in
        adapter.addFragment(new SignUpFragment(), "Sign Up"); //add fragment Sign Up
    }
}