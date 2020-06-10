package com.example.pacemaker.ui.mynote;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pacemaker.MathFormulaHashMap;
import com.example.pacemaker.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.like.IconType;
import com.like.LikeButton;
import com.like.OnLikeListener;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private JSONArray jsonArray;
    private ArrayList<CardForm> mData;
    private SharedPreferences pref;
    private Context context;
    private HashMap<String, String> hmap;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    CardListAdapter(ArrayList<CardForm> list, Context context) {
        mData = list;
        this.context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        hmap = MathFormulaHashMap.getData();
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public CardListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        @SuppressLint("ResourceType") View view = inflater.inflate(R.xml.card_recycler_item, parent, false);
        CardListAdapter.ViewHolder vh = new CardListAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final CardListAdapter.ViewHolder holder, int position) {
        final SharedPreferences.Editor editor = pref.edit();
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.xml.test_reading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final CardForm item = mData.get(position);

        holder.cardname1.setText(item.getName1());
        holder.starCheck1.setLiked(pref.getBoolean(item.getName1(), false));
        holder.cardname2.setText(item.getName2());
        if(item.getName2().equals("NO")) holder.cardframe2.setVisibility(View.GONE);
        else{
            holder.starCheck2.setLiked(pref.getBoolean(item.getName2(), false));
            final String imgUrl2 = "http://nobles1030.cafe24.com/mathMaterial/" + hmap.get(item.getName2());
            final Handler mHandler2 = new Handler();
            new Thread(new Runnable(){
                @Override
                public void run(){
                    Bitmap bitmap2 = null;
                    try {
                        // Download Image from URL
                        InputStream input2 = new java.net.URL(imgUrl2).openStream();
                        // Decode Bitmap
                        bitmap2 = BitmapFactory.decodeStream(input2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    final Bitmap finalBitmap2 = bitmapResize(bitmap2);
                    mHandler2.post(new Runnable(){
                        @Override
                        public void run(){
                            holder.cardImage2.setImageBitmap(finalBitmap2);
                        }
                    });
                }
            }).start();


            holder.cardframe2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PhotoView testReading = dialog.findViewById(R.id.test_reading);
                    final Handler readingHandler = new Handler();
                    new Thread(new Runnable(){
                        @Override
                        public void run(){
                            Bitmap bitmap = null;
                            try {
                                // Download Image from URL
                                InputStream input = new java.net.URL(imgUrl2).openStream();
                                // Decode Bitmap
                                bitmap = BitmapFactory.decodeStream(input);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            final Bitmap readingBitmap = bitmap;
                            readingHandler.post(new Runnable(){
                                @Override
                                public void run(){
                                    testReading.setImageBitmap(readingBitmap);
                                }
                            });
                        }
                    }).start();
                    dialog.show();
                }
            });
        }
        final String imgUrl1 = "http://nobles1030.cafe24.com/mathMaterial/" + hmap.get(item.getName1());

        final Handler mHandler = new Handler();
        new Thread(new Runnable(){
            @Override
            public void run(){
                Bitmap bitmap1 = null;
                try {
                    // Download Image from URL
                    InputStream input1 = new java.net.URL(imgUrl1).openStream();
                    // Decode Bitmap
                    bitmap1 = BitmapFactory.decodeStream(input1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                final Bitmap finalBitmap1 = bitmapResize(bitmap1);
                mHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        holder.cardImage1.setImageBitmap(finalBitmap1);
                    }
                });
            }
        }).start();

        holder.cardframe1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PhotoView testReading = dialog.findViewById(R.id.test_reading);
                final Handler readingHandler = new Handler();
                new Thread(new Runnable(){
                    @Override
                    public void run(){
                        Bitmap bitmap = null;
                        try {
                            // Download Image from URL
                            InputStream input = new java.net.URL(imgUrl1).openStream();
                            // Decode Bitmap
                            bitmap = BitmapFactory.decodeStream(input);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        final Bitmap readingBitmap = bitmap;
                        readingHandler.post(new Runnable(){
                            @Override
                            public void run(){
                                testReading.setImageBitmap(readingBitmap);
                            }
                        });
                    }
                }).start();
                dialog.show();
            }
        });

        holder.starCheck1.setOnLikeListener(new OnLikeListener() {
            String arr = "{" +"\"name\":"+"\""+item.getName1()+"\""+ "}";
            @Override
            public void liked(LikeButton likeButton) {
                try {
                    String tmp = pref.getString("favorite_math", null);
                    if(tmp != null) Log.d("favorite math is =", tmp);
                    jsonArray = tmp != null ? new JSONArray(tmp) : new JSONArray() ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(arr);
                Log.d("json is = ", String.valueOf(jsonArray));
                editor.putString("favorite_math", String.valueOf(jsonArray));
                editor.putBoolean(item.getName1(), true);
                editor.commit();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                try {
                    String tmp = pref.getString("favorite_math", null);
                    if(tmp != null)Log.d("favorite math is =", tmp);
                    jsonArray = tmp != null ? new JSONArray(tmp) : new JSONArray() ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int i = 0;
                while(i < jsonArray.length()){
                    try {
                        if(String.valueOf(jsonArray.get(i)).contains(item.getName1())) break;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                jsonArray.remove(i);
                Log.d("remove json is = ", String.valueOf(jsonArray));
                editor.putString("favorite_math", String.valueOf(jsonArray));
                editor.putBoolean(item.getName1(), false);
                editor.commit();
            }
        });

        holder.starCheck2.setOnLikeListener(new OnLikeListener() {
            String arr = "{" +"\"name\":"+"\""+item.getName2()+"\""+ "}";
            @Override
            public void liked(LikeButton likeButton) {
                try {
                    String tmp = pref.getString("favorite_math", null);
                    if(tmp != null) Log.d("favorite math is =", tmp);
                    jsonArray = tmp != null ? new JSONArray(tmp) : new JSONArray() ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(arr);
                Log.d("json is = ", String.valueOf(jsonArray));
                editor.putString("favorite_math", String.valueOf(jsonArray));
                editor.putBoolean(item.getName2(), true);
                editor.commit();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                try {
                    String tmp = pref.getString("favorite_math", null);
                    if(tmp != null)Log.d("favorite math is =", tmp);
                    jsonArray = tmp != null ? new JSONArray(tmp) : new JSONArray() ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int i = 0;
                while(i < jsonArray.length()){
                    try {
                        if(String.valueOf(jsonArray.get(i)).contains(item.getName2())) break;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                jsonArray.remove(i);
                Log.d("remove json is = ", String.valueOf(jsonArray));
                editor.putString("favorite_math", String.valueOf(jsonArray));
                editor.putBoolean(item.getName2(), false);
                editor.commit();
            }
        });
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cardframe1;
        LinearLayout cardframe2;
        ImageView cardImage1;
        ImageView cardImage2;
        TextView cardname1;
        TextView cardname2;
        LikeButton starCheck1;
        LikeButton starCheck2;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            cardframe1 = itemView.findViewById(R.id.card_frame1);
            cardframe2 = itemView.findViewById(R.id.card_frame2);
            cardImage1 = itemView.findViewById(R.id.card_image1);
            cardImage2 = itemView.findViewById(R.id.card_image2);
            cardname1 = itemView.findViewById(R.id.card_name1);
            cardname2 = itemView.findViewById(R.id.card_name2);
            starCheck1 = itemView.findViewById(R.id.like_check_mymath1);
            starCheck1.setCircleEndColorRes(R.color.colorPrimary);
            starCheck2 = itemView.findViewById(R.id.like_check_mymath2);
            starCheck2.setCircleEndColorRes(R.color.colorPrimary);
            starCheck1.setIcon(IconType.Star);
            starCheck2.setIcon(IconType.Star);
        }
    }

    private Bitmap bitmapResize(Bitmap bitmap){

        int width = 180; // 축소시킬 너비
        int height = 100; // 축소시킬 높이
        float bmpWidth = bitmap.getWidth();
        float bmpHeight = bitmap.getHeight();

        if (bmpWidth > width) {
            // 원하는 너비보다 클 경우의 설정
            float mWidth = bmpWidth / 100;
            float scale = width/ mWidth;
            bmpWidth *= (scale / 100);
            bmpHeight *= (scale / 100);
        } else if (bmpHeight > height) {
            // 원하는 높이보다 클 경우의 설정
            float mHeight = bmpHeight / 100;
            float scale = height/ mHeight;
            bmpWidth *= (scale / 100);
            bmpHeight *= (scale / 100);
        }

        return  Bitmap.createScaledBitmap(bitmap, (int) bmpWidth, (int) bmpHeight, true);
    }
}
