package com.example.newuser.lab4_recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by newuser on 2/16/16.
 */
public class aboutMeFragment extends Fragment {

    public static aboutMeFragment newInstance() {

        Bundle args = new Bundle();
        aboutMeFragment fragment = new aboutMeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.activity_aboutme, container, false);

    }

    public aboutMeFragment()
    {

    }
}
