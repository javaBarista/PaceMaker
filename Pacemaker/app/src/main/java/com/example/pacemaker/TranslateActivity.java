package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateActivity extends AppCompatActivity {

    private TextView trans_from;
    private TextView trans_to;
    private ImageView swap;
    private EditText fromTxt;
    private Button transBtn;
    private TextView toTxt;
    private String source = "ko";
    private String target = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        trans_from = findViewById(R.id.trans_from);
        trans_to = findViewById(R.id.trans_to);
        swap = findViewById(R.id.trans_comp);
        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = trans_from.getText().toString();
                trans_from.setText(trans_to.getText().toString());
                trans_to.setText(tmp);

                tmp = source;
                source = target;
                target = tmp;
            }
        });

        fromTxt = findViewById(R.id.fromTxt);
        toTxt = findViewById(R.id.toTxt);
        transBtn = findViewById(R.id.trans_btn);
        transBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute(fromTxt.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.english_menu, menu);
        MenuItem trans = menu.findItem(R.id.action_trans);
        trans.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case android.R.id.home:
                // TODO : process the click event for action_search item.
                onBackPressed();
                return true ;
            case R.id.action_word:
                Intent wordIntent = new Intent(this, EnglishActivity.class);
                startActivity(wordIntent);
                finish();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class BackgroundTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... str) {
            String inputText = str[0];
            String clientId = "hMHq___3norAShsDP1Sj";
            String clientSecret="FAvNVxly_I";
            String result = "";
            try{
                String text = URLEncoder.encode(inputText, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-naver-Client-Secret", clientSecret);

                String postParams = "source=" + source + "&target=" + target +"&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                BufferedReader br =  new BufferedReader(new InputStreamReader(responseCode == 200 ? con.getInputStream() : con.getErrorStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();
                while((inputLine = br.readLine()) != null)
                    response.append(inputLine);
                br.close();
                result = response.toString();
            }catch (Exception e){
                result="번역실패";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(s);
            String res = element.getAsJsonObject().get("message").getAsJsonObject().get("result").getAsJsonObject().get("translatedText").getAsString();
            toTxt.setText(res);
        }

    }
}
