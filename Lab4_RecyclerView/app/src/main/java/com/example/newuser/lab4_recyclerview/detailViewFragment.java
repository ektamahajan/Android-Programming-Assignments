package com.example.newuser.lab4_recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by newuser on 2/18/16.
 */
public class detailViewFragment extends Fragment {

    private static final String ARG_MOVIE ="movie";
    private HashMap<String,?> movie;

    public static detailViewFragment newInstance( HashMap<String,?> movie) {

        Bundle args = new Bundle();

        args.putInt("image", (Integer) movie.get("image"));
        args.putString("name", (String) movie.get("name"));
        args.putString("year", (String) movie.get("year"));
        args.putString("description", (String) movie.get("description"));
        args.putString("stars", (String) movie.get("stars"));
        double r = (Double) movie.get("rating");
        float f = (float)r / 2.0f;
        args.putFloat("rating",f);


        args.putSerializable(ARG_MOVIE, movie);
        detailViewFragment fragment = new detailViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null)
        {
            movie = ( HashMap<String,?>) getArguments().getSerializable(ARG_MOVIE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.activity_detailview, container, false);
        Bundle args = getArguments();

        ImageView imgMovie= (ImageView)rootView.findViewById(R.id.img_movie);
        imgMovie.setImageResource(args.getInt("image"));

        TextView title = (TextView) rootView.findViewById(R.id.title);
        title.setText(args.getString("name"));

        TextView year = (TextView) rootView.findViewById(R.id.year);
        year.setText(args.getString("year"));

        TextView description = (TextView) rootView.findViewById(R.id.description);
        description.setText(args.getString("description"));

        TextView stars = (TextView) rootView.findViewById(R.id.stars);
        stars.setText(args.getString("stars"));

        RatingBar rate = (RatingBar)rootView.findViewById(R.id.rating);
        rate.setRating(args.getFloat("rating"));

        return rootView;
    }

    public detailViewFragment()
    {

    }
}
