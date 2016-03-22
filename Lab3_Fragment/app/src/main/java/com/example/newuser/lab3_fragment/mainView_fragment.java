package com.example.newuser.lab3_fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashMap;


/**
 * Created by newuser on 2/10/16.
 */
public class mainView_fragment extends Fragment {

    MovieData movieData;

    public static mainView_fragment newInstance() {

        Bundle args = new Bundle();
        mainView_fragment fragment = new mainView_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = null;
        rootView= inflater.inflate(R.layout.activity_masterdetailmain, container, false);
        movieData = new MovieData();

        final Button frozen = (Button) rootView.findViewById(R.id.button_frozen);
        final Button lionking = (Button) rootView.findViewById(R.id.button_lionking);
        final Button transformer = (Button) rootView.findViewById(R.id.button_transformer);
        final Button starwar = (Button) rootView.findViewById(R.id.button_starwar);
        final Button avatar = (Button) rootView.findViewById(R.id.button_avatar);


        final OnListItemSelectedListener mListener;
        try
        {
            mListener = (OnListItemSelectedListener) getContext();

        }catch (ClassCastException e)
        {
            throw new ClassCastException("The hosting activity of the Fragment"+
                    "forget to implement onFragmentInteraction Listener");
        }

        frozen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View rootView)
            {
                HashMap<String,?> movie = (HashMap<String,?>) movieData.getItem(18);
                mListener.onListItemSelected(0, movie);
            }


        });

        lionking.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View rootView)
            {
                HashMap<String,?> movie = (HashMap<String,?>) movieData.getItem(11);
                mListener.onListItemSelected(1, movie);

            }


        });

        transformer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View rootView)
            {
                HashMap<String,?> movie = (HashMap<String,?>) movieData.getItem(17);
                mListener.onListItemSelected(2, movie);

            }


        });

        starwar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View rootView) {
                HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getItem(4);
                mListener.onListItemSelected(3, movie);

            }


        });

        avatar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View rootView)
            {
                HashMap<String,?> movie = (HashMap<String,?>) movieData.getItem(0);
                mListener.onListItemSelected(4, movie);

            }


        });

        return rootView;
    }


    public interface OnListItemSelectedListener
    {
        public void onListItemSelected(int position, HashMap<String,?>movie);
    }

    public mainView_fragment()
    {

    }

}
