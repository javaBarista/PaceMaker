package com.example.pacemaker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QandAListAdapter extends RecyclerView.Adapter<QandAListAdapter.ViewHolder> {
    private ArrayList<QandAForm> mData;
    private SharedPreferences pref;
    private Context context;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    QandAListAdapter(ArrayList<QandAForm> list, Context context) {
        mData = list;
        this.context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public QandAListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        @SuppressLint("ResourceType") View view = inflater.inflate(R.xml.qanda_recycler_item, parent, false);
        QandAListAdapter.ViewHolder vh = new QandAListAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final QandAListAdapter.ViewHolder holder, final int position) {
        final QandAForm item = mData.get(position);

        holder.title.setText(item.getYear() + " " + item.getName());
        holder.body.setText(item.getUserID());
        holder.year.setText(item.getUploadDate());
        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnswerActivity.class);
                intent.putExtra("id", item.getUserID());
                intent.putExtra("num", item.getNum());
                intent.putExtra("img", item.getImgUrl());
                intent.putExtra("year", item.getYear());
                intent.putExtra("name", item.getName());
                context.startActivity(intent);
            }
        });
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() { return mData.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cl;
        TextView title;
        TextView body;
        TextView year;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            cl = itemView.findViewById(R.id.qanda_cl);
            title = itemView.findViewById(R.id.qanda_title);
            body = itemView.findViewById(R.id.qanda_body);
            year = itemView.findViewById(R.id.qanda_Date);
        }
    }
}
