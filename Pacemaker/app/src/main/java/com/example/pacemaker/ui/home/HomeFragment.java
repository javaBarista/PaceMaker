package com.example.pacemaker.ui.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pacemaker.ApplyActivity;
import com.example.pacemaker.CompetitionActivity;
import com.example.pacemaker.DdayActivity;
import com.example.pacemaker.EnglishActivity;
import com.example.pacemaker.GuidelinesActivity;
import com.example.pacemaker.MainActivity;
import com.example.pacemaker.MathActivity;
import com.example.pacemaker.R;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private Button applybtn;
    private Dialog dialog;
    private TextView dday;
    private TextView nextTest;
    private LinearLayout competitionPad;
    private LinearLayout guidlinesPad;
    private LinearLayout englishPad;
    private LinearLayout mathPad;
    private String name;
    private String day;
    protected Bundle bundle;
    String sfName = "dday_counter";

    public void setNextTest(String name, String day){
        this.name = name;
        this.day = day;
    }

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        applybtn = root.findViewById(R.id.applybtn);
        dday = root.findViewById(R.id.dday);
        nextTest = root.findViewById(R.id.nextTest);
        dday.setText(day);
        nextTest.setText(name);

        SharedPreferences sf = this.getActivity().getSharedPreferences(sfName, 0);
        String test_sf = sf.getString("test_sf", day);
        String day_sf = sf.getString("day_sf", name);
        if (test_sf != null) {
            nextTest.setText(test_sf);
            dday.setText(day_sf);
        }

        ViewGroup dday_layout = (ViewGroup) root.findViewById(R.id.dday_layout);
        dday_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ddayActivity = new Intent(getContext(), DdayActivity.class);
                startActivityForResult(ddayActivity, 1);
            }
        });

        dialog = new Dialog(root.getContext());
        dialog.setContentView(R.xml.apply_choice_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        competitionPad = root.findViewById(R.id.competitionPad);
        competitionPad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent competitionIntent = new Intent(getContext(), CompetitionActivity.class);
                startActivity(competitionIntent);
            }
        });

        guidlinesPad = root.findViewById(R.id.guidelinesPad);
        guidlinesPad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guidIntent = new Intent(getContext(), GuidelinesActivity.class);
                startActivity(guidIntent);
            }
        });

        englishPad = root.findViewById(R.id.englishPad);
        englishPad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent englishIntent = new Intent(getContext(), EnglishActivity.class);
                startActivity(englishIntent);
            }
        });

        mathPad = root.findViewById(R.id.mathPad);
        mathPad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mathIntent = new Intent(getContext(), MathActivity.class);
                startActivity(mathIntent);
            }
        });

        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(root.getContext(), ApplyActivity.class);
                ImageButton uway = dialog.findViewById(R.id.uwayBtn);
                ImageButton jinhak = dialog.findViewById(R.id.jinhakBtn);

                uway.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra("url", "http://m.uwayapply.com/");
                        startActivity(intent);
                    }
                });

                jinhak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra("url", "https://www.jinhakapply.com/");
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode==RESULT_OK) {
                String newName = data.getStringExtra("new_nextTest");
                String newDay = data.getStringExtra("new_dday");
                nextTest.setText(newName);
                dday.setText(newDay);
            }
        }
    }

    public String getNextTest_name() {
        return nextTest.getText().toString();
    }
    public String getNextTest_day() {
        return dday.getText().toString();
    }

}