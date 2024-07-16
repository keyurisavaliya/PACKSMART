package com.sgp.packsmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postAtTime(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splash_screen.this, home_page.class);
                startActivity(i);
                finish();
            }
        },5*1000);

    }
}
