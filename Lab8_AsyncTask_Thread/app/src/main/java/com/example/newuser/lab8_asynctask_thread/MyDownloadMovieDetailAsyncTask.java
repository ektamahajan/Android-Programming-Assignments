package com.example.newuser.lab8_asynctask_thread;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by newuser on 3/23/16.
 */
public class MyDownloadMovieDetailAsyncTask extends AsyncTask<String,Void,HashMap> {

     WeakReference<recyclerViewFragment.OnListItemSelectedListener> weakListener;

    public MyDownloadMovieDetailAsyncTask(recyclerViewFragment.OnListItemSelectedListener listener)
    {
        weakListener =new WeakReference<recyclerViewFragment.OnListItemSelectedListener>(listener);
    }

    @Override
    protected HashMap doInBackground(String... urls) {
      HashMap movie = new HashMap();

        JSONArray arr = null;

            //for (String url : urls) {
                try{
                    String jmovie1 = MyUtility.downloadJSONusingHTTPGetRequest(urls[0]);

                    String jmovie = MyUtility.downloadJSONusingHTTPGetRequest(urls[0]);
                    System.out.println("ihbuhbuhbuhbhubjhbj      ------>    " +jmovie);
                    arr = new JSONArray(jmovie);

                    if(arr != null) {
                    JSONObject obj = arr.getJSONObject(0);
                    movie = new HashMap();


                        String id = (String) (obj.get("id"));
                        String name = (String) (obj.get("name"));
                        String description = (String) (obj.get("description"));
                        String stars = (String) (obj.get("stars"));
                        String length = (String) (obj.get("length"));
                        String image = (String) (obj.get("image"));
                        String year = (obj.get("year").toString());
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
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            //}

        return  movie;
    }

    @Override
    protected void onPostExecute(HashMap movie) {

        recyclerViewFragment.OnListItemSelectedListener mListener = weakListener.get();
        mListener.OnListItemSelected(movie);
    }
}
