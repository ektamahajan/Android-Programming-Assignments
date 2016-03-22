package com.example.newuser.lab2_layout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


/**
 * Created by newuser on 1/30/16.
 */


public class ControlEvents extends AppCompatActivity {

    private GestureDetectorCompat detector;
    MovieData movieData = null;
     TextView textView;
     ImageView imageViewMovie;
     SeekBar seekBar;
    int i = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void setImageView(int i) {
        movieData = new MovieData();
        final HashMap movie2 = movieData.getItem(i);
        int imageId = (Integer) movie2.get("image");
        imageViewMovie.setImageResource(imageId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controlevents);
        textView = (TextView) findViewById(R.id.textView5);
        imageViewMovie = (ImageView) findViewById(R.id.imageViewMovie);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        setImageView(0);

        imageViewMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Image CLicked");
                textView.setBackgroundColor(Color.WHITE);

                Toast.makeText(ControlEvents.this, "Toast message.", Toast.LENGTH_LONG).show();
                Snackbar.make(findViewById(R.id.imageViewMovie), "Snackbar message.", Snackbar.LENGTH_LONG).show();
            }
        });


        imageViewMovie.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view1) {
                textView.setText("Image Long CLicked");
                textView.setBackgroundColor(Color.GRAY);
                seekBar.setProgress(50);
                return true;
            }

        });

        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textView.setText(Integer.toString(progress));
                ViewGroup.LayoutParams dim = imageViewMovie.getLayoutParams();
                dim.width = progress * 5;
                dim.height = progress * 5;
                imageViewMovie.setLayoutParams(dim);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        detector = new GestureDetectorCompat(this,new GestureDetector.SimpleOnGestureListener()
        {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
                public boolean onDown(MotionEvent event)
            {
                return true;
            }

            @Override
                 public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

                boolean result = false;

                float diffX = event2.getX() - event1.getX();
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {


                    if (diffX > 0)
                    {
                        if (i > 0)
                        {
                            i = i - 1;
                            setImageView(i);
                        }
                    }

                    else {
                        if (i < 29)
                        {
                            i = i + 1;
                            setImageView(i);
                        }

                    }
                }
                return true;
            }
        });



    }


}