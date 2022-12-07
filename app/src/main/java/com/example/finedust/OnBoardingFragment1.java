package com.example.finedust;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OnBoardingFragment1  extends Fragment {

    public View onCreate(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
       ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_page,container,false);
       return root;
    }
}
