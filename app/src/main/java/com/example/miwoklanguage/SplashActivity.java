package com.example.miwoklanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //to hide the actionbar
        getSupportActionBar().hide();

        //To give delay to splash screen
        Thread thread = new Thread(){
          //public method to launch the main activity after the splash screen
          public void run(){
              try{
                  sleep(4000);
              }
              catch (Exception e){
                  //for all type of exception catching
                  e.printStackTrace();
              }
              finally {
                  Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                  startActivity(intent);
              }
          }
        };
        thread.start();
    }
}