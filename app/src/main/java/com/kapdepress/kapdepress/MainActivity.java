package com.kapdepress.kapdepress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sh = getSharedPreferences("kapdepress", MODE_PRIVATE);

                String loginStatus = sh.getString("loginstatus", "");

               if(loginStatus.equals("acitve"))
               {
                   Intent i=new Intent(getApplicationContext(),
                           DashboardActivity.class);
                   //Intent is used to switch from one activity to another.

                   startActivity(i);
                   //invoke the SecondActivity.

                   finish();
                   //the current activity will get finished.
               }
               else if(loginStatus.isEmpty())
               {
                   Intent i=new Intent(getApplicationContext(),
                           LoginActivity.class);
                   //Intent is used to switch from one activity to another.

                   startActivity(i);
                   //invoke the SecondActivity.

                   finish();
                   //the current activity will get finished.
               }
               else
                {
                    Intent i=new Intent(getApplicationContext(),
                            LoginActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.

                    finish();
                    //the current activity will get finished.
                }

            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}