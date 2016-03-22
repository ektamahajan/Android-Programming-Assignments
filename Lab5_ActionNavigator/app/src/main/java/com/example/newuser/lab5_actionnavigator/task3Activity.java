package com.example.newuser.lab5_actionnavigator;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

public class task3Activity extends AppCompatActivity {

  //  Fragment mContent;
     Toolbar mToolBar;
     Toolbar mtoolBar2;
   // private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);
        mToolBar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(mToolBar);
     //   getSupportActionBar();

        mtoolBar2 = (Toolbar) findViewById(R.id.toolbar_bottom);
        mtoolBar2.inflateMenu(R.menu.toolbar_bottom_menu);
      //  setSupportActionBar(mtoolBar2);
      //  getSupportActionBar();
        setupToolbarItemSelected();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.task3_container, new task3_Fragment())
                .commit();


//
//        mActionBar = getSupportActionBar();
//        mActionBar.setDisplayHomeAsUpEnabled(true);
//        mActionBar.setLogo(R.drawable.about_me_black);


        //  setSupportActionBar(mtoolBar2);



//        if (savedInstanceState != null) {
//            if (getSupportFragmentManager().getFragment(savedInstanceState, "mContent") != null) {
//                mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
//            } else {
//                mContent = task3_Fragment.newInstance();
//            }
//        } else {
//            mContent = task3_Fragment.newInstance();
//        }


    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (mContent.isAdded()) {
//            getSupportFragmentManager().putFragment(outState, "mContent", mContent);
//        }
//    }

//    public void OnListItemSelected(int position, HashMap<String, ?> movie) {
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.main_container, detailViewFragment.newInstance(movie))
//                .addToBackStack(null)
//                .commit();
//
//    }


    void setupToolbarItemSelected() {

        mtoolBar2.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.msg:
                        Toast.makeText(getApplicationContext(), "Hello, I am message!", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container1, aboutMeFragment.newInstance(0))
                                .addToBackStack(null)
                                .commit();
                        return true;

                    default:
                        break;
                }

                return false;
            }


        });


        mtoolBar2.setNavigationIcon(R.drawable.about_me_black);
        mtoolBar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtoolBar2.setVisibility(View.GONE);
            }
        });


        mToolBar.setNavigationIcon(R.drawable.about_me_black);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtoolBar2.setVisibility(View.VISIBLE);
            }
        });

    }
}





