package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FindActivity extends AppCompatActivity {

    private Button findId;
    private Button findPw;
    private EditText idTxt;
    private EditText name;
    private EditText mail;
    private Spinner mailSpin;
    private ListView idList;
    private Button findBtn;
    private SendMailTask sendMailTask;
    private MailSender mailSender = new MailSender("20166439.sw.cau@gmail.com", "trilogy!0628");
    private String tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mailSpin = findViewById(R.id.find_mailList);
        ArrayAdapter mailAdapter = ArrayAdapter.createFromResource(this, R.array.mail, android.R.layout.simple_spinner_dropdown_item);
        mailSpin.setAdapter(mailAdapter);

        idTxt = findViewById(R.id.find_id_txt);
        findId = findViewById(R.id.find_ID_Btn);
        findPw = findViewById(R.id.find_PW_Btn);
        name = findViewById(R.id.find_nameText);
        mail = findViewById(R.id.find_mailText);
        idList = findViewById(R.id.idList);

        findId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findId.setTextColor(Color.parseColor("#FFFFFFFF"));
                findId.setBackgroundResource(R.drawable.custom_btn);
                findPw.setTextColor(Color.parseColor("#FF000000"));
                findPw.setBackgroundResource(R.drawable.custom_btn2);
                idTxt.setVisibility(View.GONE);
            }
        });

        findPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findId.setTextColor(Color.parseColor("#FF000000"));
                findId.setBackgroundResource(R.drawable.custom_btn2);
                findPw.setTextColor(Color.parseColor("#FFFFFFFF"));
                findPw.setBackgroundResource(R.drawable.custom_btn);
                idTxt.setVisibility(View.VISIBLE);
            }
        });

        findBtn = findViewById(R.id.findfindBtn);
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient event = new OkHttpClient();

                if(idTxt.getText().toString().length() > 1) {
                    RequestBody body = new FormBody.Builder()
                            .add("id", idTxt.getText().toString())
                            .build();
                    event.newCall(new Request.Builder().url("http://nobles1030.cafe24.com/find_myPW.php").post(body).build()).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) { }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            new Handler(getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String result = response.body().string();
                                        Log.d("find PW is = ", result);

                                        if(result.contains("failure"))    Toast.makeText(getApplicationContext(), "Check your ID", Toast.LENGTH_LONG).show();
                                        else{
                                            tmp = result.replaceAll("\"", "").replace("{", "").replace("}", "");
                                            sendMailTask = new SendMailTask();
                                            sendMailTask.execute();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                }
                else{
                    idList.setVisibility(View.VISIBLE);

                    RequestBody body = new FormBody.Builder()
                            .add("name", name.getText().toString())
                            .add("mail", mail.getText().toString() + "@" + mailSpin.getSelectedItem().toString())
                            .build();
                    event.newCall(new Request.Builder().url("http://nobles1030.cafe24.com/find_myID.php").post(body).build()).enqueue(new Callback() {

                        @Override
                        public void onFailure(Call call, IOException e) { }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            new Handler(getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String result = response.body().string();
                                        Log.d("find ID is = ", result);

                                        if (result.contains("failure"))
                                            Toast.makeText(FindActivity.this, "Check your Name or Mail", Toast.LENGTH_LONG).show();
                                        else {
                                            ArrayList<String> al = new ArrayList<>();
                                            String s = result;
                                            JSONArray jsonArray = new JSONArray(s);

                                            for(int i = 0 ; i < jsonArray.length(); i++) {
                                                JsonElement jsonElement = new JsonParser().parse(jsonArray.getString(i));
                                                JsonObject jsonObject = jsonElement.getAsJsonObject();
                                                al.add(String.valueOf(jsonObject.get("id")).replaceAll("\"", ""));
                                            }

                                            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, al);
                                            idList.setAdapter(adapter);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private class SendMailTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(FindActivity.this);
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
                        mailSender.sendMail(name.getText().toString() + "님의 임시 비밀번호 입니다.", "20166439.sw.cau@gmail.com", mail.getText().toString() + "@" + mailSpin.getSelectedItem().toString(), "임시 비밀번호는 : " + tmp + " 입니다.\n" + "MyPage에서 비밀번호를 변경해서 사용해 주세요");
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
            idTxt.setText(null);
            name.setText(null);
            mailSpin.setSelection(0);
            mail.setText(null);
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
