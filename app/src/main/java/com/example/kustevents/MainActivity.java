package com.example.kustevents;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = (ProgressBar)findViewById(R.id.progress_circular) ;

        new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mProgress.setProgress(1000);
            }

            @Override
            public void onFinish()
            {
                startActivity(new Intent(getApplicationContext(), UserLogin.class));
            }
        }.start();

    }
}
