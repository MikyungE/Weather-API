package com.example.finedust;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Login extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_page,container,false);
        Button button =  (Button) root.findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               Intent next = new Intent(v.getContext(),MainActivity.class);
               startActivity(next);
               // Intent를 사용해 다음 activity를 띄워줌.
            }
        });
        return root;
    }

    public View onCreate(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_page,container,false);
        return root;
    }
}
