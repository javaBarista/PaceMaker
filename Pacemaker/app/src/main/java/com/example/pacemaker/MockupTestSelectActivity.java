package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private Bundle bundle;
    private LinearLayout linearLayout;
    private Spinner mocSpin;
    private TextView textView;
    private Button testStartBtn;
    private Button sendBtn;
    private Button resultBtn;

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
        for(int i = 1; i < 3; i++) mocItem.add(String.valueOf(i) + "회");
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mocItem);
        mocSpin.setAdapter(adapter);

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
                if(pref.getBoolean(mocSpin.getSelectedItem().toString() + "complete", false)) resultBtn.setVisibility(View.VISIBLE);
            }
        });

        testStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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