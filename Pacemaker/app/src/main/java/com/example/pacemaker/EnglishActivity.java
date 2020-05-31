package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class EnglishActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private HashMap<String, Button> dayMap;
    private Button day1;
    private Button day2;
    private Button day3;
    private Button day4;
    private Button day5;
    private Button day6;
    private Button day7;
    private Button day8;
    private Button day9;
    private Button day10;
    private Button day11;
    private Button day12;
    private Button day13;
    private Button day14;
    private Button day15;
    private Button day16;
    private Button day17;
    private Button day18;
    private Button day19;
    private Button day20;
    private Button day21;
    private Button day22;
    private Button day23;
    private Button day24;
    private Button day25;
    private Button day26;
    private Button day27;
    private Button day28;
    private Button day29;
    private Button day30;
    private Button day31;
    private Button day32;
    private Button day33;
    private Button day34;
    private Button day35;
    private Button day36;
    private Button day37;
    private Button day38;
    private Button day39;
    private Button day40;
    private Button day41;
    private Button day42;
    private Button day43;
    private Button day44;
    private Button day45;
    private Button day46;
    private Button day47;
    private Button day48;
    private Button day49;
    private Button day50;
    private Button day51;
    private Button DayTest_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engish);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        link_Button();

        day1.setOnClickListener(new ClickEvent());
        day2.setOnClickListener(new ClickEvent());
        day3.setOnClickListener(new ClickEvent());
        day4.setOnClickListener(new ClickEvent());
        day5.setOnClickListener(new ClickEvent());
        day6.setOnClickListener(new ClickEvent());
        day7.setOnClickListener(new ClickEvent());
        day8.setOnClickListener(new ClickEvent());
        day9.setOnClickListener(new ClickEvent());
        day10.setOnClickListener(new ClickEvent());
        day11.setOnClickListener(new ClickEvent());
        day12.setOnClickListener(new ClickEvent());
        day13.setOnClickListener(new ClickEvent());
        day14.setOnClickListener(new ClickEvent());
        day15.setOnClickListener(new ClickEvent());
        day16.setOnClickListener(new ClickEvent());
        day17.setOnClickListener(new ClickEvent());
        day18.setOnClickListener(new ClickEvent());
        day19.setOnClickListener(new ClickEvent());
        day20.setOnClickListener(new ClickEvent());
        day21.setOnClickListener(new ClickEvent());
        day22.setOnClickListener(new ClickEvent());
        day23.setOnClickListener(new ClickEvent());
        day24.setOnClickListener(new ClickEvent());
        day25.setOnClickListener(new ClickEvent());
        day26.setOnClickListener(new ClickEvent());
        day27.setOnClickListener(new ClickEvent());
        day28.setOnClickListener(new ClickEvent());
        day29.setOnClickListener(new ClickEvent());
        day30.setOnClickListener(new ClickEvent());
        day31.setOnClickListener(new ClickEvent());
        day32.setOnClickListener(new ClickEvent());
        day33.setOnClickListener(new ClickEvent());
        day34.setOnClickListener(new ClickEvent());
        day35.setOnClickListener(new ClickEvent());
        day36.setOnClickListener(new ClickEvent());
        day37.setOnClickListener(new ClickEvent());
        day38.setOnClickListener(new ClickEvent());
        day39.setOnClickListener(new ClickEvent());
        day40.setOnClickListener(new ClickEvent());
        day41.setOnClickListener(new ClickEvent());
        day42.setOnClickListener(new ClickEvent());
        day43.setOnClickListener(new ClickEvent());
        day44.setOnClickListener(new ClickEvent());
        day45.setOnClickListener(new ClickEvent());
        day46.setOnClickListener(new ClickEvent());
        day47.setOnClickListener(new ClickEvent());
        day48.setOnClickListener(new ClickEvent());
        day49.setOnClickListener(new ClickEvent());
        day50.setOnClickListener(new ClickEvent());
        day51.setOnClickListener(new ClickEvent());

        // day 누적 테스트
        DayTest_btn = (Button) findViewById(R.id.selectDay_Test_btn);
        DayTest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnglishActivity.this, WordTestSelectActivity.class);
                startActivity(intent);
            }
        });

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

    private void link_Button() {
        dayMap = new HashMap<>();
        prefs = getSharedPreferences("PaceMaker", MODE_PRIVATE);

        day1 = findViewById(R.id.day01);
        day2 = findViewById(R.id.day02);
        day3 = findViewById(R.id.day03);
        day4 = findViewById(R.id.day04);
        day5 = findViewById(R.id.day05);
        day6 = findViewById(R.id.day06);
        day7 = findViewById(R.id.day07);
        day8 = findViewById(R.id.day08);
        day9 = findViewById(R.id.day09);
        day10 = findViewById(R.id.day10);
        day11 = findViewById(R.id.day11);
        day12 = findViewById(R.id.day12);
        day13 = findViewById(R.id.day13);
        day14 = findViewById(R.id.day14);
        day15 = findViewById(R.id.day15);
        day16 = findViewById(R.id.day16);
        day17 = findViewById(R.id.day17);
        day18 = findViewById(R.id.day18);
        day19 = findViewById(R.id.day19);
        day20 = findViewById(R.id.day20);
        day21 = findViewById(R.id.day21);
        day22 = findViewById(R.id.day22);
        day23 = findViewById(R.id.day23);
        day24 = findViewById(R.id.day24);
        day25 = findViewById(R.id.day25);
        day26 = findViewById(R.id.day26);
        day27 = findViewById(R.id.day27);
        day28 = findViewById(R.id.day28);
        day29 = findViewById(R.id.day29);
        day30 = findViewById(R.id.day30);
        day31 = findViewById(R.id.day31);
        day32 = findViewById(R.id.day32);
        day33 = findViewById(R.id.day33);
        day34 = findViewById(R.id.day34);
        day35 = findViewById(R.id.day35);
        day36 = findViewById(R.id.day36);
        day37 = findViewById(R.id.day37);
        day38 = findViewById(R.id.day38);
        day39 = findViewById(R.id.day39);
        day40 = findViewById(R.id.day40);
        day41 = findViewById(R.id.day41);
        day42 = findViewById(R.id.day42);
        day43 = findViewById(R.id.day43);
        day44 = findViewById(R.id.day44);
        day45 = findViewById(R.id.day45);
        day46 = findViewById(R.id.day46);
        day47 = findViewById(R.id.day47);
        day48 = findViewById(R.id.day48);
        day49 = findViewById(R.id.day49);
        day50 = findViewById(R.id.day50);
        day51 = findViewById(R.id.day51);

        dayMap.put("day1", day1);
        dayMap.put("day2", day2);
        dayMap.put("day3", day3);
        dayMap.put("day4", day4);
        dayMap.put("day5", day5);
        dayMap.put("day6", day6);
        dayMap.put("day7", day7);
        dayMap.put("day8", day8);
        dayMap.put("day9", day9);
        dayMap.put("day10", day10);
        dayMap.put("day11", day11);
        dayMap.put("day12", day12);
        dayMap.put("day13", day13);
        dayMap.put("day14", day14);
        dayMap.put("day15", day15);
        dayMap.put("day16", day16);
        dayMap.put("day17", day17);
        dayMap.put("day18", day18);
        dayMap.put("day19", day19);
        dayMap.put("day20", day20);
        dayMap.put("day21", day21);
        dayMap.put("day22", day22);
        dayMap.put("day23", day23);
        dayMap.put("day24", day24);
        dayMap.put("day25", day25);
        dayMap.put("day26", day26);
        dayMap.put("day27", day27);
        dayMap.put("day28", day28);
        dayMap.put("day29", day29);
        dayMap.put("day30", day30);
        dayMap.put("day31", day31);
        dayMap.put("day32", day32);
        dayMap.put("day33", day33);
        dayMap.put("day34", day34);
        dayMap.put("day35", day35);
        dayMap.put("day36", day36);
        dayMap.put("day37", day37);
        dayMap.put("day38", day38);
        dayMap.put("day39", day39);
        dayMap.put("day40", day40);
        dayMap.put("day41", day41);
        dayMap.put("day42", day42);
        dayMap.put("day43", day43);
        dayMap.put("day44", day44);
        dayMap.put("day45", day45);
        dayMap.put("day46", day46);
        dayMap.put("day47", day47);
        dayMap.put("day48", day48);
        dayMap.put("day49", day49);
        dayMap.put("day50", day50);
        dayMap.put("day51", day51);

        for (int i = 1; i <= 51; i++) {
            if (prefs.getBoolean("day" + String.valueOf(i), false)) {
                dayMap.get("day" + String.valueOf(i)).setBackground(ContextCompat.getDrawable(this, R.drawable.dday_counter));
                dayMap.get("day" + String.valueOf(i)).setTextColor(Color.parseColor("#FFA9A9A9"));
            }
        }
    }

    class ClickEvent implements Button.OnClickListener {
        private Intent intent = new Intent(getApplicationContext(), WordActivity.class);

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.day01:
                    intent.putExtra("day", "day1");
                    break;
                case R.id.day02:
                    intent.putExtra("day", "day2");
                    break;
                case R.id.day03:
                    intent.putExtra("day", "day3");
                    break;
                case R.id.day04:
                    intent.putExtra("day", "day4");
                    break;
                case R.id.day05:
                    intent.putExtra("day", "day5");
                    break;
                case R.id.day06:
                    intent.putExtra("day", "day6");
                    break;
                case R.id.day07:
                    intent.putExtra("day", "day7");
                    break;
                case R.id.day08:
                    intent.putExtra("day", "day8");
                    break;
                case R.id.day09:
                    intent.putExtra("day", "day9");
                    break;
                case R.id.day10:
                    intent.putExtra("day", "day10");
                    break;
                case R.id.day11:
                    intent.putExtra("day", "day11");
                    break;
                case R.id.day12:
                    intent.putExtra("day", "day12");
                    break;
                case R.id.day13:
                    intent.putExtra("day", "day13");
                    break;
                case R.id.day14:
                    intent.putExtra("day", "day14");
                    break;
                case R.id.day15:
                    intent.putExtra("day", "day15");
                    break;
                case R.id.day16:
                    intent.putExtra("day", "day16");
                    break;
                case R.id.day17:
                    intent.putExtra("day", "day17");
                    break;
                case R.id.day18:
                    intent.putExtra("day", "day18");
                    break;
                case R.id.day19:
                    intent.putExtra("day", "day19");
                    break;
                case R.id.day20:
                    intent.putExtra("day", "day20");
                    break;
                case R.id.day21:
                    intent.putExtra("day", "day21");
                    break;
                case R.id.day22:
                    intent.putExtra("day", "day22");
                    break;
                case R.id.day23:
                    intent.putExtra("day", "day23");
                    break;
                case R.id.day24:
                    intent.putExtra("day", "day24");
                    break;
                case R.id.day25:
                    intent.putExtra("day", "day25");
                    break;
                case R.id.day26:
                    intent.putExtra("day", "day26");
                    break;
                case R.id.day27:
                    intent.putExtra("day", "day27");
                    break;
                case R.id.day28:
                    intent.putExtra("day", "day28");
                    break;
                case R.id.day29:
                    intent.putExtra("day", "day29");
                    break;
                case R.id.day30:
                    intent.putExtra("day", "day30");
                    break;
                case R.id.day31:
                    intent.putExtra("day", "day31");
                    break;
                case R.id.day32:
                    intent.putExtra("day", "day32");
                    break;
                case R.id.day33:
                    intent.putExtra("day", "day33");
                    break;
                case R.id.day34:
                    intent.putExtra("day", "day34");
                    break;
                case R.id.day35:
                    intent.putExtra("day", "day35");
                    break;
                case R.id.day36:
                    intent.putExtra("day", "day36");
                    break;
                case R.id.day37:
                    intent.putExtra("day", "day37");
                    break;
                case R.id.day38:
                    intent.putExtra("day", "day38");
                    break;
                case R.id.day39:
                    intent.putExtra("day", "day39");
                    break;
                case R.id.day40:
                    intent.putExtra("day", "day40");
                    break;
                case R.id.day41:
                    intent.putExtra("day", "day41");
                    break;
                case R.id.day42:
                    intent.putExtra("day", "day42");
                    break;
                case R.id.day43:
                    intent.putExtra("day", "day43");
                    break;
                case R.id.day44:
                    intent.putExtra("day", "day44");
                    break;
                case R.id.day45:
                    intent.putExtra("day", "day45");
                    break;
                case R.id.day46:
                    intent.putExtra("day", "day46");
                    break;
                case R.id.day47:
                    intent.putExtra("day", "day47");
                    break;
                case R.id.day48:
                    intent.putExtra("day", "day48");
                    break;
                case R.id.day49:
                    intent.putExtra("day", "day49");
                    break;
                case R.id.day50:
                    intent.putExtra("day", "day50");
                    break;
                case R.id.day51:
                    intent.putExtra("day", "day51");
                    break;
            }
            startActivity(intent);
        }
    }
}
