package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
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
    private Intent getIntent;
    private Bundle bundle;
    private RecyclerView mRecyclerView;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String num;
    private String user;
    private String year;
    private String testNum;
    private String name;
    private String uploadDate;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        pref =  PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();

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
