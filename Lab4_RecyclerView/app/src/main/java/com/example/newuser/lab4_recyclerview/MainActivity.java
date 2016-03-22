package com.example.newuser.lab4_recyclerview;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements frontPageFragment.onButtonClickListener {

    Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            if (getSupportFragmentManager().getFragment(savedInstanceState, "mContent") != null) {
                mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
            } else {
                mContent = frontPageFragment.newInstance();
            }
        } else {
            mContent = frontPageFragment.newInstance();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.basicLayout, mContent)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mContent.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "mContent", mContent);
        }
    }

    @Override
    public void onButtonClick(View v) {

        mContent = aboutMeFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.basicLayout, mContent)
                .addToBackStack(null)
                .commit();


    }

}
