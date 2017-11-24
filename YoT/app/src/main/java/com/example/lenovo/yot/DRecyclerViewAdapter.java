package com.example.lenovo.yot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

/**
 * Created by lenovo on 2017/7/31.
 */
public class DRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>   {

    private final int GREEN_VIEW = 1;
    private final int BLUE_VIEW = 2;

    public Context context;
    private List<List_set> data;;
    private OnItemListener listener;



    public DRecyclerViewAdapter(Context context,List<List_set> data){
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
    public int getItemViewType(int position) {
        if(((MainActivity)context).postion_state == 0){
            return GREEN_VIEW;
        }
        else {
            return BLUE_VIEW;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == GREEN_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_set, parent, false);
            return new BlueHolder(view);
        }
        else if (viewType == BLUE_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_set, parent, false);
            return new GreenHolder(view);
        }
        else
        {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof GreenHolder) {
            GreenHolder viewHolder = (GreenHolder) holder;
            viewHolder.imageView_set.setImageResource(data.get(position).getidResource());
            viewHolder.textView_id.setTextSize(20);
            viewHolder.textView_id.setText(data.get(position).id);
            viewHolder.linearLayout.setBackgroundResource(R.drawable.ripple_green);


        } else if (holder instanceof BlueHolder) {
            BlueHolder viewHolder = (BlueHolder) holder;
            viewHolder.imageView_set.setImageResource(data.get(position).getidResource());
            viewHolder.textView_id.setTextSize(20);
            viewHolder.textView_id.setText(data.get(position).id);
            viewHolder.linearLayout.setBackgroundResource(R.drawable.ripple_blue);
            //viewHolder.mTextView.setText("我是接收");


        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    class BlueHolder extends RecyclerView.ViewHolder {


        ImageView imageView_set;
        TextView textView_id;
        LinearLayout linearLayout;

        public BlueHolder(View itemView) {
            super(itemView);

            imageView_set = (ImageView) itemView.findViewById(R.id.imageView_set);
            textView_id = (TextView) itemView.findViewById(R.id.textView_set);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.drwalayout_background);



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
    class GreenHolder extends RecyclerView.ViewHolder {


        ImageView imageView_set;
        TextView textView_id;
        LinearLayout linearLayout;

        public GreenHolder(View itemView) {
            super(itemView);

            imageView_set = (ImageView) itemView.findViewById(R.id.imageView_set);
            textView_id = (TextView) itemView.findViewById(R.id.textView_set);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.drwalayout_background);



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