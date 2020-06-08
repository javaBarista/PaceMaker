package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import static java.lang.Math.ceil;

public class WordTestSelectActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private int voca_count;
    private final int max_count = 1020;
    private Spinner startDay_spinner;
    private Spinner endDay_spinner;
    private Button testStart_btn;
    private ArrayList<String> dayItem = new ArrayList<>();
    private String startDay;
    private String endDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test_select);
        setTitle("시작과 끝 DAY를 선택하세요");
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        voca_count = pref.getInt("voca_setting_count", 20);
        int day = (int) ceil((double)(max_count / voca_count));

        for(int i = 1; i <= day; i++) dayItem.add("DAY " + String.valueOf(i));
        ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, dayItem);

        startDay_spinner = (Spinner) findViewById(R.id.spinner_startDay);
        startDay_spinner.setAdapter(adapter);
        endDay_spinner = (Spinner) findViewById(R.id.spinner_endDay);
        endDay_spinner.setAdapter(adapter);
        testStart_btn = (Button) findViewById(R.id.test_start_btn);

        startDay_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                startDay = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        endDay_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                endDay = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        testStart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordTestSelectActivity.this, WordTestAccumulationActivity.class);
                int start = Integer.parseInt(startDay.substring(3));
                int end = Integer.parseInt(endDay.substring(3));
                if (start > end) {
                    intent.putExtra("start", end);
                    intent.putExtra("end", start);
                } else {
                    intent.putExtra("start", start);
                    intent.putExtra("end", end);
                }
                startActivity(intent);
            }
        });
    }
}
