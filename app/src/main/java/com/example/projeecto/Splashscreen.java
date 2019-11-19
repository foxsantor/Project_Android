package com.example.projeecto;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(Splashscreen.this).withFullScreen().withTargetActivity(MainActivity.class).withSplashTimeOut(3000)
                .withBackgroundResource(R.drawable.ic_launcher_background);
        View splashscreen = config.create();
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(splashscreen);
    }

}
