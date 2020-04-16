package com.example.pacemaker.ui.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.pacemaker.ApplyActivity;
import com.example.pacemaker.R;

public class HomeFragment extends Fragment {

    private Button applybtn;
    private Dialog dialog;
    private TextView dday;
    private TextView nextTest;
    private String name;
    private String day;

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

        dialog = new Dialog(root.getContext());
        dialog.setContentView(R.xml.apply_choice_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
}