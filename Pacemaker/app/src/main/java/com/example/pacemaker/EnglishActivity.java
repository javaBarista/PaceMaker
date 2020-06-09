package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.pacemaker.ui.word.DayForm;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.ceil;

public class EnglishActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private RecyclerView mRecyclerView;
    private EnglishListAdapter mAdapter;
    private ArrayList<DayForm> dayMap = new ArrayList<>();
    private Button DayTest_btn;
    private final int max_count = 1020;
    private int voca_count;
    public static Activity _EnglishActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engish);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        voca_count = pref.getInt("voca_setting_count", 20);

        _EnglishActivity = EnglishActivity.this;
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // day 누적 테스트
        DayTest_btn = (Button) findViewById(R.id.selectDay_Test_btn);
        DayTest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnglishActivity.this, WordTestSelectActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView = findViewById(R.id.english_day_item);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        int day = (int) ceil((double)(max_count / voca_count));
        int rem = 0;
        if(day % 3 != 0){
            rem = (day % 3);
            day -= rem;
        }
        for(int i = 1; i <= day; i+=3) {
            DayForm dayForm = new DayForm("DAY " + String.valueOf(i), "DAY " + String.valueOf(i + 1), "DAY " + String.valueOf(i + 2));
            dayMap.add(dayForm);
        }
        if(rem > 0) {
            ArrayList<String> tmp = new ArrayList<>();
            for(int i = 1; i <= rem; i++) tmp.add("DAY " + String.valueOf(day + i));
            for (int i = rem + 1; i <= 3; i++) tmp.add("NO");
            DayForm dayForm = new DayForm(tmp.get(0), tmp.get(1), tmp.get(2));
            dayMap.add(dayForm);
        }

        mAdapter = new EnglishListAdapter(dayMap, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.english_menu, menu);
        MenuItem word = menu.findItem(R.id.action_word);
        word.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO : process the click event for action_search item.
                onBackPressed();
                return true;
            case R.id.action_trans:
                Intent transIntent = new Intent(this, TranslateActivity.class);
                startActivity(transIntent);
                finish();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter = new EnglishListAdapter(dayMap, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        mAdapter = new EnglishListAdapter(dayMap, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
