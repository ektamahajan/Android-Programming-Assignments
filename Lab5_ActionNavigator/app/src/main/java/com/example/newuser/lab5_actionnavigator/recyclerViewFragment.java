package com.example.newuser.lab5_actionnavigator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.support.v7.widget.SearchView;
import android.widget.PopupMenu;
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
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),movieData.getMoviesList());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        setHasOptionsMenu(true);

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
               getActivity().startActionMode(new ActionBarCallBack(position));

            }

            @Override
            public void onOverflowMenuClick(View v, final int position)
            {
                PopupMenu popup = new PopupMenu(getActivity(), v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        switch (item.getItemId())
                        {
                            case R.id.item_duplicate:
                                HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getItem(position);
                                movieData.moviesList.add(position+1, (Map<String, ?>) movie.clone());
                                mRecyclerViewAdapter.notifyItemInserted(position+1);
                                return true;
                            case R.id.item_delete:
                                movieData.removeItem(position);
                                mRecyclerViewAdapter.notifyItemRemoved(position);
                                return true;
                            default:
                                return true;
                        }
                    }
                });

                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.contextual_or_pop_menu,popup.getMenu());
                popup.show();
            }


        });
        adapterAnimation();
        itemAnimation();


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
        public void OnListItemSelected(int position, HashMap<String, ?> movie);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater)
    {

        if (menu.findItem(R.id.action_search)==null)
        {
            inflater.inflate(R.menu.toolbar_top_menu,menu);
        }

        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if(search!=null)
        {
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

                @Override
            public boolean onQueryTextSubmit(String query)
                {
                    int position = movieData.findFirst(query);
                    if (position>=0)
                        mRecyclerView.scrollToPosition(position);
                    return true;
                }

                @Override
            public boolean onQueryTextChange(String query)
                {
                 return true;

                }
            });
        }
        super.onCreateOptionsMenu(menu,inflater);

    }



    class ActionBarCallBack implements ActionMode.Callback
    {
        int position;

        public ActionBarCallBack (int position)
        {
            this.position = position;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item)
        {
            int id = item.getItemId();

            switch (id)
            {
                case R.id.item_delete:
                    movieData.removeItem(position);
                    mRecyclerViewAdapter.notifyItemRemoved(position);
                    mode.finish();
                    break;
                case R.id.item_duplicate:
                    HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getItem(position);
                    movieData.moviesList.add(position+1, (Map<String, ?>) movie.clone());
                    mRecyclerViewAdapter.notifyItemInserted(position+1);
                    mode.finish();
                    break;
                default:
                    break;

            }
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu)
        {
            mode.getMenuInflater().inflate(R.menu.contextual_or_pop_menu,menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode)
        {

        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode,Menu menu)
        {
            HashMap hm = movieData.getItem(position);
            mode.setTitle((String) hm.get("name"));
            return false;
        }
    }



}
















