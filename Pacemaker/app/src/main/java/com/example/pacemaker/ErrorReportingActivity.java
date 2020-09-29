package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MenuItem;
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
    private SendMailTask sendMailTask;
    private MailSender mailSender = new MailSender("20166439.sw.cau@gmail.com", "trilogy!0628");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_reporting);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                sendMailTask = new SendMailTask();
                sendMailTask.execute();
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

    private class SendMailTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog( ErrorReportingActivity.this);
        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("전송중입니다..");
            // show dialog
            asyncDialog.show();
            new Thread(new Runnable(){
                @Override
                public void run(){
                    try {
                        mailSender.sendMail(pref.getString("id", "") +"님의 " + errspin.getSelectedItem().toString() + " 에 대한 오류 문의 입니다. ", "20166439.sw.cau@gmail.com", "20166439.sw.cau@gmail.com", error_body.getText().toString());
                    } catch (Exception e) { }
                }
            }).start();
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                for (int i = 0; i < 5; i++) {
                    //asyncDialog.setProgress(i * 30);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            asyncDialog.dismiss();
            error_body.setText(null);
            errspin.setSelection(0);
            Toast.makeText(getApplicationContext(), "전송완료.", Toast.LENGTH_SHORT).show();
        }
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
