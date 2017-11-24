package com.example.lenovo.yot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by lenovo on 2017/9/23.
 */
public class ToFriendActivity extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public Context context;
    private List<List_friend> data;
    View view;
    private OnItemListener listener;



    public ToFriendActivity(android.content.Context context,List<List_friend> data){
        this.context = context;
        this.data = data;

    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.listener = onItemListener;
    }

    public interface OnItemListener {
        //此处可以选择我们需要的参数例如position.
        void onClick(View v, int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_friend, parent, false);
        return new SendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SendViewHolder viewHolder = (SendViewHolder) holder;
        // viewHolder.textView_id.setTextSize(20);



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SendViewHolder extends RecyclerView.ViewHolder {





        public SendViewHolder(final View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {

                        listener.onClick(v, getAdapterPosition());
                    }
                }
            });

        }



    }



}

