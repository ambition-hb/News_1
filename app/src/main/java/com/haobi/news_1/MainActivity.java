package com.haobi.news_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.haobi.news_1.splash.TimeView;

public class MainActivity extends AppCompatActivity {

    TimeView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = (TimeView)findViewById(R.id.time);
        time.setD(200);
    }
}
