package com.example.newuser.lab8_asynctask_thread;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by newuser on 2/17/16.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>
{

    private List<Map<String,?>> mDataSet;
    private Context context;
    OnItemClickListener mItemClickListener;

    public MyRecyclerViewAdapter(Context myContext,List<Map<String,?>> myDataSet)
    {
        context = myContext;
        mDataSet = myDataSet;
    }

    public interface OnItemClickListener
    {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
        public void onOverflowMenuClick(View view, int position);
    }



    public void setOnItemClickListener(final OnItemClickListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemViewType(int position)
    {
        Map<String,?>movie = mDataSet.get(position);
        double r = (Double) movie.get("rating");
        float f = (float)r;
        if (f>=8.0)
        {
            return 0;
        }
        else
        {
            return 1;
        }

    }


    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v;

        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cardview,parent,false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Map<String,?> movie = mDataSet.get(position);
        holder.bindMovieData(movie);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public RatingBar vRating;
        public ImageView vMenu;

        public ViewHolder(View v) {
            super(v);
            vIcon = (ImageView) v.findViewById(R.id.icon);
            vTitle = (TextView) v.findViewById(R.id.title);
            vDescription = (TextView) v.findViewById(R.id.description);
            vRating = (RatingBar) v.findViewById(R.id.rating);
            vMenu = (ImageView) v.findViewById(R.id.overflow_button);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }
                }


            });

            v.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemLongClick(v, getPosition());
                    }
                    return true;
                }

            });

            if (vMenu != null) {
                vMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickListener != null) {
                            mItemClickListener.onOverflowMenuClick(v, getPosition());
                        }
                    }
                });

            }
        }

        public void bindMovieData(final Map<String,?>movie)
        {
            vTitle.setText((String) movie.get("name"));
            vDescription.setText((String) movie.get("description"));
            //vIcon.setImageResource((Integer) movie.get("image"));

            String url = (String)movie.get("url");
            MyDownloadImageAsyncTask task = new MyDownloadImageAsyncTask(vIcon);
            task.execute(url);

            double r = (Double) movie.get("rating");
            float f = (float)r / 2.0f;
            vRating.setRating(f);
        }
    }
}