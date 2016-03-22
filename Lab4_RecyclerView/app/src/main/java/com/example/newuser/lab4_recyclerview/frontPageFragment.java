package com.example.newuser.lab4_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by newuser on 2/16/16.
 */
public class frontPageFragment extends Fragment {

    Button b;
    onButtonClickListener mListener;


    public static frontPageFragment newInstance() {

        Bundle args = new Bundle();
        frontPageFragment fragment = new frontPageFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public frontPageFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
            /*final onButtonClickListener mListener;
                try {
                    mListener = (onButtonClickListener) getActivity();
                }catch (ClassCastException e)
                {
                    throw new ClassCastException("The hosting activity of the Fragment"+
                            "forget to implement onFragmentInteraction Listener");
                }*/
        View v = inflater.inflate(R.layout.activity_frontpagefragment, container, false);
        b = (Button) v.findViewById(R.id.aboutMe);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mListener.onButtonClick(v);
            }

        });

        b = (Button) v.findViewById(R.id.recyclerPage);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent (getActivity(),recyclerViewActivity.class);
                startActivity(intent1);
            }

        });

        return v;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            mListener = (onButtonClickListener) context;
        }catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement onFragmentInteractionListener");
        }
    }

    public interface onButtonClickListener
    {
        public void onButtonClick(View v);
    }



}
