package com.example.pacemaker.ui.test;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import com.example.pacemaker.R;
import com.github.chrisbanes.photoview.PhotoView;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.InputStream;

public class TestFragment extends Fragment {
    private SharedPreferences pref;
    private String address;
    private String text;
    private boolean isOpen;
    private String part;
    private int answer;
    private JSONArray jsonArray;

    public static TestFragment newInstance(String address, String text, Boolean isOpen, String part, int answer) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString("address", address);
        args.putString("text", text);
        args.putBoolean("isOpen", isOpen);
        args.putString("part", part);
        args.putInt("answer", answer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        address = getArguments().getString("address", "");
        text = getArguments().getString("text", "");
        isOpen = getArguments().getBoolean("isOpen");
        part = getArguments().getString("part", "");
        answer = getArguments().getInt("answer");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("ResourceType") View view = inflater.inflate(R.xml.fragment_test, container, false);
        final SharedPreferences.Editor editor = pref.edit();

        final PhotoView question = view.findViewById(R.id.test_question);
        RadioGroup select_ans = view.findViewById(R.id.ans_radio);
        Button reading = view.findViewById(R.id.reading_open);

        final Handler mHandler = new Handler();
        new Thread(new Runnable(){
            @Override
            public void run(){
                Bitmap bitmap = null;
                try {
                    // Download Image from URL
                    InputStream input = new java.net.URL(address).openStream();
                    // Decode Bitmap
                    bitmap = BitmapFactory.decodeStream(input);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final Bitmap finalBitmap = bitmap;
                mHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        question.setImageBitmap(finalBitmap);
                    }
                });
            }
        }).start();

        if(isOpen){
            reading.setVisibility(View.VISIBLE);
            reading.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.xml.test_reading);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    final PhotoView testReading = dialog.findViewById(R.id.test_reading);
                    final Handler readingHandler = new Handler();
                    new Thread(new Runnable(){
                        @Override
                        public void run(){
                            Bitmap bitmap = null;
                            try {
                                // Download Image from URL
                                InputStream input = new java.net.URL(text).openStream();
                                // Decode Bitmap
                                bitmap = BitmapFactory.decodeStream(input);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            final Bitmap readingBitmap = bitmap;
                            readingHandler.post(new Runnable(){
                                @Override
                                public void run(){
                                   testReading.setImageBitmap(readingBitmap);
                                }
                            });
                        }
                    }).start();
                    dialog.show();
                }
            });
        }

        select_ans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.test_select1:
                        break;

                }
            }
        });

        return view;
    }
}
