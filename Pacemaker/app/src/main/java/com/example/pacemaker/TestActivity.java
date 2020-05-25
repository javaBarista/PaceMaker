package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pacemaker.ui.test.TestForm;
import com.example.pacemaker.ui.test.TestFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {

    private ViewPager testPager;
    private MyPagerAdapter myPagerAdapter;
    private Bundle bundle;
    private Intent getIntent;
    private TextView timmer;
    private ArrayList<TestForm> mList= new ArrayList<>();
    private CountDownTimer countDownTimer;
    private int time;
    private long MILLISINFUTURE = 60000; //* 분 하면 나옴
    private final long COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)
    private long backKeyPressedTime = 0;

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
                Toast.makeText(getApplicationContext(), "시험이 종료되었습니다.", Toast.LENGTH_LONG).show();
                //결과용 액티비티 만들어야하나....?
            }
        }.start();

        testPager = findViewById(R.id.testPager);
        new makeTestList().execute(bundle.getString("year"), bundle.getString("college"));
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS;
        private ArrayList<TestForm> mList;

        public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<TestForm> mList) {
            super(fragmentManager);
            this.mList = mList;
            NUM_ITEMS = mList.size();
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            return TestFragment.newInstance(mList.get(position).getAddress(), mList.get(position).getMain_text(),mList.get(position).isMain_text(), mList.get(position).getPart(), mList.get(position).getAnswer());
        }
        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }


    private class makeTestList extends AsyncTask<String, Void, TestForm[]> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected TestForm[] doInBackground(String... str) {

            String url = "https://nobles1030.cafe24.com/requestPreviousTest.php";
            @SuppressLint("WrongThread") RequestBody body = new FormBody.Builder()
                    .add("year", str[0])
                    .add("college", str[1])
                    .build();

            Request request = new Request.Builder().url(url).post(body).build();

            try {
                Response response = client.newCall(request).execute();
                //Json data를 Gson형식으로 파싱해 리스트 생성
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();

                JsonElement rootObject = parser.parse(response.body().charStream());
                Log.d("data is:", rootObject.toString());
                TestForm[] posts = gson.fromJson(rootObject, TestForm[].class);
                return posts;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(TestForm[] result) {
            if(result.length > 0){
                for (TestForm post: result){
                    Log.d("result is : ",String.valueOf(post));
                    mList.add(post);
                }
            }
            myPagerAdapter = new TestActivity.MyPagerAdapter(getSupportFragmentManager(), mList);
            testPager.setAdapter(myPagerAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로가기\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }
}
