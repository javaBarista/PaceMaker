package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MockupTestSelectActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private Intent getIntent;
    private ArrayList<String> mocItem = new ArrayList<>();
    private ArrayList<String> subItem = new ArrayList<>();
    private Bundle bundle;
    private LinearLayout linearLayout;
    private Spinner mocSpin;
    private Spinner subSpin;
    private TextView textView;
    private Button testStartBtn;
    private Button sendBtn;
    private Button resultBtn;
    private final int max_moctest = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mockup_test_select);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        getIntent = getIntent();
        bundle = getIntent.getExtras();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mocSpin = findViewById(R.id.moc_num_spin);
        for(int i = 1; i <= max_moctest; i++) mocItem.add(String.valueOf(i) + "회");
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mocItem);
        mocSpin.setAdapter(adapter);

        subSpin = findViewById(R.id.moc_sub_spin);
        subItem.add("영어");
        subItem.add("수학");
        ArrayAdapter subAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subItem);
        subSpin.setAdapter(subAdapter);

        linearLayout = findViewById(R.id.moctest_notify);
        textView = findViewById(R.id.selected_moc_num);
        testStartBtn = findViewById(R.id.moctest_start);
        resultBtn = findViewById(R.id.go_moc_result_btn);
        sendBtn = findViewById(R.id.moctest_sendBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                textView.setText(mocSpin.getSelectedItem().toString());
                if(pref.getBoolean(subSpin.getSelectedItem().toString() + mocSpin.getSelectedItem().toString() + "complete", false)) resultBtn.setVisibility(View.VISIBLE);
                else resultBtn.setVisibility(View.GONE);
            }
        });

        testStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mocTestIntent = new Intent(getApplicationContext(), TestActivity.class);
                bundle.putString("moc_subject", subSpin.getSelectedItem().toString());
                bundle.putInt("time", 60);
                bundle.putString("count", "40");
                bundle.putString("moc_num", mocSpin.getSelectedItem().toString());
                mocTestIntent.putExtras(bundle);
                startActivity(mocTestIntent);
                finish();
            }
        });

        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mocResultIntent = new Intent(getApplicationContext(), TestResultActivity.class);
                bundle.putString("moc_subject", subSpin.getSelectedItem().toString());
                bundle.putString("count", "40");
                bundle.putString("moc_num", mocSpin.getSelectedItem().toString());
                mocResultIntent.putExtras(bundle);
                startActivity(mocResultIntent);
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
