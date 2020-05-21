package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.pacemaker.DdayActivity;
import com.example.pacemaker.ListViewItem;
import com.example.pacemaker.R;
import com.example.pacemaker.WordActivity;
import com.example.pacemaker.ui.word.WordTestForm;
import com.example.pacemaker.ui.word.WordTestFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WordTestActivity extends AppCompatActivity {
    private ViewPager mPager;
    private MPagerAdapter mPagerAdapter;
    private Intent getIntent;
    private ArrayList<WordTestForm> wordList = new ArrayList<>();
    private ArrayList<ListViewItem> arrayList = new ArrayList<>();
    private static String dayInfo;
    static int[] numArr;
    WordTestForm wordTestForm;

    private static String TAG = "phptest_TestWord";
    String mJsonString;
    private static final String TAG_JSON = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test);
        mPager = findViewById(R.id.test_pager);
        getIntent = getIntent();
        dayInfo = getIntent.getStringExtra("dayInfo").substring(3);
        int num[] = {0, 1, 2, 3, 4};
        numArr = shuffle(num, 10);

        getTestData getTestData = new getTestData();
        getTestData.execute("https://nobles1030.cafe24.com/requestEnglishTestwords.php?");
    }

    public static class MPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 5;
        private ArrayList<WordTestForm> wordList = new ArrayList<>();

        public MPagerAdapter(FragmentManager fragmentManager, ArrayList<WordTestForm> wordList) {
            super(fragmentManager);
            this.wordList = wordList;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int i) {
            if (i == numArr[0]) {
                return WordTestFragment.newInstance((i+1)+"/5",wordList.get(i).getTestWord(), wordList.get(i).getAnswer(),
                        wordList.get(i).getnAnswer1(), wordList.get(i).getnAnswer2(), wordList.get(i).getnAnswer3(), wordList.get(i).getAnswer(), dayInfo);
            } else if (i == numArr[1]) {
                return WordTestFragment.newInstance((i+1)+"/5", wordList.get(i).getTestWord(), wordList.get(i).getnAnswer3(),
                        wordList.get(i).getnAnswer1(), wordList.get(i).getnAnswer2(), wordList.get(i).getAnswer(),wordList.get(i).getAnswer(), dayInfo);
            } else if (i == numArr[2]) {
                return WordTestFragment.newInstance((i+1)+"/5", wordList.get(i).getTestWord(), wordList.get(i).getnAnswer2(),
                        wordList.get(i).getAnswer(), wordList.get(i).getnAnswer3(), wordList.get(i).getnAnswer1(),wordList.get(i).getAnswer(), dayInfo);
            } else if (i == numArr[3]) {
                return WordTestFragment.newInstance((i+1)+"/5", wordList.get(i).getTestWord(), wordList.get(i).getnAnswer2(),
                        wordList.get(i).getnAnswer3(), wordList.get(i).getAnswer(), wordList.get(i).getnAnswer1(),wordList.get(i).getAnswer(), dayInfo);
            } else {
                return WordTestFragment.newInstance((i+1)+"/5", wordList.get(i).getTestWord(), wordList.get(i).getAnswer(),
                        wordList.get(i).getnAnswer2(), wordList.get(i).getnAnswer3(), wordList.get(i).getnAnswer1(),wordList.get(i).getAnswer(), dayInfo);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

    private static int[] shuffle(int[] array, int count) {
        int temp1, temp2, randomNum1, randomNum2;
        for (int i = 0; i < count; i++) {
            randomNum1 = (int)(Math.random()*array.length);
            temp1 = array[randomNum1];
            randomNum2 = (int)(Math.random()*array.length);
            temp2 = array[randomNum2];

            array[randomNum1] = temp2;
            array[randomNum2] = temp1;
        }
        return array;
    }

    public class getTestData extends AsyncTask<String, Void, String> {
        String TAG = "JsonParseTest";
        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            try {
                String selectDay = "Day=" + dayInfo;
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(selectDay.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();
            } catch (Exception e) {
                Log.d(TAG, "Error", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {
            } else {
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String Word = item.getString("Word");
                String meaning = item.getString("meaning");

                ListViewItem items = new ListViewItem(Word, meaning);
                arrayList.add(items);
                //wordTestForm = new WordTestForm(Number, Word, pronunciation, partSpeech, meaning);
                //wordList.add(wordTestForm);
            }

            for (int i = 0; i < 5; i++) {
                wordTestForm = new WordTestForm(arrayList.get(i).getCollege_name(), arrayList.get(i).getCollege_dday(), arrayList.get(i + 5).getCollege_dday(), arrayList.get(i + 10).getCollege_dday(), arrayList.get(i + 15).getCollege_dday());
                wordList.add(wordTestForm);
            }
            mPagerAdapter = new MPagerAdapter(getSupportFragmentManager(), wordList);
            mPager.setAdapter(mPagerAdapter);

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}
