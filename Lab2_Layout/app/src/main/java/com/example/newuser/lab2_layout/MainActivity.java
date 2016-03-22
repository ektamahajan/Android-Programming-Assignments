package com.example.newuser.lab2_layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.tasks_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        switch (id){

            case R.id.action_CoverPage:
                Intent intent1 = new Intent (this, MainActivity.class);
                startActivity(intent1);
                return true;

            case R.id.action_LinearLayout:
                Intent intent2 = new Intent (this, LinearLayout.class);
                startActivity(intent2);
                return true;

            case R.id.action_RelativeLayout:
                Intent intent3 = new Intent (this, RelativeLayout.class);
                startActivity(intent3);
                return true;

            case R.id.action_NumberPad:
                Intent intent4 = new Intent (this, NumberPad.class);
                startActivity(intent4);
                return true;

            case R.id.action_ControlEvents:
                Intent intent5 = new Intent (this, ControlEvents.class);
                startActivity(intent5);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
