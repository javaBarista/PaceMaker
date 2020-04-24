package com.example.pacemaker.ui.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.pacemaker.CompetitionActivity;
import com.example.pacemaker.LoginActivity;
import com.example.pacemaker.R;

public class MyPageFragment extends Fragment {

    private TextView myName;
    private TextView myId;
    private Button logoutBtn;
    private TextView goal1;
    private TextView goal2;
    private TextView goal3;
    private Bundle bundle;
    private Switch noti;
    private String id;
    private String name;
    private String college1;
    private String college2;
    private String college3;

    public void setBundle(Bundle bundle){
        this.bundle = bundle;
        this.id = this.bundle.getString("id");
        this.name = this.bundle.getString("name");
        this.college1 = this.bundle.getString("college1");
        this.college2 = this.bundle.getString("college2");
        this.college3 = this.bundle.getString("college3");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mypage, container, false);

        myName = root.findViewById(R.id.myName);
        myId = root.findViewById(R.id.myId);
        myName.setText(name);
        myId.setText(id);

        noti = root.findViewById(R.id.notiChk);

        goal1 = root.findViewById(R.id.goal1);
        goal2 = root.findViewById(R.id.goal2);
        goal3 = root.findViewById(R.id.goal3);
        goal1.setText(college1);
        goal2.setText(college2);
        goal3.setText(college3);

        logoutBtn = root.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getContext(), LoginActivity.class);
                startActivity(loginIntent);
                getActivity().finish();
            }
        });

        return root;
    }
}