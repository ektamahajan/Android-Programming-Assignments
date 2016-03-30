package com.example.newuser.lab8_asynctask_thread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Externalizable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by newuser on 3/22/16.
 */
public class MovieDataJson {

    static List<Map<String,?>> moviesList;
    static final String PHP_Server = "http://www.emahajan.com/";
    public void downloadMovieDataJson(String url)
    {
        JSONArray arr = null;
        moviesList = new ArrayList<Map<String,?>>();
        HashMap movie;

        String data = MyUtility.downloadJSONusingHTTPGetRequest(url);

//        BufferedReader reader = null;
//        reader = new BufferedReader(new InputStreamReader(url.openStream()));
//        StringBuffer buffer = new StringBuffer();
//        int read;
//        char[] chars = new char[1024];
//        while ((read = reader.read(chars)) != -1)
//        {
//            buffer.append(chars, 0, read);
//            //movie.moviesList.add();
//        }
        try {
             arr = new JSONArray(data);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(arr != null)
        {


        for (int i= 0; i < arr.length();i++)
        {
            try{
                JSONObject obj = arr.getJSONObject(i);
                movie = new HashMap();


                String id =  (String)(obj.get("id"));
                String name =  (String)(obj.get("name"));
                String description =  (String)(obj.get("description"));
                String stars =  (String)(obj.get("stars"));
                String length =  (String)(obj.get("length"));
                String image =  (String)(obj.get("image"));
                String year =  (String)(obj.get("year"));
                double rating =  obj.getDouble("rating");
                String director =  (String)(obj.get("director"));
                String url1 =  (String)(obj.get("url"));

                movie.put("id", id);
                movie.put("name", name);
                movie.put("description", description);
                movie.put("stars", stars);
                movie.put("length",length);
                movie.put("image",image);
                movie.put("year",year);
                movie.put("rating",rating);
                movie.put("director",director);
                movie.put("url",url1);

                moviesList.add(movie);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        }

    }
}
