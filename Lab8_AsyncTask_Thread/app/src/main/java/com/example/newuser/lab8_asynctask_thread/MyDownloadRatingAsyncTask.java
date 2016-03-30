package com.example.newuser.lab8_asynctask_thread;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by newuser on 3/23/16.
 */
public class MyDownloadRatingAsyncTask extends AsyncTask<String,Void,List<Map<String,?>>> {

    //private final WeakReference<recyclerViewFragment.OnListItemSelectedListener> weakListener;
    MovieData movieData;
    private final WeakReference<MyRecyclerViewAdapter> adapterReference;
    public MyDownloadRatingAsyncTask(MovieData movieData, MyRecyclerViewAdapter adapter)
    {
        adapterReference = new WeakReference<MyRecyclerViewAdapter>(adapter);
        this.movieData = movieData;
    }

    @Override
    protected List<Map<String,?>> doInBackground(String... urls) {
        HashMap movie;
        List<Map<String,?>> moviesList;
        moviesList = new ArrayList<Map<String,?>>();
        JSONArray arr = null;

        //for (String url : urls) {
        try{
            String data = MyUtility.downloadJSONusingHTTPGetRequest(urls[0]);
            System.out.println("ihbuhbuhbuhbhubjhbj      ------>    " +data);
            arr = new JSONArray(data);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                movie = new HashMap();


                String id = (String) (obj.get("id"));
                String name = (String) (obj.get("name"));
                String description = (String) (obj.get("description"));
                String stars = (String) (obj.get("stars"));
                String length = (String) (obj.get("length"));
                String image = (String) (obj.get("image"));
                String year = (String) (obj.get("year").toString());
                double rating = obj.getDouble("rating");
                String director = (String) (obj.get("director"));
                String url1 = (String) (obj.get("url"));

                movie.put("id", id);
                movie.put("name", name);
                movie.put("description", description);
                movie.put("stars", stars);
                movie.put("length", length);
                movie.put("image", image);
                movie.put("year", year);
                movie.put("rating", rating);
                movie.put("director", director);
                movie.put("url", url1);
                moviesList.add(movie);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //}

        return  moviesList;
    }

    @Override
    protected void onPostExecute(List<Map<String,?>> movie) {
        movieData.moviesList.clear();
        for(int i = 0; i < movie.size(); i++)
        {
            movieData.moviesList.add(movie.get(i));

        }

        if (adapterReference != null) {
            final MyRecyclerViewAdapter adapter = adapterReference.get();
            if (adapter != null)
                adapter.notifyDataSetChanged();
        }


    }
}
