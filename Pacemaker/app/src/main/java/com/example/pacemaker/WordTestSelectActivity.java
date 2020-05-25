package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class WordTestSelectActivity extends AppCompatActivity {
    private Spinner startDay_spinner;
    private Spinner endDay_spinner;
    private Button testStart_btn;
    private String startDay;
    private String endDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test_select);
        setTitle("시작과 끝 DAY를 선택하세요");

        startDay_spinner = (Spinner) findViewById(R.id.spinner_startDay);
        endDay_spinner = (Spinner) findViewById(R.id.spinner_endDay);
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
                intent.putExtra("startDay", startDay);
                intent.putExtra("endDay", endDay);
                startActivity(intent);
            }
        });
    }
}
