package com.example.newuser.lab4_recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;



/**
 * Created by newuser on 2/17/16.
 */
public class recyclerViewFragment extends Fragment {

    MovieData movieData;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyRecyclerViewAdapter mRecyclerViewAdapter;
    OnListItemSelectedListener mListener;


    public static recyclerViewFragment newInstance() {

        Bundle args = new Bundle();
        recyclerViewFragment fragment = new recyclerViewFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public recyclerViewFragment()
    {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        movieData = new MovieData();

        final View rootView= inflater.inflate(R.layout.activity_recyclerviewfragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new  LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),movieData.getMoviesList());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);


        try
        {
            mListener = (OnListItemSelectedListener) getContext();

        }catch (ClassCastException e)
        {
            throw new ClassCastException("The hosting activity of the Fragment"+
                    "forget to implement onFragmentInteraction Listener");
        }


        mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getItem(position);
                mListener.OnListItemSelected(position, movie);
            }


            @Override
            public void onItemLongClick(View v, int position) {
                HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getItem(position);
                movieData.moviesList.add(position, (Map<String, ?>) movie.clone());
                mRecyclerViewAdapter.notifyItemInserted(position);

            }


        });
        adapterAnimation();
        itemAnimation();

        Button clearAll = (Button) rootView.findViewById(R.id.clearall);
        clearAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for (int i = 0; i < movieData.getSize(); i++) {
                    HashMap<String, Boolean> item = (HashMap<String, Boolean>) movieData.getItem(i);
                    item.put("selection", false);
                }
                mRecyclerViewAdapter.notifyDataSetChanged();
            }


        });

        Button selectAll = (Button) rootView.findViewById(R.id.selectall);
        selectAll.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                for(int i=0; i< movieData.getSize();i++) {
                    HashMap<String,Boolean> item = (HashMap<String,Boolean>) movieData.getItem(i);
                    item.put("selection", true);
                }
                mRecyclerViewAdapter.notifyDataSetChanged();
            }
        });


        Button delete = (Button) rootView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //int p = movieData.getSize();
                int p = movieData.getSize();
                for(int i = 0; i< p; i++)
                {
                    HashMap<String,Boolean> item = (HashMap<String,Boolean>) movieData.getItem(i);
                    boolean b = item.get("selection");
                  //  Toast.makeText(getActivity(), Boolean.toString(b), Toast.LENGTH_SHORT).show();
                    if (b == true)
                    {
                       // movieData.moviesList.remove(i);
                        movieData.getMoviesList().remove(i);
                        mRecyclerViewAdapter.notifyItemRemoved(i);
                        i--;
                        p--;
                    }

                }

            }

        });

        return rootView;

    }

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


    public interface OnListItemSelectedListener
    {
        public void OnListItemSelected(int position, HashMap<String,?> movie);

    }



//
//    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//        try
//        {
//            mListener = (OnListItemSelectedListener) getActivity();
//        }catch (ClassCastException e)
//        {
//            throw new ClassCastException(getActivity().toString() + "must implement onFragmentInteractionListener");
//        }
//    }
//


}
















