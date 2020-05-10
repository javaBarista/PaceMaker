package com.example.pacemaker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

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

public class CompetitionActivity extends AppCompatActivity {

    private Spinner year;
    private Spinner college;
    private Button send;
    //private ImageView competitonImg;
    private String url;
    private PhotoView competitonImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        year = findViewById(R.id.yearSpin);
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(yearAdapter);
        college = findViewById(R.id.collegeSpin);
        ArrayAdapter collegeAdapter = ArrayAdapter.createFromResource(this, R.array.college, android.R.layout.simple_spinner_dropdown_item);
        college.setAdapter(collegeAdapter);
        send = findViewById(R.id.sendBtn);
        //competitonImg = findViewById(R.id.competitionImg);
        competitonImg = findViewById(R.id.competitionImg);

        send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                OkHttpClient ClickEvent = new OkHttpClient();

                RequestBody body = new FormBody.Builder()
                        .add("year", year.getSelectedItem().toString())
                        .add("college", college.getSelectedItem().toString())
                        .build();
                ClickEvent.newCall(new Request.Builder().url("https://nobles1030.cafe24.com/requestCompetition.php").post(body).build()).enqueue(new Callback() {

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
                                    Log.d("compe = ", result);

                                    if(result.contains("success")){
                                        JsonElement jsonElement =  new JsonParser().parse(result.substring(result.indexOf("{"), result.indexOf("}") + 1));
                                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                                        url = jsonObject.get("Address").toString().replaceAll("\\/", "/").replaceAll("\"", "");

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
                                                        competitonImg.setImageBitmap(finalBitmap);
                                                    }
                                                });
                                            }
                                        }).start();
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case android.R.id.home:
                // TODO : process the click event for action_search item.
                onBackPressed();
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
