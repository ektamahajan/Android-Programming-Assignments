package com.example.newuser.lab8_asynctask_thread;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.PopupMenu;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

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

//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//       setHasOptionsMenu(true);
//        setRetainInstance(true);
//        movieData = new MovieDataJson();
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        movieData = new MovieData();
        movieData.moviesList.clear();
        final View rootView= inflater.inflate(R.layout.activity_recyclerviewfragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),movieData.moviesList);
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
        MyDownloadJsonAsyncTask downloadJson = new MyDownloadJsonAsyncTask(mRecyclerViewAdapter, movieData);
        String url = MovieDataJson.PHP_Server + "movies/";
        downloadJson.execute(url);

        mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                HashMap<String, ?> movie = (HashMap<String, ?>) movieData.moviesList.get(position);
                String id = (String) movieData.getItem(position).get("id");

                String url = MovieDataJson.PHP_Server + "movies/id/" +id;
                System.out.println("Url of the id " + url);
                //if (url != null)
                //{
                    MyDownloadMovieDetailAsyncTask downloadDetail = new MyDownloadMovieDetailAsyncTask(mListener);
                    downloadDetail.execute(url);

                //}
              //  mListener.OnListItemSelected(position, movie);
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
//                                HashMap<String, ?> movie = (HashMap<String, ?>) movieData.moviesList.get(position);
//                                movieData.moviesList.add(position+1, (Map<String, ?>) movie.clone());
//                                mRecyclerViewAdapter.notifyItemInserted(position+1);

                                final JSONObject json1;
                                HashMap val1 = (HashMap)movieData.getItem(position).clone();
                                val1.put("id", val1.get("id") + "_new");
                                if (val1 != null)
                                {
                                    json1 = new JSONObject(val1);

                                } else json1 = null;

                                Runnable runnable1 = new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println(json1.toString());
                                        String url = MovieDataJson.PHP_Server + "post1";
                                        MyUtility.sendHttPostRequest(url,json1);
                                        MyUtility.sendHttPostRequest(url,json1);
                                    }
                                };

                                new Thread(runnable1).start();

                                MovieDataJson.moviesList.add(position, val1);
                                return true;

                            case R.id.item_delete:

                                final JSONObject json;
                                Map<String,?> val = MovieDataJson.moviesList.get(position);

                                if (val != null)
                                {
                                    json = new JSONObject(val);

                                } else json = null;

                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println(json.toString());
                                        String url = MovieDataJson.PHP_Server + "post";
                                        MyUtility.sendHttPostRequest(url,json);
                                        MyUtility.sendHttPostRequest(url,json);
                                    }
                                };

                                new Thread(runnable).start();

                                MovieDataJson.moviesList.remove(position);

//                                movieData.moviesList.remove(position);
//                                mRecyclerViewAdapter.notifyItemRemoved(position);
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
        public void OnListItemSelected(HashMap<String, ?> movie);

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
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    int position = 0;

                    //for (int i = 0; i < movieData.moviesList.size(); i++) {
                      //  Map<String, ?> movie = movieData.moviesList.get(i);
                       // double rate = (double) movie.get("rating");
//                        //query.toLowerCase();
//                       // StringTokenizer st = new StringTokenizer(rate);
//
//                 //       String id = (String) movie.get("id");
                        String url;
                        url = MovieDataJson.PHP_Server + "movies/rating/" + query;
                        System.out.println("Url of the id" + url);
                        if (url != null) {
                            MyDownloadRatingAsyncTask downloadDetail = new MyDownloadRatingAsyncTask(movieData,mRecyclerViewAdapter);
                            downloadDetail.execute(url);

                        }
                  //  }

                    return true;

                }

                @Override
                public boolean onQueryTextChange(String query) {
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
                    final JSONObject json;
                    Map<String,?> val = MovieDataJson.moviesList.get(position);

                    if (val != null)
                    {
                        json = new JSONObject(val);

                    } else json = null;

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(json.toString());
                            String url = MovieDataJson.PHP_Server + "post";
                            MyUtility.sendHttPostRequest(url,json);
                            MyUtility.sendHttPostRequest(url,json);
                        }
                    };

                    new Thread(runnable).start();

                    MovieDataJson.moviesList.remove(position);



//                    final JSONObject[] object = new  JSONObject[1];
//                    if (item != null)
//                    {
//                        object[0] = new JSONObject();
//
//                    }
//                    else
//                    {
//                        object[0]=null;
//                        Runnable runnable = new Runnable() {
//                            @Override
//                            public void run() {
//                                String url = MovieDataJson.PHP_Server + "/post";
//                                Map<String,?> val = MovieDataJson.moviesList.get(position);
//                                JSONObject object = new JSONObject(val);
//                            }
//                        };
//                    }

                    //movieData.moviesList.remove(position);
                    //mRecyclerViewAdapter.notifyItemRemoved(position);
                    //mode.finish();
                    break;
                case R.id.item_duplicate:

                    final JSONObject json1;
                    Map<String,?> val1 = MovieDataJson.moviesList.get(position);

                    if (val1 != null)
                    {
                        json1 = new JSONObject(val1);

                    } else json1 = null;

                    Runnable runnable1 = new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(json1.toString());
                            String url = MovieDataJson.PHP_Server + "post1";
                            MyUtility.sendHttPostRequest(url,json1);
                            MyUtility.sendHttPostRequest(url,json1);
                        }
                    };

                    new Thread(runnable1).start();

                    MovieDataJson.moviesList.add(position, val1);
                   // return true;

//                    HashMap<String, ?> movie = (HashMap<String, ?>) movieData.moviesList.get(position);
//                    movieData.moviesList.add(position+1, (Map<String, ?>) movie.clone());
//                    mRecyclerViewAdapter.notifyItemInserted(position+1);
//                    mode.finish();
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
            HashMap hm = (HashMap)movieData.moviesList.get(position);
            mode.setTitle((String) hm.get("name"));
            return false;
        }
    }



}
















