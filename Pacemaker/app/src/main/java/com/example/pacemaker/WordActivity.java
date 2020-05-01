package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pacemaker.ui.mypage.MyPageFragment;
import com.example.pacemaker.ui.word.Word1Fragment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WordActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentPagerAdapter fpAdapter;
    private CircleIndicator circleIndicator;
    private Intent getIntent;
    private String daylist[];
    private Spinner daySpin;
    private String day;

    //디비에 day값만 넘겨주고 거기에 해당하는 단어들을 오름차순으로 가져와서 class에 매핑시켜 배열로 저장 후 각 프래그먼트에 넘겨줘야한다.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        viewPager = findViewById(R.id.wordPager);
        fpAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fpAdapter);
        circleIndicator = findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);

        getIntent = getIntent();
        day = getIntent.getStringExtra("day");

        daySpin = findViewById(R.id.daySpin);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_dropdown_item);
        daySpin.setAdapter(adapter);
        daySpin.setSelection(adapter.getPosition(day));
    }

    public void makeList(){
        OkHttpClient request = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("day", day.replace("day", ""))
                .build();

        request.newCall(new Request.Builder().url("https://nobles1030.cafe24.com/RequestWord.php").post(body).build()).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = response.body().string();

                            if(result.contains("success")){
                                JsonElement jsonElement =  new JsonParser().parse(result.substring(result.indexOf("{"), result.indexOf("}") + 1));
                                JsonObject jsonObject = jsonElement.getAsJsonObject();
                              //  url = jsonObject.get("Address").toString().replaceAll("\\/", "/").replaceAll("\"", "");

                                final Handler mHandler = new Handler();


                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 1;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return Word1Fragment.newInstance("Pace Maker", "발음.......","명사", "심박 조율기");
                case 1:
                    //return SecondFragment.newInstance(1, "Page # 2");
                case 2:
                    //return ThirdFragment.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
