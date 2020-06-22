package com.example.pacemaker;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
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

public class AnswerListAdapter extends RecyclerView.Adapter<AnswerListAdapter.ItemViewHolder> {
    private ArrayList<AnswerForm> mData;
    private Context context;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private int prePosition = -1;
    private SharedPreferences pref;
    private String myId;
    private String num;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    AnswerListAdapter(ArrayList<AnswerForm> list, String num, Context context) {
        mData = list;
        this.context = context;
        pref =  PreferenceManager.getDefaultSharedPreferences(context);
        myId = pref.getString("id", "");
        this.num = num;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public AnswerListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        @SuppressLint("ResourceType") View view = inflater.inflate(R.xml.answer_recycler_item, parent, false);
        AnswerListAdapter.ItemViewHolder vh = new AnswerListAdapter.ItemViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final AnswerListAdapter.ItemViewHolder holder, final int position) {
        holder.onBind(mData.get(position), position);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() { return mData.size(); }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView userId;
        private TextView date;
        private TextView body;
        private TextView open;
        private TextView long_body;
        private ImageButton report;
        private ImageButton delete;
        private AnswerForm data;
        private int position;

        ItemViewHolder(View itemView) {
            super(itemView);

            userId = itemView.findViewById(R.id.answer_item_userId);
            date = itemView.findViewById(R.id.answer_item_date);
            body = itemView.findViewById(R.id.answer_item_body);
            open = itemView.findViewById(R.id.answer_item_open);
            long_body = itemView.findViewById(R.id.answer_item_longbody);
            report = itemView.findViewById(R.id.answer_user_report);
            delete = itemView.findViewById(R.id.answer_my_ans_delete);
        }

        void onBind(final AnswerForm data, final int position) {
            this.data = data;
            this.position = position;

            userId.setText(data.getUserID());
            date.setText(data.getUploadDate());
            if(data.getBody().length() > 14){
                open.setVisibility(View.VISIBLE);
                body.setText(data.getBody().substring(0, 14) + "....");
            }
            else body.setText(data.getBody());

            if(data.getUserID().equals(myId))   report.setVisibility(View.GONE);
            else    delete.setVisibility(View.GONE);
            changeVisibility(selectedItems.get(position));
            final Dialog dialog = new Dialog(context);
            final OkHttpClient ClickEvent = new OkHttpClient();

            open.setOnClickListener(this);
            report.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {
                    dialog.setContentView(R.xml.report_user_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    final EditText edt = dialog.findViewById(R.id.user_report_body);
                    Button btn = dialog.findViewById(R.id.user_report_btn);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RequestBody body = new FormBody.Builder()
                                    .add("qnum", num)
                                    .add("anum", data.getAnum())
                                    .add("userId", data.getUserID())
                                    .add("qBody", data.getBody())
                                    .add("rBody", edt.getText().toString())
                                    .add("upload", data.getUploadDate())
                                    .build();
                            ClickEvent.newCall(new Request.Builder().url("http://nobles1030.cafe24.com/Request_report_user.php").post(body).build()).enqueue(new Callback() {

                                @Override
                                public void onFailure(Call call, IOException e) {}

                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    new Handler(getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            String result = response.body().toString();
                                            mData.remove(position);
                                            itemView.setVisibility(View.GONE);
                                            Toast.makeText(context, "신고 접수 되었습니다.", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            });
                        }
                    });
                    dialog.show();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {
                    dialog.setContentView(R.xml.delete_check_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    Button btn = dialog.findViewById(R.id.check_delete_btn);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RequestBody body = new FormBody.Builder()
                                    .add("userId", data.getUserID())
                                    .add("anum", data.getAnum())
                                    .add("qnum", num)
                                    .build();
                            ClickEvent.newCall(new Request.Builder().url("http://nobles1030.cafe24.com/request_remove_myans.php").post(body).build()).enqueue(new Callback() {

                                @Override
                                public void onFailure(Call call, IOException e) {}

                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    new Handler(getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            itemView.setVisibility(View.GONE);
                                            mData.remove(position);
                                            Toast.makeText(context, "삭제 되었습니다.", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            });
                        }
                    });
                    dialog.show();
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.answer_item_open:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;

                case R.id.answer_user_report:
            }
        }

        /**
         * 클릭된 Item의 상태 변경
         * @param isExpanded Item을 펼칠 것인지 여부
         */
        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 300;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(300);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView의 높이 변경
                    long_body.setText(data.getBody());
                    //long_body.getLayoutParams().height = value;
                    long_body.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    if(isExpanded){
                        long_body.setVisibility(View.VISIBLE);
                        body.setVisibility(View.INVISIBLE);
                        open.setText("↑닫기");
                    }
                    else{
                        long_body.setVisibility(View.GONE);
                        body.setVisibility(View.VISIBLE);
                        open.setText("↓펼치기");
                    }
                }
            });
            // Animation start
            va.start();
        }
    }
}