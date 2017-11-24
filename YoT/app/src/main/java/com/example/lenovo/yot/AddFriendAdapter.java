package com.example.lenovo.yot;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2017/11/6.
 */
public class AddFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public Context context;
    private List<List_send> data;
    View view;
    private OnItemListener listener;


    public AddFriendAdapter(android.content.Context context, List<List_send> data) {
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

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_yo, parent, false);
        return new SendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SendViewHolder viewHolder = (SendViewHolder) holder;

        Log.e("yo", "" + data.get(position).getPosition());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SendViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView;
        ImageView imageView_yo;
        ImageView imageView_love;
        ImageView imageView_hoping;
        TextView textView_message;
        TextView textView_location;
        TextView textView_my_yo;
        TextView textView_my_love;
        TextView textView_my_want;


        public SendViewHolder(final View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView_yowall);
            imageView_yo = (ImageView) itemView.findViewById(R.id.imageView_my_yo);
            imageView_love = (ImageView) itemView.findViewById(R.id.imageView_my_love);
            imageView_hoping = (ImageView) itemView.findViewById(R.id.imageView_my_hoping);
            textView_message = (TextView) itemView.findViewById(R.id.textView_yowall_message);
            textView_location = (TextView) itemView.findViewById(R.id.textView_location);
            textView_my_yo = (TextView) itemView.findViewById(R.id.textView_my_yo);
            textView_my_love = (TextView) itemView.findViewById(R.id.textView_my_love);
            textView_my_want = (TextView) itemView.findViewById(R.id.textView_my_hoping);

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