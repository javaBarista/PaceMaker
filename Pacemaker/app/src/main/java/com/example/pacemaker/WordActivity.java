package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
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

    private SharedPreferences pref;
    private ArrayList<WordForm> mList = new ArrayList<>();
    private ArrayList<String> wordItem = new ArrayList<>();
    private ViewPager viewPager;
    private MyPagerAdapter fpAdapter;
    private CircleIndicator circleIndicator;
    private Intent getIntent;
    private Spinner daySpin;
    private Button prevBtn;
    private Button nextBtn;
    private  static int day;
    private int voca_count;
    private GetEnglishWords getEnglishWords = new GetEnglishWords();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        voca_count = pref.getInt("voca_setting_count", 20);

        viewPager = findViewById(R.id.wordPager);
        circleIndicator = findViewById(R.id.indicator);
        daySpin = findViewById(R.id.daySpin);
        getIntent = getIntent();
        day = getIntent.getIntExtra("day", 0);
        getEnglishWords.execute();

        daySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = String.valueOf(daySpin.getSelectedItem());
                viewPager.setCurrentItem(Integer.parseInt(s.substring(0, s.indexOf("."))) - 1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        prevBtn = findViewById(R.id.word_prevBtn);
        nextBtn = findViewById(R.id.word_nextBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem() > 0) viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                else Toast.makeText(getApplicationContext(), "처음 페이지 입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem() < voca_count) viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                else Toast.makeText(getApplicationContext(), "마지막 페이지 입니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS;
        private ArrayList<WordForm> mList= new ArrayList<>();
       // private CircleIndicator indicator;

        public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<WordForm> mList, int count) {
            super(fragmentManager);
            NUM_ITEMS = count + 1;
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
            if(position == NUM_ITEMS - 1) {
                int end = (NUM_ITEMS - 1) * day;
                int start = end - NUM_ITEMS;
                return WordFragment.newInstance(mList.get(position).getWord(), true, start, end);
            } else {
                return WordFragment.newInstance(mList.get(position).getWord(), mList.get(position).getPron(),mList.get(position).getGram(), mList.get(position).getMean());
            }
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
            int end = voca_count * day;
            int start = end - voca_count + 1;

            String url = "https://nobles1030.cafe24.com/requestEnglishwords.php";
            RequestBody body = new FormBody.Builder()
                    .add("start", String.valueOf(start))
                    .add("end", String.valueOf(end))
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
                int i = 1;
                for (WordForm post: result){
                    Log.d("result is : ",String.valueOf(post));
                    mList.add(post);
                    wordItem.add(String.valueOf(i++) + ". " + post.getWord());
                }
            }
            WordForm last = new WordForm(String.valueOf(voca_count + 1), "\n\n\n단어 TEST", "", "", "");
            mList.add(last);

            //Adapter setting
            ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, wordItem);
            daySpin.setAdapter(adapter);
            fpAdapter = new MyPagerAdapter(getSupportFragmentManager(), mList, voca_count);
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
    public void onStop() {
        super.onStop();
        getEnglishWords.cancel(true);
    }
}