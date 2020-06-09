package com.example.pacemaker.ui.test;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.example.pacemaker.R;
import java.util.ArrayList;

public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.ViewHolder> {

        private SharedPreferences pref;
        private ArrayList<TestForm> mData;
        private Context context;

        // 생성자에서 데이터 리스트 객체를 전달받음.
        public TestRecyclerAdapter(ArrayList<TestForm> list, Context context) {
            mData = list;
            this.context = context;
            pref = PreferenceManager.getDefaultSharedPreferences(this.context);
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
            TestForm item = mData.get(position);

            holder.title.setText(item.getNum() + ". " + item.getPart());
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
                            int onedu = pref.getInt(pref.getString("id", "") + "onedu", 0);
                            if(onedu == 0)  Toast.makeText(context, "원두가 부족합니다.\n원두를 충전후 이용해 주세요", Toast.LENGTH_LONG).show();
                            else{
                                editor.putInt(pref.getString("id", "") + "onedu", onedu - 1);
                                editor.commit();
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
