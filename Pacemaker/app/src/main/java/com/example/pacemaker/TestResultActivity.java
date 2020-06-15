package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private LinearLayout moc_lin;
    private TextView moc_college1;
    private TextView moc_college2;
    private TextView moc_college3;
    private TextView moc_ranking1;
    private TextView moc_ranking2;
    private TextView moc_ranking3;
    private TextView modify;
    private ArrayList<TestForm> mList = new ArrayList<>();
    private String identity1;
    private String identity2;
    private String url;
    private boolean isMoc = false;
    private RecyclerView mRecyclerView;
    private TestRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIntent = getIntent();
        bundle = getIntent.getExtras();
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        year = findViewById(R.id.result_year);
        college = findViewById(R.id.result_college);
        score_all = findViewById(R.id.all_score);
        moc_lin = findViewById(R.id.moc_result_linear);
        modify = findViewById(R.id.if_moc_is_modify);

        if(bundle.getString("year") == null){
            identity1 = bundle.getString("moc_subject");
            identity2 = bundle.getString("moc_num");

            moc_lin.setVisibility(View.VISIBLE);
            modify.setText("모의고사");
            moc_college1 = findViewById(R.id.moc_ranking_college1);
            moc_college2 = findViewById(R.id.moc_ranking_college2);
            moc_college3 = findViewById(R.id.moc_ranking_college3);
            moc_ranking1 = findViewById(R.id.moc_result_ranking1);
            moc_ranking2 = findViewById(R.id.moc_result_ranking2);
            moc_ranking3 = findViewById(R.id.moc_result_ranking3);

            url = "http://nobles1030.cafe24.com/requestMockupTest_analysis.php";
            isMoc = true;
        }
        else{
            identity1 = bundle.getString("year");
            identity2 = bundle.getString("college");

            url = "http://nobles1030.cafe24.com/requestPreviousTest_analysis.php";
        }

        year.setText(identity1);
        college.setText(identity2);
        score_all.setText(bundle.getString("count"));

        String tmp = pref.getString(identity1 + identity2 + "result", null);
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
        mAdapter = new TestRecyclerAdapter(mList, this, identity1, identity2);
        mRecyclerView.setAdapter(mAdapter);

        ranking = findViewById(R.id.result_ranking);
        OkHttpClient requestRanking = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("college", identity2)
                .add("year", identity1)
                .add("id", pref.getString("id", ""))
                .add("score", score.getText().toString())
                .build();
        requestRanking.newCall(new Request.Builder().url(url).post(body).build()).enqueue(new Callback() {
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
                            Log.d("ranking is ", result);
                            JsonElement jsonElement =  new JsonParser().parse(result);
                            JsonObject jsonObject = jsonElement.getAsJsonObject();

                            ranking.setText(String.valueOf(jsonObject.get("rank")).replace("\"", "") + "/" + String.valueOf(jsonObject.get("all")).replace("\"", ""));
                            if(isMoc){
                                moc_college1.setText(String.valueOf(jsonObject.get("col1_name")).replace("\"", ""));
                                moc_college2.setText(String.valueOf(jsonObject.get("col2_name")).replace("\"", ""));
                                moc_college3.setText(String.valueOf(jsonObject.get("col3_name")).replace("\"", ""));

                                moc_ranking1.setText(String.valueOf(jsonObject.get("col1_rank")).replace("\"", "") + "/" + String.valueOf(jsonObject.get("col1_all")).replace("\"", ""));
                                moc_ranking2.setText(String.valueOf(jsonObject.get("col2_rank")).replace("\"", "") + "/" + String.valueOf(jsonObject.get("col2_all")).replace("\"", ""));
                                moc_ranking3.setText(String.valueOf(jsonObject.get("col3_rank")).replace("\"", "") + "/" + String.valueOf(jsonObject.get("col3_all")).replace("\"", ""));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                // TODO : process the click event for action_search item.
                onBackPressed();
                return true ;
            // ...
            // ...
            default :
                return super.onOptionsItemSelected(item);
        }
    }
}
