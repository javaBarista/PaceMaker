package com.example.pacemaker.ui.test;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pacemaker.R;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static android.os.Looper.getMainLooper;

public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.ViewHolder> {

        private SharedPreferences pref;
        private JSONArray jsonArray;
        private ArrayList<TestForm> mData;
        private Context context;
        private String year;
        private String college;

        // 생성자에서 데이터 리스트 객체를 전달받음.
        public TestRecyclerAdapter(ArrayList<TestForm> list, Context context, String year, String college) {
            mData = list;
            this.context = context;
            pref = PreferenceManager.getDefaultSharedPreferences(this.context);
            this.year = year;
            this.college = college;
        }

        // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
        @Override
        public TestRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

            @SuppressLint("ResourceType") View view = inflater.inflate(R.xml.result_recycler_item, parent, false);
            TestRecyclerAdapter.ViewHolder vh = new TestRecyclerAdapter.ViewHolder(view);

            return vh;
        }

        // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
        @Override
        public void onBindViewHolder(final TestRecyclerAdapter.ViewHolder holder, final int position) {
            final SharedPreferences.Editor editor = pref.edit();
            final TestForm item = mData.get(position);

            holder.title.setText(item.getNum() + ". " + item.getPart());
            holder.btn.setEnabled(pref.getBoolean(year + college + item.getNum() + "ask", true));
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.xml.qanda_check_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    Button button = dialog.findViewById(R.id.check_qanda_btn);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int onedu = pref.getInt(pref.getString("id", "") + "onedu", 0);
                            if(onedu == 0)  Toast.makeText(context, "원두가 부족합니다.\n원두를 충전후 이용해 주세요", Toast.LENGTH_LONG).show();
                            else{
                                OkHttpClient saveClickEvent = new OkHttpClient();
                                RequestBody body = new FormBody.Builder()
                                        .add("year", year)
                                        .add("name", college)
                                        .add("id", pref.getString("id", ""))
                                        .add("testNum", item.getNum())
                                        .build();
                                saveClickEvent.newCall(new Request.Builder().url("http://nobles1030.cafe24.com/upload_question.php").post(body).build()).enqueue(new Callback() {

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
                                                    result = result.substring(result.indexOf("{"), result.indexOf("}"));
                                                    result += ("," + "\"testNum\":"+"\""+item.getNum()+"\""+ "," + "\"image\":"+"\""+item.getAddress()+"\""+ "," + "\"text\":"+"\""+item.getMain_text()+"\""+"}");
                                                    Log.d("result == ", result);
                                                    try {
                                                        String tmp = pref.getString("ask_favorite_test", null);
                                                        jsonArray = tmp != null ? new JSONArray(tmp) : new JSONArray() ;
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    jsonArray.put(result);
                                                    editor.putString("ask_favorite_test", String.valueOf(jsonArray));
                                                    editor.putBoolean(year + college + item.getNum() + "ask", false);
                                                    editor.putInt(pref.getString("id", "") + "onedu", onedu - 1);
                                                    editor.commit();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                });
                                Toast.makeText(context, "질문이 등록 되었습니다.\n등록하신 질문은 myNote에서 확인 가능 합니다.", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog.show();
                }
            });
        }

        // getItemCount() - 전체 데이터 갯수 리턴.
        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            Button btn;

            ViewHolder(View itemView) {
                super(itemView);
                // 뷰 객체에 대한 참조. (hold strong reference)
                title = itemView.findViewById(R.id.wrong_result);
                btn = itemView.findViewById(R.id.request_qanda_btn);
            }
        }
    }
