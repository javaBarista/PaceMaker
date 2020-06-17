package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.Request;

public class AnswerActivity extends AppCompatActivity {

    private TextView title;
    private TextView date;
    private TextView register;
    private PhotoView photoView;
    private PhotoView reading;
    private EditText my_ans;
    private ImageView uploadBtn;
    private Intent getIntent;
    private Bundle bundle;
    private RecyclerView mRecyclerView;
    private AnswerListAdapter mAdapter;
    private GetAnswer getAnswer = new GetAnswer();
    private SharedPreferences pref;
    private ArrayList<AnswerForm> mList = new ArrayList<>();
    private String num;
    private String user;
    private String year;
    private String testNum;
    private String name;
    private String uploadDate;
    private String url;
    private long expires;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        pref =  PreferenceManager.getDefaultSharedPreferences(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIntent = getIntent();
        bundle = getIntent.getExtras();
        num = bundle.getString("num");
        user = bundle.getString("id");
        year = bundle.getString("year");
        name = bundle.getString("name");
        testNum = bundle.getString("testNum");
        uploadDate = bundle.getString("date");

        title = findViewById(R.id.answer_title);
        date = findViewById(R.id.answer_date);
        register = findViewById(R.id.answer_register);
        photoView = findViewById(R.id.answer_problem);
        reading = findViewById(R.id.answer_problem_text);
        mRecyclerView = findViewById(R.id.answer_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        my_ans = findViewById(R.id.answer_answer);
        uploadBtn = findViewById(R.id.answer_regist_btn);

        getAnswer.execute();

        register.setText(user);
        title.setText(year + " " + name + " " + testNum + "번");
        date.setText(uploadDate);

        OkHttpClient photoEvent = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("year", year)
                .add("college", name)
                .add("num", testNum)
                .build();
        photoEvent.newCall(new Request.Builder().url("https://nobles1030.cafe24.com/request_testUrl.php").post(body).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = response.body().string();
                            JsonElement jsonElement =  new JsonParser().parse(result);
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                                url = jsonObject.get("address").toString().replaceAll("\\/", "/").replaceAll("\"", "");
                                final String readtext = jsonObject.get("main_text").toString().replaceAll("\\/", "/").replaceAll("\"", "");
                                if(readtext.length() > 10){
                                    reading.setVisibility(View.VISIBLE);
                                    final Handler readingHandler = new Handler();

                                    new Thread(new Runnable(){
                                        @Override
                                        public void run(){
                                            Bitmap bitmap2 = null;
                                            try {
                                                // Download Image from URL
                                                InputStream input = new java.net.URL(readtext).openStream();
                                                // Decode Bitmap
                                                bitmap2 = BitmapFactory.decodeStream(input);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            final Bitmap finalBitmap2 = bitmap2;
                                            readingHandler.post(new Runnable(){
                                                @Override
                                                public void run(){
                                                    reading.setImageBitmap(finalBitmap2);
                                                }
                                            });
                                        }
                                    }).start();
                                }
                                final Handler mHandler = new Handler();

                                new Thread(new Runnable(){
                                    @Override
                                    public void run(){
                                        Bitmap bitmap = null;
                                        try {
                                            // Download Image from URL
                                            InputStream input = new java.net.URL(url).openStream();
                                            // Decode Bitmap
                                            bitmap = BitmapFactory.decodeStream(input);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        final Bitmap finalBitmap = bitmap;
                                        mHandler.post(new Runnable(){
                                            @Override
                                            public void run(){
                                                photoView.setImageBitmap(finalBitmap);
                                            }
                                        });
                                    }
                                }).start();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String bodyText = my_ans.getText().toString();
                expires = pref.getLong("No_use_cvEx", 0L);
                if(System.currentTimeMillis() < expires){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    Date timeInDate = new Date(expires);
                    String timeInFormat = sdf.format(timeInDate);

                    Toast.makeText(getApplicationContext(), pref.getString("id", "") + " 님은 현재 누적신고 5회 이상으로\n" + timeInFormat + " 까지 댓글 이용이 불가능합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(!pref.getBoolean(year + name + "complete", false)) Toast.makeText(getApplicationContext(), "먼저 해당 시험을 응시하셔야 댓글을 입력 할 수 있습니다.", Toast.LENGTH_SHORT).show();
                else if(my_ans.getText().toString().length() < 1)   Toast.makeText(getApplicationContext(), "댓글을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                else{
                    final Handler mHandler = new Handler();
                    final AnswerForm[] tmp = new AnswerForm[1];
                    new Thread(new Runnable(){
                        @Override
                        public void run(){
                            OkHttpClient uploadEvent = new OkHttpClient();
                            RequestBody body = new FormBody.Builder()
                                    .add("qnum", num)
                                    .add("id", pref.getString("id", ""))
                                    .add("body", my_ans.getText().toString())
                                    .build();

                            uploadEvent.newCall(new Request.Builder().url("https://nobles1030.cafe24.com/upload_answer.php").post(body).build()).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) { }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String result = response.body().string();
                                    Log.d("is it wrong=", result);
                                    if(result.contains("success")) {
                                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                                        tmp[0] = new AnswerForm(pref.getString("id", ""), bodyText, format.format(System.currentTimeMillis()));
                                        mList.add(tmp[0]);
                                    }
                                }
                            });
                            mHandler.post(new Runnable(){
                                @Override
                                public void run(){
                                    mAdapter = new AnswerListAdapter(mList, num, AnswerActivity.this);
                                    mRecyclerView.setAdapter(mAdapter);
                                    my_ans.setText(null);
                                    Toast.makeText(getApplicationContext(), "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });
    }

    private class GetAnswer extends AsyncTask<Void, Void, AnswerForm[]> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected AnswerForm[] doInBackground(Void... voids) {

            String url = "http://nobles1030.cafe24.com/request_AnswerBoard.php";
            RequestBody body = new FormBody.Builder()
                    .add("num", num)
                    .build();
            Request request = new Request.Builder().url(url).post(body).build();

            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream());

                AnswerForm[] posts = gson.fromJson(rootObject, AnswerForm[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(AnswerForm[] result) {
            if(result == null) return;
            if(result.length > 0){
                mList.clear();
                for (AnswerForm post: result){
                    mList.add(post);
                }
                //Adapter setting
                mAdapter = new AnswerListAdapter(mList, num, AnswerActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getAnswer.cancel(true);
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
