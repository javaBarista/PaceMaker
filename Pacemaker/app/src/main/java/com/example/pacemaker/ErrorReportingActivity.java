package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class ErrorReportingActivity extends AppCompatActivity{

    private SharedPreferences pref;
    private ArrayList arrList = new ArrayList<String>();
    private Spinner errspin;
    private EditText error_body;
    private Button send;
    private Intent getIntent;
    private Bundle bundle;
    private MailSender mailSender = new MailSender("20166439.sw.cau@gmail.com", "your password");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_reporting);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        getIntent = getIntent();
        bundle = getIntent.getExtras();

        errspin = findViewById(R.id.error_activity_spin);
        error_body = findViewById(R.id.error_body);
        send = findViewById(R.id.error_sendBtn);

        makeList();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrList);
        errspin.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler mHandler = new Handler();
                new Thread(new Runnable(){
                    @Override
                    public void run(){
                        try {
                            mailSender.sendMail(pref.getString("id", ""), bundle.getString("mail") + "@" + bundle.getString("address"), errspin.getSelectedItem().toString(), error_body.getText().toString());
                        } catch (Exception e) { }
                        mHandler.post(new Runnable(){
                            @Override
                            public void run(){
                                Intent reIntent = new Intent(getApplicationContext(), ErrorReportingActivity.class);
                                startActivity(reIntent);
                                finish();
                                Toast.makeText(getApplicationContext(), "전송완료.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();

            }
        });
    }

    void makeList(){
        arrList.add("로그인");
        arrList.add("회원가입");
        arrList.add("캘린더");
        arrList.add("캘린더 리스트 관리");
        arrList.add("마이 페이지");
        arrList.add("홈 화면");
        arrList.add("영단어");
        arrList.add("수학");
        arrList.add("번역기");
        arrList.add("모집요강");
        arrList.add("경쟁률");
        arrList.add("기출문제");
        arrList.add("모의고사");
        arrList.add("Q&A 게시판");
        arrList.add("기타");
    }
}
