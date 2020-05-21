package com.example.pacemaker.ui.word;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pacemaker.R;

import java.net.Proxy;

public class WordTestFragment extends Fragment {
    private String num;
    private String word;
    private String mean1;
    private String mean2;
    private String mean3;
    private String mean4;
    private String answer;
    private String day;
    RadioGroup radioGroup;

    public WordTestFragment() {
    }

    public static WordTestFragment newInstance(String num, String word, String mean1, String mean2, String mean3, String mean4, String answer, String day) {
        WordTestFragment fragment = new WordTestFragment();
        Bundle args = new Bundle();
        args.putString("num", num);
        args.putString("word", word);
        args.putString("mean1", mean1);
        args.putString("mean2", mean2);
        args.putString("mean3", mean3);
        args.putString("mean4", mean4);
        args.putString("answer", answer);
        args.putString("day", day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            num = getArguments().getString("num", "");
            word = getArguments().getString("word", "");
            mean1 = getArguments().getString("mean1", "");
            mean2 = getArguments().getString("mean2", "");
            mean3 = getArguments().getString("mean3", "");
            mean4 = getArguments().getString("mean4", "");
            answer = getArguments().getString("answer","");
            day = getArguments().getString("day", "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_test, container, false);

        TextView numtv = (TextView) view.findViewById(R.id.test_number);
        TextView wordtv = (TextView) view.findViewById(R.id.test_word);
        final TextView checktv = (TextView) view.findViewById(R.id.checkTv);
        final ImageView checkImg = (ImageView) view.findViewById(R.id.checkImg);
        Button testDone = (Button) view.findViewById(R.id.test_done);

        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        final RadioButton mean1tv = (RadioButton) view.findViewById(R.id.test_mean1);
        final RadioButton mean2tv = (RadioButton) view.findViewById(R.id.test_mean2);
        final RadioButton mean3tv = (RadioButton) view.findViewById(R.id.test_mean3);
        final RadioButton mean4tv = (RadioButton) view.findViewById(R.id.test_mean4);

        numtv.setText(num);
        wordtv.setText(word);
        mean1tv.setText(mean1);
        mean2tv.setText(mean2);
        mean3tv.setText(mean3);
        mean4tv.setText(mean4);

        mean1tv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (mean1.equals(answer)) {
                        mean1tv.setTextColor(Color.parseColor("#933FE2"));
//                        Toast toast = Toast.makeText(getContext(), "정답입니다", Toast.LENGTH_SHORT);
//                        ViewGroup group = (ViewGroup) toast.getView();
//                        TextView msg = (TextView) group.getChildAt(0);
//                        msg.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
//                        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
//                        toast.show();
                        checktv.setText("정답입니다");
                        checkImg.setImageResource(R.drawable.correct_button);
                    } else {
                        checktv.setText("오답입니다");
                        checkImg.setImageResource(R.drawable.xmark);
                    }
                    checkImg.setVisibility(View.VISIBLE);
                    checktv.setVisibility(View.VISIBLE);
                }
            }
        });
        mean2tv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (mean2tv.getText().toString().equals(answer)) {
                        mean2tv.setTextColor(Color.parseColor("#933FE2"));
                        checktv.setText("정답입니다");
                        checkImg.setImageResource(R.drawable.correct_button);
                    } else {
                        checktv.setText("오답입니다");
                        checkImg.setImageResource(R.drawable.xmark);
                    }
                    checkImg.setVisibility(View.VISIBLE);
                    checktv.setVisibility(View.VISIBLE);
                }
            }
        });
        mean3tv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (mean3tv.getText().toString().equals(answer)) {
                        mean3tv.setTextColor(Color.parseColor("#933FE2"));
                        checkImg.setImageResource(R.drawable.correct_button);
                        checktv.setText("정답입니다");
                    } else {
                        checkImg.setImageResource(R.drawable.xmark);
                        checktv.setText("오답입니다");
                    }
                    checkImg.setVisibility(View.VISIBLE);
                    checktv.setVisibility(View.VISIBLE);
                }
            }
        });
        mean4tv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (mean4tv.getText().toString().equals(answer)) {
                        mean4tv.setTextColor(Color.parseColor("#933FE2"));
                        checkImg.setImageResource(R.drawable.correct_button);
                        checktv.setText("정답입니다");
                    } else {
                        checkImg.setImageResource(R.drawable.xmark);
                        checktv.setText("오답입니다");
                    }
                    checkImg.setVisibility(View.VISIBLE);
                    checktv.setVisibility(View.VISIBLE);
                }
            }
        });

        if (num.equals("5/5")) {
            testDone.setVisibility(View.VISIBLE);
        }
        testDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertConfirm = new AlertDialog.Builder(getContext());
                alertConfirm.setMessage("학습을 완료합니다");
                alertConfirm.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //SharedPreferences
                        SharedPreferences sf = getActivity().getSharedPreferences("PaceMaker", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sf.edit();
                        editor.putBoolean("day"+day, true);
                        editor.commit();

                        getActivity().finish();
                    }
                });
                alertConfirm.setNegativeButton("취소", null);
                AlertDialog alert = alertConfirm.create();
                alert.show();
            }
        });

        return view;
    }

}
