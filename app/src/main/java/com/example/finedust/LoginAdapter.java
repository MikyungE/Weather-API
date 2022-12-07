package com.example.finedust;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class LoginAdapter  extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;
    public  LoginAdapter(FragmentManager fm , Context context , int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
        case 0 :
            Login login = new Login();
            return login;
        case 1 :
            Sigup sigup = new Sigup();
            return  sigup;

            default: return null;
        }
    }


    @Override
    public int getCount() {
        return totalTabs;
    }
}
