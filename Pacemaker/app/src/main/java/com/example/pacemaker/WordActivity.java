package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class WordActivity extends AppCompatActivity {

    private Intent getIntent;
    private String daylist[];
    private Spinner daySpin;
    private String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        getIntent = getIntent();
        day = getIntent.getStringExtra("day");

        daylist = new String[31];
        for(int i = 1; i <=30; i++) daylist[i] = "day" + i;

        daySpin = findViewById(R.id.daySpin);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item , daylist);
        daySpin.setAdapter(adapter);

        int spinnerIndex = 0;
        for(int i =0; i <= 30;i++) {
            if(daylist.equals(day))
                spinnerIndex = i;
        }
        daySpin.setSelection(spinnerIndex);
    }
}
