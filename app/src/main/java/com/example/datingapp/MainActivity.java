package com.example.datingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.datingapp.adapter.viewPagerAdapter;
import com.example.datingapp.fragment.SignInFragment;
import com.example.datingapp.fragment.SignUpFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabs;
    private viewPagerAdapter adapter;
    private FirebaseAuth mAuth;
  //  private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mViewPager = findViewById(R.id.viewPager);
        mTabs = findViewById(R.id.tabLayout);
        mAuth = FirebaseAuth.getInstance();

        adapter = new viewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignInFragment(), "Sign In"); //add fragment Sign in
        adapter.addFragment(new SignUpFragment(), "Sign Up"); //add fragment Sign Up

        mViewPager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewPager);

        //saat menekan sign in atau sign up, backgroundnya akan berubah
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    findViewById(R.id.layout_main).setBackground(getDrawable(R.drawable.layout_bg)); //saat menekan sign in, muncul layout_bg
                    getWindow().setStatusBarColor(getColor(R.color.colorPrimary));
                }
                if(tab.getPosition() == 1) {
                    findViewById(R.id.layout_main).setBackground(getDrawable(R.drawable.signup_bg)); //saat menekan sign up, muncul signup_bg
                    getWindow().setStatusBarColor(getColor(R.color.colorGolden));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class); //jika user telah login, maka langsung menuju ke halaman home
            startActivity(intent); //jalankan activity
            finish();
        }
    }
}