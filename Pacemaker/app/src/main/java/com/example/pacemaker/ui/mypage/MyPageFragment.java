package com.example.pacemaker.ui.mypage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.ErrorReportingActivity;
import com.example.pacemaker.LoginActivity;
import com.example.pacemaker.MyNoteActivity;
import com.example.pacemaker.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Looper.getMainLooper;

public class MyPageFragment extends Fragment {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private TextView myName;
    private TextView myId;
    private Button logoutBtn;
    private TextView goal1;
    private TextView goal2;
    private TextView goal3;
    private Bundle bundle;
    private LinearLayout myInfo;
    private LinearLayout myInfo_edit;
    private EditText pre_pw;
    private EditText new_pw;
    private EditText new_pw_chk;
    private TextView ispw_chk;
    private EditText new_name;
    private EditText new_mail;
    private Spinner mailSpin;
    private Button saveBtn;
    private Button vocaBtn;
    private Button onedu;
    private LinearLayout goal;
    private Spinner edit_college1;
    private Spinner edit_college2;
    private Spinner edit_college3;
    private Spinner countSpin;
    private Button goalSaveBtn;
    private LinearLayout myNote;
    private LinearLayout vocaSetting;
    private LinearLayout vocaLayout;
    private String id;
    private String name;
    private String college1;
    private String college2;
    private String college3;
    private ArrayAdapter adapter;
    private boolean isSetchk = false;
    private boolean isMyInfo = false;
    private boolean ispwChk = false;
    private boolean isCollegeChk = false;

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

        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = pref.edit();

        onedu = root.findViewById(R.id.on_eduBtn);
        onedu.setText(String.valueOf(pref.getInt(id+"onedu", 0)));
        onedu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(id+"onedu", 100);
                editor.commit();
                onedu.setText("100");
            }
        });

        myName = root.findViewById(R.id.myName);
        myId = root.findViewById(R.id.myId);
        myName.setText(name);
        myId.setText(id);

        goal1 = root.findViewById(R.id.goal1);
        goal2 = root.findViewById(R.id.goal2);
        goal3 = root.findViewById(R.id.goal3);
        goal1.setText(college1);
        goal2.setText(college2);
        goal3.setText(college3);

        myInfo = root.findViewById(R.id.myInfo);
        myInfo_edit = root.findViewById(R.id.myInfo_edit);
        pre_pw = root.findViewById(R.id.pre_pw);
        new_pw = root.findViewById(R.id.new_pw);
        new_pw_chk = root.findViewById(R.id.new_pw_chk);
        ispw_chk = root.findViewById(R.id.isNewPwChk);
        new_name = root.findViewById(R.id.new_name);
        new_name.setText(name);
        new_mail = root.findViewById(R.id.new_mail);
        new_mail.setText(bundle.getString("mail"));
        mailSpin = root.findViewById(R.id.new_mailSpin);
        ArrayAdapter mailAdapter = ArrayAdapter.createFromResource(getContext(), R.array.mail, android.R.layout.simple_spinner_dropdown_item);
        mailSpin.setAdapter(mailAdapter);
        mailSpin.setSelection(mailAdapter.getPosition(bundle.getString("address")));
        saveBtn = root.findViewById(R.id.saveBtn);
        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMyInfo = !isMyInfo;
                myinfo_click();
            }
        });

        goal = root.findViewById(R.id.myPage_goal);
        edit_college1 = root.findViewById(R.id.myPage_college1);
        edit_college2 = root.findViewById(R.id.myPage_college2);
        edit_college3 = root.findViewById(R.id.myPage_college3);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.college, android.R.layout.simple_spinner_dropdown_item);
        edit_college1.setAdapter(adapter);
        edit_college2.setAdapter(adapter);
        edit_college3.setAdapter(adapter);

        goalSaveBtn = root.findViewById(R.id.goalSaveBtn);
        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCollegeChk = !isCollegeChk;
                goalEdit();
            }
        });

        vocaSetting = root.findViewById(R.id.setDailyVocaBtn);
        vocaLayout = root.findViewById(R.id.setDailyVocaLayout);

        countSpin = root.findViewById(R.id.daySettingSpin);
        ArrayList<Integer> dayCount = new ArrayList<>();
        for(int i = 20; i <= 50; i += 5) dayCount.add(i);
        ArrayAdapter vocaAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, dayCount);
        countSpin.setAdapter(vocaAdapter);
        countSpin.setSelection(vocaAdapter.getPosition(pref.getInt("voca_setting_count", 20)));

        vocaBtn = root.findViewById(R.id.vocaSetBtn);
        vocaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("voca_setting_count", (Integer) countSpin.getSelectedItem());
                editor.commit();
                isSetchk = false;
                vocaLayout.setVisibility(View.GONE);
                Toast.makeText(getContext(), "변경완료.", Toast.LENGTH_SHORT).show();
            }
        });

        vocaSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSetchk = !isSetchk;
                if(isSetchk) vocaLayout.setVisibility(View.VISIBLE);
                else vocaLayout.setVisibility(View.GONE);
            }
        });

        myNote = root.findViewById(R.id.myPage_myNote);
        myNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myNoteIntent = new Intent(getContext(), MyNoteActivity.class);
                startActivity(myNoteIntent);
            }
        });

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

    void myinfo_click(){
        if(isMyInfo) {
            myInfo_edit.setVisibility(View.VISIBLE);
            pre_pw.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
            pre_pw.setTransformationMethod(PasswordTransformationMethod.getInstance());
            new_pw.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
            new_pw.setTransformationMethod(PasswordTransformationMethod.getInstance());
            new_pw_chk.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            new_pw_chk.setTransformationMethod(PasswordTransformationMethod.getInstance());
            new_pw_chk.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    ispw_chk.setVisibility(View.VISIBLE);
                    if(new_pw.getText().toString().equals(new_pw_chk.getText().toString())) {
                        ispw_chk.setText("가능");
                        ispw_chk.setTextColor(Color.parseColor("#FF32CD32"));
                        ispwChk = true;
                    } else {
                        ispwChk = false;
                        ispw_chk.setText("불가");
                        ispw_chk.setTextColor(Color.parseColor("#FFFF0000"));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) { }
            });

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ispwChk && bundle.getString("password").equals(pre_pw.getText().toString())){
                        String address = new_mail.getText().toString() + "@" + mailSpin.getSelectedItem().toString();

                        OkHttpClient saveClickEvent = new OkHttpClient();
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                        final SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("password", new_pw.getText().toString());

                        RequestBody body = new FormBody.Builder()
                                .add("id", id)
                                .add("password", new_pw.getText().toString())
                                .add("name", new_name.getText().toString())
                                .add("mail", address)
                                .build();

                        saveClickEvent.newCall(new Request.Builder().url("http://nobles1030.cafe24.com/personalInfor_modification.php").post(body).build()).enqueue(new Callback() {

                            @Override
                            public void onFailure(Call call, IOException e) {
                                //로그인 실패시 인터넷 연결확인이 필요하기 떄문에 토스트메시지 제공해야함
                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                new Handler(getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            String result = response.body().string();
                                            if(result.contains("success")){
                                                bundle.putString("password", new_pw.getText().toString());
                                                bundle.putString("name", new_name.getText().toString());
                                                bundle.putString("mail", new_mail.getText().toString());
                                                bundle.putString("address",mailSpin.getSelectedItem().toString());

                                                myName.setText(new_name.getText().toString());
                                                editor.commit();
                                                ispw_chk.setVisibility(View.INVISIBLE);
                                                Toast.makeText(getContext(), "정보수정 완료", Toast.LENGTH_LONG).show();
                                                myInfo_edit.setVisibility(View.GONE);
                                            }
                                            else{
                                                Toast.makeText(getContext(), "Check your network", Toast.LENGTH_LONG).show();
                                            }

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    }
                    else if(!ispwChk){
                        Toast.makeText(getContext(), "새로운 비밀번호를 확인하세요", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getContext(), bundle.getString("password") + "입력한 이전 비밀번호를 확인하세요", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else myInfo_edit.setVisibility(View.GONE);
    }

    void goalEdit(){
        if(isCollegeChk){
            goal1.setVisibility(View.GONE);
            goal2.setVisibility(View.GONE);
            goal3.setVisibility(View.GONE);
            edit_college1.setVisibility(View.VISIBLE);
            edit_college2.setVisibility(View.VISIBLE);
            edit_college3.setVisibility(View.VISIBLE);
            goalSaveBtn.setVisibility(View.VISIBLE);

            edit_college1.setSelection(adapter.getPosition(college1));
            edit_college2.setSelection(adapter.getPosition(college2));
            edit_college3.setSelection(adapter.getPosition(college3));

            goalSaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OkHttpClient saveClickEvent = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("id", id)
                            .add("college1", edit_college1.getSelectedItem().toString())
                            .add("college2", edit_college2.getSelectedItem().toString())
                            .add("college3", edit_college3.getSelectedItem().toString())
                            .build();
                    saveClickEvent.newCall(new Request.Builder().url("http://nobles1030.cafe24.com/update_targetUniversity.php").post(body).build()).enqueue(new Callback() {

                        @Override
                        public void onFailure(Call call, IOException e) {
                            //로그인 실패시 인터넷 연결확인이 필요하기 떄문에 토스트메시지 제공해야함
                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            new Handler(getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String result = response.body().string();
                                        if(result.contains("success")){
                                            college1 = edit_college1.getSelectedItem().toString();
                                            college2 = edit_college2.getSelectedItem().toString();
                                            college3 = edit_college3.getSelectedItem().toString();
                                            bundle.putString("college1", college1);
                                            bundle.putString("college2", college2);
                                            bundle.putString("college3", college3);

                                            goal1.setText(college1);
                                            goal2.setText(college2);
                                            goal3.setText(college3);

                                            Toast.makeText(getContext(), "정보수정 완료", Toast.LENGTH_LONG).show();

                                            goal1.setVisibility(View.VISIBLE);
                                            goal2.setVisibility(View.VISIBLE);
                                            goal3.setVisibility(View.VISIBLE);
                                            edit_college1.setVisibility(View.GONE);
                                            edit_college2.setVisibility(View.GONE);
                                            edit_college3.setVisibility(View.GONE);
                                            goalSaveBtn.setVisibility(View.GONE);
                                        }
                                        else{
                                            Toast.makeText(getContext(), "Check your network", Toast.LENGTH_LONG).show();
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
        else{
            goal1.setVisibility(View.VISIBLE);
            goal2.setVisibility(View.VISIBLE);
            goal3.setVisibility(View.VISIBLE);
            edit_college1.setVisibility(View.GONE);
            edit_college2.setVisibility(View.GONE);
            edit_college3.setVisibility(View.GONE);
            goalSaveBtn.setVisibility(View.GONE);
        }
    }

}