package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.example.pacemaker.ui.word.WordForm;
import com.example.pacemaker.ui.word.WordFragment;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private ViewPager testPager;
    private MyPagerAdapter myPagerAdapter;
    private Bundle bundle;
    private Intent getIntent;
    private TextView timmer;
    private CountDownTimer countDownTimer;
    private int time;
    private long MILLISINFUTURE = 60000; //* 분 하면 나옴
    private final long COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getIntent = getIntent();
        bundle = getIntent.getExtras();

        time = bundle.getInt("time");
        timmer = findViewById(R.id.timmer);
        countDownTimer = new CountDownTimer(MILLISINFUTURE * time, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                long authCount = millisUntilFinished / 1000;

                if ((authCount - ((authCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    timmer.setText((authCount / 60) + " : " + (authCount - ((authCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    timmer.setText((authCount / 60) + " : 0" + (authCount - ((authCount / 60) * 60)));
                }
            }


            @Override
            public void onFinish() { //시간이 다 되면 액티비티 종료


            }
        }.start();

        //testPager = findViewById(R.id.testPager);
        //myPagerAdapter = new WordActivity.MyPagerAdapter(getSupportFragmentManager(), bundle.get("count"), mList);
        //testPager.setAdapter(myPagerAdapter);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS;
        private ArrayList<WordForm> mList= new ArrayList<>();
        // private CircleIndicator indicator;

        public MyPagerAdapter(FragmentManager fragmentManager, int count, ArrayList<WordForm> mList) {
            super(fragmentManager);
            NUM_ITEMS = count;
            this.mList = mList;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            //if(position == 20) 테스트 페이지 출력
            return WordFragment.newInstance(mList.get(position).getWord(), mList.get(position).getPron(),mList.get(position).getGram(), mList.get(position).getMean());
        }
        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
