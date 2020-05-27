package com.example.pacemaker.ui.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pacemaker.R;
import java.util.ArrayList;

public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.ViewHolder> {

        private ArrayList<TestForm> mData;
        private Context context;
        private int curPos = -1;

        // 생성자에서 데이터 리스트 객체를 전달받음.
        public TestRecyclerAdapter(ArrayList<TestForm> list, Context context) {
            mData = list;
            this.context = context;
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
            TestForm item = mData.get(position);

            holder.title.setText(item.getNum() + ". " + item.getPart());
        }

        // getItemCount() - 전체 데이터 갯수 리턴.
        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageButton imgbtn;

            ViewHolder(View itemView) {
                super(itemView);
                // 뷰 객체에 대한 참조. (hold strong reference)
                title = itemView.findViewById(R.id.wrong_result);
               // imgbtn = itemView.findViewById(R.id.regist_qanda);
            }
        }
    }
