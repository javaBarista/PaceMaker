package com.example.pacemaker;


import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout login;
    private LinearLayout join;
    private LinearLayout loginBox;
    private LinearLayout joinBox;
    private EditText id;
    private EditText password;
    private EditText idText;
    private EditText passwordText;
    private EditText passwordText_Chk;
    private TextView passwordChk;
    private EditText mail;
    private Spinner mailList;
    private Spinner collegeList1;
    private Spinner collegeList2;
    private Spinner collegeList3;
    private EditText name;
    private Button loginBtn;
    private Button joinBtn;
    private ImageButton checkBtn;
    private int trigger = 0;
    private SharedPreferences prefs;
    private Dialog chkDialog;
    private Bundle bundle = new Bundle();
    private boolean ispwChk = false;
    private boolean isvaild = false;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();

        prefs = getSharedPreferences("PaceMaker", MODE_PRIVATE);

        login = findViewById(R.id.login);
        join = findViewById(R.id.signup);
        loginBox = findViewById(R.id.loginBox);
        joinBox = findViewById(R.id.signupBox);

        //로그인 박스안에
        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);

        password.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        id.setText(prefs.getString("id",""));
        password.setText(prefs.getString("password",""));

        //가입 박스 안에
        idText = findViewById(R.id.idText);
        checkBtn = findViewById(R.id.checkBtn);
        passwordText = findViewById(R.id.passwordText);
        passwordText_Chk = findViewById(R.id.pwChkTxt);
        passwordChk = findViewById(R.id.pwChk);
        mail = findViewById(R.id.mailText);
        mailList = findViewById(R.id.mailList);
        ArrayAdapter mailAdapter = ArrayAdapter.createFromResource(this, R.array.mail, android.R.layout.simple_spinner_dropdown_item);
        mailList.setAdapter(mailAdapter);

        collegeList1 = findViewById(R.id.college1);
        collegeList2 = findViewById(R.id.college2);
        collegeList3 = findViewById(R.id.college3);
        ArrayAdapter collegeAdapter = ArrayAdapter.createFromResource(this, R.array.college, android.R.layout.simple_spinner_dropdown_item);
        collegeList1.setAdapter(collegeAdapter);
        collegeList2.setAdapter(collegeAdapter);
        collegeList3.setAdapter(collegeAdapter);

        name = findViewById(R.id.nameText);
        joinBtn = findViewById(R.id.joinBtn);

        passwordText.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordText_Chk.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordText_Chk.setTransformationMethod(PasswordTransformationMethod.getInstance());

        passwordText_Chk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordChk.setVisibility(View.VISIBLE);
                if(passwordText.getText().toString().equals(passwordText_Chk.getText().toString())) {
                    passwordChk.setText("가능");
                    passwordChk.setTextColor(Color.parseColor("#FF32CD32"));
                    ispwChk = true;
                } else {
                    ispwChk = false;
                    passwordChk.setText("불가");
                    passwordChk.setTextColor(Color.parseColor("#FFFF0000"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(trigger > 0){ trigger = 0; }
                else{ trigger = 1; }

                onSelectBox();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(trigger > 0){ trigger = 0; }
                else{ trigger = 2; }

                onSelectBox();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //비동기작업으로 로그인 정보를 넘긴다.
                OkHttpClient loginBtnClickEvent = new OkHttpClient();
                SharedPreferences prefs = getSharedPreferences("PaceMaker", MODE_PRIVATE);
                final SharedPreferences.Editor editor = prefs.edit();

                RequestBody body = new FormBody.Builder()
                        .add("id", id.getText().toString())
                        .add("password", password.getText().toString())
                        .build();

                editor.putString("id", id.getText().toString());
                editor.putString("password", password.getText().toString());

                loginBtnClickEvent.newCall(new Request.Builder().url("http://nobles1030.cafe24.com/loginRequest.php").post(body).build()).enqueue(new Callback() {

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

                                    Log.d("result is = ", result);

                                    if(result.contains("success")){
                                        JsonElement jsonElement =  new JsonParser().parse(result.substring(result.indexOf("{"), result.indexOf("}") + 1));
                                        JsonObject jsonObject = jsonElement.getAsJsonObject();

                                        editor.commit();
                                        Intent mainmenu = new Intent(getApplicationContext(), MainActivity.class);
                                        bundle.putString("id", id.getText().toString());
                                        bundle.putString("password", password.getText().toString());
                                        bundle.putString("name", jsonObject.get("name").toString().replaceAll("\"",""));
                                        bundle.putString("college1", jsonObject.get("college1").toString().replaceAll("\"",""));
                                        bundle.putString("college2", jsonObject.get("college2").toString().replaceAll("\"",""));
                                        bundle.putString("college3", jsonObject.get("college3").toString().replaceAll("\"",""));
                                        if(jsonObject.get("mail").toString().length() > 5) {
                                            String temp =  jsonObject.get("mail").toString().replace("\"", "");
                                            bundle.putString("mail", temp.substring(0, temp.indexOf("@")));
                                            bundle.putString("address", temp.substring(temp.indexOf("@") + 1));
                                        }
                                        mainmenu.putExtras(bundle);
                                        mainmenu.putExtra("test", jsonObject.get("college").toString().replaceAll("\"",""));
                                        mainmenu.putExtra("date", jsonObject.get("endDate").toString().replaceAll("\"",""));
                                        startActivity(mainmenu);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Check your ID or Password", Toast.LENGTH_LONG).show();
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

        checkBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(idText.getText().toString() == null || idText.getText().toString().length() < 5){
                    Toast.makeText(getApplicationContext(), "ID is longer than 5 Char", Toast.LENGTH_LONG).show();
                }
                else {
                    OkHttpClient requestValidationChk = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("id", idText.getText().toString())
                            .build();
                    requestValidationChk.newCall(new Request.Builder().url("http://nobles1030.cafe24.com/vaildationCheck.php").post(body).build()).enqueue(new Callback() {

                        @Override
                        public void onFailure(Call call, IOException e) {
                           //여기도 토스트메시지 구현
                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            new Handler(getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String result = response.body().string();

                                        if (result.contains("success")) {
                                            isvaild = true;
                                            idText.setBackgroundColor(Color.parseColor("#FF708090"));
                                            idText.setEnabled(false);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "User ID is already in use.", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

        chkDialog = new Dialog(this);
        chkDialog.setContentView(R.xml.check_join_dialog);
        chkDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isvaild && ispwChk && passwordText.getText().toString().length() > 5 && name.getText().toString().length() > 2){
                    final OkHttpClient joinBtnClickEvent = new OkHttpClient();
                    SharedPreferences prefs = getSharedPreferences("ChajAjUm", MODE_PRIVATE);
                    final SharedPreferences.Editor editor = prefs.edit();

                    String address = mailList.getSelectedItem().toString().equals("선택 안함") ? "" : mail.getText().toString() + "@" + mailList.getSelectedItem().toString();
                    String college1 = collegeList1.getSelectedItem().toString().equals("대학 선택") ? "" : collegeList1.getSelectedItem().toString();
                    String college2 = collegeList2.getSelectedItem().toString().equals("대학 선택") ? "" : collegeList2.getSelectedItem().toString();
                    String college3 = collegeList3.getSelectedItem().toString().equals("대학 선택") ? "" : collegeList3.getSelectedItem().toString();

                    final RequestBody body = new FormBody.Builder()
                            .add("id", idText.getText().toString())
                            .add("password", passwordText.getText().toString())
                            .add("name", name.getText().toString())
                            .add("mail", address)
                            .add("college1", college1)
                            .add("college2", college2)
                            .add("college3", college3)
                            .build();

                    final CheckBox agreeChk = chkDialog.findViewById(R.id.agreeChk);
                    Button signInBtn = chkDialog.findViewById(R.id.signinBtn);

                    signInBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(agreeChk.isChecked()){
                                joinBtnClickEvent.newCall(new Request.Builder().url("http://nobles1030.cafe24.com/registeMember.php").post(body).build()).enqueue(new Callback() {

                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        //인터넷 연결확인 토스트메시지 출력
                                    }

                                    @Override
                                    public void onResponse(Call call, final Response response) throws IOException {
                                        new Handler(getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {

                                                String result = null;
                                                try {
                                                    result = response.body().string();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                Intent intent = getIntent();
                                                finish();
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                });
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "개인정보 사용내역에 동의하셔야 합니다.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    chkDialog.show();
                }

                else if(passwordText.getText().toString().length() < 6 && name.getText().toString().length() < 4){
                    Toast.makeText(getApplicationContext(), "비밀번호와 이름은 빈칸일 수 없습니다.", Toast.LENGTH_LONG).show();
                }

                else if(!ispwChk){
                    Toast.makeText(getApplicationContext(), "유효하지 않은 비밀번호 입니다.", Toast.LENGTH_LONG).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), "ID 중복확인을 해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void onSelectBox(){

        final LinearLayout.LayoutParams loginWeight
                = (LinearLayout.LayoutParams) login.getLayoutParams();
        final LinearLayout.LayoutParams joinWeight
                = (LinearLayout.LayoutParams) join.getLayoutParams();

        switch(trigger){
            case 0:
                loginWeight.weight = 1;
                joinWeight.weight = 1;

                login.setLayoutParams(loginWeight);
                join.setLayoutParams(joinWeight);

                loginBox.setVisibility(View.GONE);
                joinBox.setVisibility(View.GONE);
                break;
            case 1:
                loginWeight.weight = 1;
                joinWeight.weight = 0;

                login.setLayoutParams(loginWeight);
                join.setLayoutParams(joinWeight);

                loginBox.setVisibility(View.VISIBLE);
                joinBox.setVisibility(View.GONE);
                break;
            case 2:
                loginWeight.weight = 0;
                joinWeight.weight = 1;

                login.setLayoutParams(loginWeight);
                join.setLayoutParams(joinWeight);

                loginBox.setVisibility(View.GONE);
                joinBox.setVisibility(View.VISIBLE);
                break;
        }

    }

}
