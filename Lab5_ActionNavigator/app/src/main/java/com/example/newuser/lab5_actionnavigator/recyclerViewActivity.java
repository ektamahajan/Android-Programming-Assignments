package com.example.newuser.lab5_actionnavigator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.HashMap;

/**
 * Created by newuser on 2/17/16.
 */
public class recyclerViewActivity extends AppCompatActivity implements recyclerViewFragment.OnListItemSelectedListener{

    Fragment mContent;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerviewact);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState != null)
        {
            if (getSupportFragmentManager().getFragment(savedInstanceState, "mContent") != null) {
                mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
            }
            else
            {
                mContent = recyclerViewFragment.newInstance();
            }
        }
        else
        {
            mContent = recyclerViewFragment.newInstance();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new recyclerViewFragment())
                .commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mContent.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "mContent", mContent);
        }
    }

    public void OnListItemSelected(int position, HashMap<String,?> movie) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, detailViewFragment.newInstance(movie))
                    .addToBackStack(null)
                    .commit();

    }

}