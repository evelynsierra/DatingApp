package com.example.datingapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> mTitleList =  new ArrayList<>();

    //membuat pergantian halaman fragment, sign in dan sign up
    public HomeViewPagerAdapter(@NonNull @org.jetbrains.annotations.NotNull FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        mTitleList.add(title);
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
    }

    @Override
    public int getCount() {

        return fragmentList.size();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position){

        return mTitleList.get(position);
    }
}
