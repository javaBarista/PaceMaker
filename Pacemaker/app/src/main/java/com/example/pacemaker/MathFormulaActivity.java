package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MathFormulaActivity extends AppCompatActivity {
    TextView formula_name;
    PhotoView formula_img;
    Bitmap bmImg;
    getImage task;
    String imgUrl;
    HashMap mathUrlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_formula);

        formula_name = (TextView) findViewById(R.id.formula_name);
        formula_img = findViewById(R.id.formula_img);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("formula_name");
        String category = intent.getExtras().getString("category_name");
        setTitle(category);
        formula_name.setText(name);

        mathUrlList = MathFormulaHashMap.getData();
        imgUrl = mathUrlList.get(name).toString();
        task = new getImage();
        task.execute("http://nobles1030.cafe24.com/mathMaterial/"+imgUrl);
    }

    private class getImage extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);
            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg;
        }
        protected void onPostExecute(Bitmap img){
            formula_img.setImageBitmap(bmImg);
        }
    }
}
