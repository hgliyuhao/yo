package com.example.lenovo.yot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2017/9/18.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context context;
    private List<List_others> data;
    View view;
    private OnItemListener listener;

    private final int First_VIEW = 1;
    private final int Normal_VIEW = 2;

    public MyRecyclerViewAdapter(Context context,List<List_others> data){
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

            return Normal_VIEW;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==First_VIEW){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_my_first, parent, false);
            return new ReceiveViewHolder(view);
        }
        else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myrecycler_yo, parent, false);
            return new SendViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof SendViewHolder) {
            SendViewHolder viewHolder = (SendViewHolder) holder;
            viewHolder.imageView_others.setImageResource(data.get(position).getidResource());
            viewHolder.textView_info.setText(data.get(position).getid());
            viewHolder.textView_info.setTextSize(18);
            viewHolder.textView_small.setText(data.get(position).getinfo());

        } else if (holder instanceof ReceiveViewHolder) {
            ReceiveViewHolder viewHolder = (ReceiveViewHolder) holder;
            //viewHolder.mTextView.setText("我是接收");


        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SendViewHolder extends RecyclerView.ViewHolder {



        ImageView imageView_others;
        TextView textView_info;
        TextView textView_small;
        public SendViewHolder(final View itemView) {
            super(itemView);

            imageView_others = (ImageView) itemView.findViewById(R.id.imageView_others);
            textView_info =(TextView)itemView.findViewById(R.id.textView_others_info);
            textView_small =(TextView)itemView.findViewById(R.id.textView_small);
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

    class ReceiveViewHolder extends RecyclerView.ViewHolder {



        public ReceiveViewHolder(final View itemView) {
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
