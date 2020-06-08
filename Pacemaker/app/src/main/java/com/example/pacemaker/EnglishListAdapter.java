package com.example.pacemaker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.ui.word.DayForm;

import java.util.ArrayList;

public class EnglishListAdapter extends RecyclerView.Adapter<EnglishListAdapter.ViewHolder> {
    private ArrayList<DayForm> mData;
    private SharedPreferences pref;
    private Context context;
    private int curPos = -1;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    EnglishListAdapter(ArrayList<DayForm> list, Context context) {
        mData = list;
        this.context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public EnglishListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        @SuppressLint("ResourceType") View view = inflater.inflate(R.xml.english_recycler_item, parent, false);
        EnglishListAdapter.ViewHolder vh = new EnglishListAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final EnglishListAdapter.ViewHolder holder, int position) {
        DayForm item = mData.get(position);

        holder.day_btn1.setText(item.getDay1());
        holder.day_btn2.setText(item.getDay2());
        holder.day_btn3.setText(item.getDay3());
        if(item.getDay2().equals("NO"))  holder.day_btn2.setVisibility(View.INVISIBLE);
        if(item.getDay3().equals("NO"))  holder.day_btn3.setVisibility(View.INVISIBLE);

        if(pref.getBoolean(holder.day_btn1.getText().toString(), false)){
            holder.day_btn1.setBackground(ContextCompat.getDrawable(context, R.drawable.dday_counter));
            holder.day_btn1.setTextColor(Color.parseColor("#FFA9A9A9"));
        }
        else{
            holder.day_btn1.setBackground(ContextCompat.getDrawable(context, R.drawable.day_button));
            holder.day_btn1.setTextColor(Color.parseColor("#FF000000"));
        }
        if(pref.getBoolean(holder.day_btn2.getText().toString(), false)){
            holder.day_btn2.setBackground(ContextCompat.getDrawable(context, R.drawable.dday_counter));
            holder.day_btn2.setTextColor(Color.parseColor("#FFA9A9A9"));
        }
        else{
            holder.day_btn2.setBackground(ContextCompat.getDrawable(context, R.drawable.day_button));
            holder.day_btn2.setTextColor(Color.parseColor("#FF000000"));
        }
        if(pref.getBoolean(holder.day_btn3.getText().toString(), false)){
            holder.day_btn3.setBackground(ContextCompat.getDrawable(context, R.drawable.dday_counter));
            holder.day_btn3.setTextColor(Color.parseColor("#FFA9A9A9"));
        }
        else{
            holder.day_btn3.setBackground(ContextCompat.getDrawable(context, R.drawable.day_button));
            holder.day_btn3.setTextColor(Color.parseColor("#FF000000"));
        }

        final Intent intent = new Intent(context, WordActivity.class);
        holder.day_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("day", Integer.parseInt(String.valueOf(holder.day_btn1.getText()).substring(4)));
                context.startActivity(intent);
            }
        });
        holder.day_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("day", Integer.parseInt(String.valueOf(holder.day_btn2.getText()).substring(4)));
                context.startActivity(intent);
            }
        });
        holder.day_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("day", Integer.parseInt(String.valueOf(holder.day_btn3.getText()).substring(4)));
                context.startActivity(intent);
            }
        });
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button day_btn1;
        Button day_btn2;
        Button day_btn3;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            day_btn1 = itemView.findViewById(R.id.english_day_btn1);
            day_btn2 = itemView.findViewById(R.id.english_day_btn2);
            day_btn3 = itemView.findViewById(R.id.english_day_btn3);
        }
    }
}