package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import com.example.pacemaker.ui.word.WordFragment;
import com.example.pacemaker.ui.word.WordForm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WordActivity extends AppCompatActivity {

    private ArrayList<WordForm> mList = new ArrayList<>();
    private ViewPager viewPager;
    private MyPagerAdapter fpAdapter;
    private CircleIndicator circleIndicator;
    private Intent getIntent;
    private Spinner daySpin;
    private String day;
    private ImageButton refreshBtn;
    private GetEnglishWords getEnglishWords = new GetEnglishWords();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.wordPager);
        circleIndicator = findViewById(R.id.indicator);
        getIntent = getIntent();
        day = getIntent.getStringExtra("day");
        Log.d("day is : ", day.replace("day", ""));
        getEnglishWords.execute();

        daySpin = findViewById(R.id.daySpin);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_dropdown_item);
        daySpin.setAdapter(adapter);
        daySpin.setSelection(adapter.getPosition(day));

        refreshBtn = findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                intent.putExtra("day", daySpin.getSelectedItem().toString());
                startActivity(intent);
                finish();
            }
        });

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 20;
        private ArrayList<WordForm> mList;

        public MyPagerAdapter(FragmentManager fragmentManager,  ArrayList<WordForm> mList) {
            super(fragmentManager);
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

    private class GetEnglishWords extends AsyncTask<Void, Void, WordForm[]> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected WordForm[] doInBackground(Void... voids) {

            String url = "https://nobles1030.cafe24.com/requestEnglishwords.php";
            RequestBody body = new FormBody.Builder()
                    .add("Day", day.replace("day", ""))
                    .build();

            Request request = new Request.Builder().url(url).post(body).build();

            try {
                Response response = client.newCall(request).execute();
                //Json data를 Gson형식으로 파싱해 리스트 생성
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();

                JsonElement rootObject = parser.parse(response.body().charStream());
                Log.d("data is:", rootObject.toString());
                WordForm[] posts = gson.fromJson(rootObject, WordForm[].class);
                return posts;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(WordForm[] result) {
            if(result.length > 0){
                for (WordForm post: result){
                    Log.d("result is : ",String.valueOf(post));
                    mList.add(post);
                }
            }
            //Adapter setting
            fpAdapter = new MyPagerAdapter(getSupportFragmentManager(), mList);
            viewPager.setAdapter(fpAdapter);
            circleIndicator.setViewPager(viewPager);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case android.R.id.home:
                // TODO : process the click event for action_search item.
                onBackPressed();
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getEnglishWords.cancel(true);
    }

}
