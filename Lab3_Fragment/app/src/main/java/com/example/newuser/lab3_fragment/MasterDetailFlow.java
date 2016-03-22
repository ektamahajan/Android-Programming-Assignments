package com.example.newuser.lab3_fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.HashMap;


/**
 * Created by newuser on 2/9/16.
 */
public class MasterDetailFlow extends AppCompatActivity implements mainView_fragment.OnListItemSelectedListener {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_masterdetail);

         if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container,mainView_fragment.newInstance())
                    .commit();
        }

        if (findViewById(R.id.detail_container) != null)
        {
                mTwoPane = true;
        }
    }

    public void onListItemSelected(int position, HashMap<String,?> movie) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, viewPageFragment.newInstance(movie))
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, viewPageFragment.newInstance(movie))
                    .addToBackStack(null)
                    .commit();
        }
    }

}
