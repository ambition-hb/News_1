package com.haobi.news_1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.haobi.news_1.splash.TimeView;

public class MainActivity extends AppCompatActivity {

    TimeView time;

    int length = 2*1000;
    int space = 250;
    int now = 0;
    int total;

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = (TimeView)findViewById(R.id.time);

        total = length/space;
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        int now = msg.arg1;
                        if (now <= total){
                            time.setProgress(total, now);
                        }else{
                            //移除任务
                            mHandler.removeCallbacks(reshRing);
                        }
                        break;
                }
            }
        };

        mHandler.post(reshRing);

//        time.setD(200);

    }

    Runnable reshRing = new Runnable() {
        @Override
        public void run() {
            //消息池中复用
            Message message = mHandler.obtainMessage(0);
            message.arg1 = now;
            mHandler.sendMessage(message);
            mHandler.postDelayed(this, space);
            now++;
        }
    };

}
