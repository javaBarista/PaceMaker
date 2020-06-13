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
import java.util.HashMap;

public class TestSelectActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private Spinner year_spin;
    private Spinner college_spin;
    private Button sendBtn;
    private Button resultBtn;
    private LinearLayout testNotify;
    private TextView year;
    private TextView college;
    private TextView time;
    private TextView questions;
    private Button startBtn;
    private HashMap<String, String> hmap = new HashMap<>();
    ArrayList<String> yearList = new ArrayList<String>();
    private Intent getIntent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testselect);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        getIntent = getIntent();
        bundle = getIntent.getExtras();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        makeInfo();

        year_spin = findViewById(R.id.test_year_spin);
        ArrayAdapter yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, yearList);
        year_spin.setAdapter(yearAdapter);
        college_spin = findViewById(R.id.test_college_spin);
        ArrayAdapter collegeAdapter = ArrayAdapter.createFromResource(this, R.array.college, android.R.layout.simple_spinner_dropdown_item);
        college_spin.setAdapter(collegeAdapter);

        testNotify = findViewById(R.id.test_notify);
        year = findViewById(R.id.selected_year);
        college = findViewById(R.id.selected_college);
        time = findViewById(R.id.test_time);
        questions = findViewById(R.id.test_exam_questions);

        sendBtn = findViewById(R.id.test_sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year.setText(year_spin.getSelectedItem().toString());
                college.setText(college_spin.getSelectedItem().toString());
                time.setText(hmap.get(college_spin.getSelectedItem().toString() + "시간"));
                questions.setText(hmap.get(college_spin.getSelectedItem().toString() + "문항"));
                testNotify.setVisibility(View.VISIBLE);
                if(pref.getBoolean(year_spin.getSelectedItem().toString() + college_spin.getSelectedItem().toString() + "complete", false)) resultBtn.setVisibility(View.VISIBLE);
                else resultBtn.setVisibility(View.GONE);
            }
        });

        startBtn = findViewById(R.id.test_start);
        resultBtn = findViewById(R.id.go_result_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(getApplicationContext(), TestActivity.class);
                bundle.putInt("time", Integer.parseInt(hmap.get(college_spin.getSelectedItem().toString() + "시간")));
                bundle.putString("count", hmap.get(college_spin.getSelectedItem().toString() + "문항"));
                bundle.putString("college", college_spin.getSelectedItem().toString());
                bundle.putString("year", year_spin.getSelectedItem().toString());
                testIntent.putExtras(bundle);
                startActivity(testIntent);
                finish();
            }
        });

        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(getApplicationContext(), TestResultActivity.class);
                bundle.putString("count", hmap.get(college_spin.getSelectedItem().toString() + "문항"));
                bundle.putString("college", college_spin.getSelectedItem().toString());
                bundle.putString("year", year_spin.getSelectedItem().toString());
                resultIntent.putExtras(bundle);
                startActivity(resultIntent);
            }
        });
    }

    private void makeInfo(){
        yearList.add("2019");

        hmap.put("가천대학교" + "시간", "60");
        hmap.put("가천대학교" + "문항", "40");
        hmap.put("서강대학교" + "시간", "60");
        hmap.put("서강대학교" + "문항", "40");
        hmap.put("성균관대학교" + "시간", "100");
        hmap.put("성균관대학교" + "문항", "50");
        hmap.put("중앙대학교" + "시간", "60");
        hmap.put("중앙대학교" + "문항", "40");
        hmap.put("한양대학교" + "시간", "60");
        hmap.put("한양대학교" + "문항", "40");
        hmap.put("이화여자대학교" + "시간", "100");
        hmap.put("이화여자대학교" + "문항", "50");
        /*
        hmap.put("가톨릭대학교", );
        hmap.put("강남대학교", );
        hmap.put("건국대학교", );
        hmap.put("경기대학교", );
        hmap.put("경희대학교", );
        hmap.put("광운대학교", );
        hmap.put("국민대학교", );
        hmap.put("단국대학교", );
        hmap.put("덕성여자대학교", );
        hmap.put("동국대학교", );
        hmap.put("동덕여자대학교", );
        hmap.put("명지대학교", );
        hmap.put("산업기술대학교", );
        hmap.put("삼육대학교", );
        hmap.put("상명대학교", );

        hmap.put("서울시립대학교", );
        hmap.put("서울여자대학교", );

        hmap.put("세종대학교", );
        hmap.put("수원대학교", );
        hmap.put("숙명여자대학교", );
        hmap.put("숭실대학교", );
        hmap.put("아주대학교", );

        hmap.put("인하대학교", );

        hmap.put("한국외국어대학교", );
        hmap.put("한국항공대학교", );
        hmap.put("한성대학교", );
        hmap.put("한양대에리카", );

        hmap.put("홍익대학교", );
         */
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
