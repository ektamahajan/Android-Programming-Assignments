package com.example.newuser.lab3_fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by newuser on 2/10/16.
 */
public class ViewPagerActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        MoviePagerAdapter moviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager(), new MovieData());
        viewPager.setAdapter(moviePagerAdapter);
        customizeViewPager();

          TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
           tabLayout.setupWithViewPager(viewPager);

    }

    private void customizeViewPager()
    {
               viewPager.setPageTransformer(false,new ViewPager.PageTransformer(){

            @Override
            public void transformPage(View page, float position) {

//                final float normalized_position = Math.abs(Math.abs(position)-1);
//                page.setAlpha(normalized_position);
//                page.setScaleX(normalized_position / 2 + 0.5f);
//                page.setScaleY(normalized_position / 2 + 0.5f);
                 page.setRotationY(position * -30);
            }
              });
    }


    static class MoviePagerAdapter extends FragmentStatePagerAdapter{

        MovieData movieData;

        public MoviePagerAdapter(FragmentManager fm, MovieData movieData) {
            super(fm);
            this.movieData = movieData;
        }

        @Override
        public Fragment getItem(int position) {

            return viewPageFragment.newInstance(movieData.getItem(position));
        }

        @Override
        public int getCount() {
            return movieData.getSize();
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            Locale l = Locale.getDefault();
            HashMap<String,?> movie = (HashMap<String,?>) movieData.getItem(position);
            String name = (String) movie.get("name");
            return name.toUpperCase(l);
        }
    }


}
