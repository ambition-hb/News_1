package com.haobi.news_1;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.haobi.news_1.news.fragment.EmptyFragment;
import com.haobi.news_1.news.fragment.NewsFragment;
import com.haobi.news_1.splash.TimeView;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    long lastbacktime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1-找到FragmentTabHost
        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.tab_Host);
        //获取tab的标题
        String[] titles = getResources().getStringArray(R.array.tab_title);
        //背景图
        int[] icons = new int[]{R.drawable.news_selector, R.drawable.reading_selector, R.drawable.video_selector, R.drawable.topic_selector, R.drawable.mine_selector};
        //目前只有新闻碎片，其余的待添加(暂用空的代替)
        Class[] classes = new Class[]{NewsFragment.class, EmptyFragment.class, EmptyFragment.class, EmptyFragment.class, EmptyFragment.class};
        //2-绑定Fragment容器
        tabHost.setup(this, getSupportFragmentManager(), R.id.content);
        //3-生成不同的标签，Tag相当于标签的id
        for (int i=0;i<titles.length;i++){
            TabHost.TabSpec tmp = tabHost.newTabSpec(""+i);
            tmp.setIndicator(getEveryView(this, titles, icons,i));
            tabHost.addTab(tmp, classes[i], null);
        }

        //监控这个FragmentTabHost的切换
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {
                Log.i("测试7", "tabId = "+tabId);
            }
        });

        //(预留)设置预选择的tag页面
        //tabHost.setCurrentTabByTag("1");

    }

    public View getEveryView(Context context, String[] titles, int[] icons, int index){
        LayoutInflater inflater = LayoutInflater.from(context);
        View title_view = inflater.inflate(R.layout.item_title, null);
        TextView title = (TextView) title_view.findViewById(R.id.title);
        ImageView icon = (ImageView) title_view.findViewById(R.id.icon);
        //设置标签的内容
        title.setText(titles[index]);
        icon.setImageResource(icons[index]);
        return title_view;
    }

    @Override
    public void onBackPressed() {
        //1、先取到当前的时间
        long now =  System.currentTimeMillis();
        //2、此时lastbacktime初始值为0，now是一个很大的值
        if(now - lastbacktime < 1000){
            finish();
        }else{
            Toast.makeText(this, "再按一次退出网易新闻", Toast.LENGTH_SHORT).show();
        }
        //3、获取到当前的时间
        lastbacktime = now;
    }
}
