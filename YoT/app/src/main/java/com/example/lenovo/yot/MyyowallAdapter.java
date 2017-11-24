package com.example.lenovo.yot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2017/10/11.
 */
public class MyyowallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public Context context;
    private List<List_yowall> data;
    View view;
    private OnItemListener listener;


    public MyyowallAdapter(android.content.Context context,List<List_yowall> data){

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

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_yowall, parent, false);
        return new SendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SendViewHolder viewHolder = (SendViewHolder) holder;
        viewHolder.textView_message.setText(data.get(position).getMessage());

        viewHolder.imageView.setVisibility(View.VISIBLE);



        String i  = Time.timeAgo(Time.StringtoLong(data.get(position).getTime()));
        if(data.get(position).getphoto().equals("")){
            viewHolder.imageView.setVisibility(View.GONE);
        }else{
            ((Myyowall)context).getyowallphoto(viewHolder.imageView, data.get(position).getphoto());
        }
        viewHolder.textView_location.setText(i +" "+ data.get(position).getLocation());
        viewHolder.textView_my_yo.setText(data.get(position).getyo()+"");
        viewHolder.textView_my_love.setText(data.get(position).getlove()+"");
        viewHolder.textView_my_hoping.setText(data.get(position).getHoping() + "");

        if(data.get(position).getisYo()){
            viewHolder.imageView_yo.setImageResource(R.mipmap.my_yo);
        }
        else{
            viewHolder.imageView_yo.setImageResource(R.mipmap.send_3);
        }

        if(data.get(position).getisLove()){
            viewHolder.imageView_love.setImageResource(R.mipmap.my_love);
        }
        else{
            viewHolder.imageView_love.setImageResource(R.mipmap.heart_4);
        }

        if(data.get(position).getisHoping()){
            viewHolder.imageView_hoping.setImageResource(R.mipmap.my_hoping_36);
        }
        else{
            viewHolder.imageView_hoping.setImageResource(R.mipmap.wish_3);
        }
        // viewHolder.textView_id.setTextSize(20);


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
        TextView textView_my_hoping;


        public SendViewHolder(final View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.imageView_yowall);
            imageView_yo =(ImageView)itemView.findViewById(R.id.imageView_my_yo);
            imageView_love =(ImageView)itemView.findViewById(R.id.imageView_my_love);
            imageView_hoping =(ImageView)itemView.findViewById(R.id.imageView_my_hoping);
            textView_message = (TextView)itemView.findViewById(R.id.textView_yowall_message);
            textView_location =(TextView)itemView.findViewById(R.id.textView_location);
            textView_my_yo = (TextView)itemView.findViewById(R.id.textView_my_yo);
            textView_my_love = (TextView)itemView.findViewById(R.id.textView_my_love);
            textView_my_hoping = (TextView)itemView.findViewById(R.id.textView_my_hoping);

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

