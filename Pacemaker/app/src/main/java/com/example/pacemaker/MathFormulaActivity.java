package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.like.LikeButton;
import com.like.OnLikeListener;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.InputStream;
import java.util.HashMap;

public class MathFormulaActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private TextView formula_name;
    private PhotoView formula_img;
    private String imgUrl = "http://nobles1030.cafe24.com/mathMaterial/";
    private HashMap mathUrlList;
    private LikeButton starCheck;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_formula);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = pref.edit();

        formula_name = findViewById(R.id.formula_name);
        formula_img = findViewById(R.id.formula_img);

        Intent intent = getIntent();
        final String name = intent.getExtras().getString("formula_name");
        String category = intent.getExtras().getString("category_name");
        setTitle(category);
        formula_name.setText(name);

        mathUrlList = MathFormulaHashMap.getData();
        imgUrl += mathUrlList.get(name).toString();
        final Handler mHandler = new Handler();
        new Thread(new Runnable(){
            @Override
            public void run(){
                Bitmap bitmap = null;
                try {
                    // Download Image from URL
                    InputStream input = new java.net.URL(imgUrl).openStream();
                    // Decode Bitmap
                    bitmap = BitmapFactory.decodeStream(input);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final Bitmap finalBitmap = bitmap;
                mHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        formula_img.setImageBitmap(finalBitmap);
                    }
                });
            }
        }).start();

        starCheck = findViewById(R.id.like_check_math);
        starCheck.setCircleEndColorRes(R.color.colorPrimary);
        starCheck.setLiked(pref.getBoolean(name, false));

       final String arr = "{" +"\"name\":"+"\""+name+"\""+ "}";

        starCheck.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                try {
                    String tmp = pref.getString("favorite_math", null);
                    if(tmp != null) Log.d("favorite math is =", tmp);
                    jsonArray = tmp != null ? new JSONArray(tmp) : new JSONArray() ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(arr);
                Log.d("json is = ", String.valueOf(jsonArray));
                editor.putString("favorite_math", String.valueOf(jsonArray));
                editor.putBoolean(name, true);
                editor.commit();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                try {
                    String tmp = pref.getString("favorite_math", null);
                    if(tmp != null)Log.d("favorite math is =", tmp);
                    jsonArray = tmp != null ? new JSONArray(tmp) : new JSONArray() ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int i = 0;
                while(i < jsonArray.length()){
                    try {
                        Log.d("json len is :", jsonArray.getString(i));
                        JsonElement jsonElement = new JsonParser().parse(jsonArray.getString(i));
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if(name.equals(String.valueOf(jsonObject.get("name")).replace("\"", ""))) break;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                jsonArray.remove(i);
                Log.d("remove json is = ", String.valueOf(jsonArray));
                editor.putString("favorite_math", String.valueOf(jsonArray));
                editor.putBoolean(name, false);
                editor.commit();
            }
        });
    }
}
