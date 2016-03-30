package com.example.newuser.lab8_asynctask_thread;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

/**
 * Created by newuser on 3/22/16.
 */
public class MyDownloadJsonAsyncTask extends AsyncTask<String,Void,MovieDataJson> {
    MovieData movieData;
    //MovieDataJson threadMovieData;
    private final WeakReference<MyRecyclerViewAdapter> adapterReference;

    public MyDownloadJsonAsyncTask(MyRecyclerViewAdapter adapter,MovieData movieData)
    {
        adapterReference = new WeakReference<MyRecyclerViewAdapter>(adapter);
        this.movieData = movieData;
        //threadMovieData = new MovieDataJson();
    }

    @Override
    protected MovieDataJson doInBackground(String... urls) {

        MovieDataJson threadMovieData = new MovieDataJson();
        for (String url : urls)
        {
            threadMovieData.downloadMovieDataJson(url);
        }
        return threadMovieData;
    }

    @Override
    protected void onPostExecute(MovieDataJson movieDataJson) {

        movieData.moviesList.clear();

        if (movieDataJson.moviesList.size() != 0) {
            for (int i = 0; i < movieDataJson.moviesList.size(); i++) {
                movieData.moviesList.add(movieDataJson.moviesList.get(i));
            }

            if (adapterReference != null) {
                final MyRecyclerViewAdapter adapter = adapterReference.get();
                if (adapter != null)
                    adapter.notifyDataSetChanged();
            }
        }
    }
}
