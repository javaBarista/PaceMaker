package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.pacemaker.ui.word.WordTestForm;
import com.example.pacemaker.ui.word.WordTestFragment;

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
import java.util.List;

public class WordTestAccumulationActivity extends AppCompatActivity {
    private ViewPager mPager;
    private WordTestAccumulationActivity.MPagerAdapter mPagerAdapter;
    private static String startDay;
    private static String endDay;
    WordTestForm wordTestForm;
    private ArrayList<WordTestForm> wordList = new ArrayList<>();
    private ArrayList<ListViewItem> arrayList = new ArrayList<>();
    private ArrayList<String> nAnswerList = new ArrayList<>();
    private ArrayList<String> parts = new ArrayList<>();
    private Intent getIntent;
    String mJsonString;
    private static String TAG = "phptest_TestWord2";
    private static final String TAG_JSON = "Word_test";
    private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test_accumulation);

        mPager = findViewById(R.id.accumulation_test_pager);
        getIntent = getIntent();
        startDay = getIntent.getStringExtra("startDay").substring(3);
        endDay = getIntent.getStringExtra("endDay").substring(3);

        getTestData2 getTestData = new getTestData2();
        getTestData.execute("https://nobles1030.cafe24.com/wordTest2.php?");
    }

    public static class MPagerAdapter extends FragmentStatePagerAdapter {
        private static int NUM_ITEMS = 20;
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
            return WordTestFragment.newInstance((i + 1) + "/20", wordList.get(i).getTestWord(), wordList.get(i).getAnswer(),
                    wordList.get(i).getnAnswer1(), wordList.get(i).getnAnswer2(), wordList.get(i).getnAnswer3(), wordList.get(i).getAnswer(), startDay + "-" + endDay);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

    public class getTestData2 extends AsyncTask<String, Void, String> {
        String TAG = "JsonParseTest2";

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            try {
                String selectDay = "Day1=" + startDay + "&" + "Day2=" + endDay;
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
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

            for (int i = 0; i < 20; i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String Word;
                String Answer;
                String partSpeech;
                Word = item.getString("Word");
                Answer = item.getString("Mean");
                partSpeech = item.getString("partSpeech");
                ListViewItem items = new ListViewItem(Word, Answer);
                arrayList.add(items);
                parts.add(partSpeech);
            }
            for (int i = 20; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String nAnswer;
                String ps;
                nAnswer = item.getString("nA1");
                ps = item.getString("ps");
                ListViewItem items = new ListViewItem(ps, nAnswer);
                arrayList.add(items);
            }

            for (int i = 0; i < 20; i++) {
                List<String> nA = new ArrayList<String>();
                for (int j = 20; j < jsonArray.length(); j++) {
                    if (parts.get(i).equals(arrayList.get(j).getCollege_name())) {
                        nA.add(arrayList.get(j).getCollege_dday());
                    }
                }
                Collections.shuffle(nA);

                wordTestForm = new WordTestForm(arrayList.get(i).getCollege_name(), arrayList.get(i).getCollege_dday(), nA.get(0).toString(), nA.get(1).toString(), nA.get(2).toString());
                wordList.add(wordTestForm);
            }
            mPagerAdapter = new MPagerAdapter(getSupportFragmentManager(), wordList);
            mPager.setAdapter(mPagerAdapter);
            mPager.setOffscreenPageLimit(19);
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

    public int counter() {
        return count++;
    }

    public int countN() {
        return count;
    }
}
