package com.example.newuser.lab5_actionnavigator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;

/**
 * Created by newuser on 2/25/16.
 */
public class task3_Fragment extends Fragment {

    MovieData movieData;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyRecyclerViewAdapter mRecyclerViewAdapter;
    //OnListItemSelectedListener mListener;


    public static task3_Fragment newInstance() {

        Bundle args = new Bundle();
        task3_Fragment fragment = new task3_Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    public task3_Fragment()
    {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        movieData = new MovieData();

        final View rootView = inflater.inflate(R.layout.activity_task3_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieData.getMoviesList());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        adapterAnimation();
        itemAnimation();
        return rootView;
    }

//        try
//        {
//            mListener = (OnListItemSelectedListener) getContext();
//
//        }catch (ClassCastException e)
//        {
//            throw new ClassCastException("The hosting activity of the Fragment"+
//                    "forget to implement onFragmentInteraction Listener");
//        }

//
//        mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(View v, int position) {
//                HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getItem(position);
//                mListener.OnListItemSelected(position, movie);
//            }
//
//
//            @Override
//            public void onItemLongClick(View v, int position) {
//                HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getItem(position);
//                movieData.moviesList.add(position, (Map<String, ?>) movie.clone());
//                mRecyclerViewAdapter.notifyItemInserted(position);
//
//            }
//
//
//        });






    private void itemAnimation()
    {

        //  FlipInRightYAnimator animator = new FlipInRightYAnimator();
        ScaleInTopAnimator animator = new ScaleInTopAnimator();
        animator.setInterpolator(new OvershootInterpolator());

        animator.setAddDuration(300);
        animator.setRemoveDuration(300);

        mRecyclerView.setItemAnimator(animator);
    }

    private void adapterAnimation()
    {
        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(mRecyclerViewAdapter);
        SlideInRightAnimationAdapter scaleAdapter = new SlideInRightAnimationAdapter(alphaAdapter);
        mRecyclerView.setAdapter(scaleAdapter);
    }

//
//    public interface OnListItemSelectedListener
//    {
//        public void OnListItemSelected(int position, HashMap<String,?> movie);
//
//    }


}



