package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pacemaker.ui.test.TestForm;
import com.example.pacemaker.ui.test.TestRecyclerAdapter;
import com.example.pacemaker.ui.word.WordForm;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TestResultActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private Intent getIntent;
    private Bundle bundle;
    private JSONArray jsonArray;
    private TextView year;
    private TextView college;
    private TextView score;
    private TextView score_all;
    private TextView ranking;
    private ArrayList<TestForm> mList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TestRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        getIntent = getIntent();
        bundle = getIntent.getExtras();
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        year = findViewById(R.id.result_year);
        college = findViewById(R.id.result_college);
        score_all = findViewById(R.id.all_score);
        year.setText(bundle.getString("year"));
        college.setText(bundle.getString("college"));
        score_all.setText(bundle.getString("count"));

        String tmp = pref.getString(bundle.getString("year") + bundle.getString("college") + "result", null);
        try {
            jsonArray = tmp != null ? new JSONArray(tmp) : new JSONArray() ;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        score = findViewById(R.id.result_score);
        mRecyclerView = findViewById(R.id.wrong_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(jsonArray != null){
            int cnt = Integer.valueOf(bundle.getString("count"));
            score.setText(String.valueOf(cnt - jsonArray.length()));
            for(int i = 0; i < jsonArray.length(); i++) {
                try {
                    Log.d("json len is :", jsonArray.getString(i));
                    JsonElement jsonElement = new JsonParser().parse(jsonArray.getString(i));
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    TestForm temp = new TestForm(String.valueOf(jsonObject.get("num")).replace("\"", ""), String.valueOf(jsonObject.get("address")).replace("\"", ""), String.valueOf(jsonObject.get("text")).replace("\"", ""), String.valueOf(jsonObject.get("part")).replace("\"", ""), String.valueOf(jsonObject.get("answer")).replace("\"", ""));
                    mList.add(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        mAdapter = new TestRecyclerAdapter(mList, this);
        mRecyclerView.setAdapter(mAdapter);

        //웹 디비에 결과 저장 리턴은 같은 년도의 같은 대학 점수 분에 내 성적의 랭크
    }

}
