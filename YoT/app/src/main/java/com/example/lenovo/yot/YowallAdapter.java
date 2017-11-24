package com.example.lenovo.yot;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/9/15.
 */
public class YowallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public Context context;
    private List<List_yowall> data;
    View view;
    private OnItemListener listener;

    Boolean isYo;
    Boolean isHoping;
    Boolean isLove;

    private final  String tag_yo ="1";
    private final  String tag_love ="2";
    private final  String tag_hoping ="3";

    int num_Yo;
    int num_Love;
    int num_Want;

    private TextView textView_yo;
    private TextView textView_love;
    private TextView textView_want;


    public YowallAdapter(android.content.Context context,List<List_yowall> data){
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final SendViewHolder viewHolder = (SendViewHolder) holder;
        viewHolder.textView_message.setText(data.get(position).getMessage());

        viewHolder.imageView.setVisibility(View.VISIBLE);

       final String yowallid = Integer.toString(data.get(position).getPosition());
       final int num_yo = data.get(position).getyo();
       final int num_love = data.get(position).getlove();
       final int num_want = data.get(position).getHoping();



        String i  = Time.timeAgo(Time.StringtoLong(data.get(position).getTime()));
        if(data.get(position).getphoto().equals("")){
           viewHolder.imageView.setVisibility(View.GONE);
        }else{
            ((MainActivity)context).getyowallphoto(viewHolder.imageView, data.get(position).getphoto());
        }

        textView_yo = viewHolder.textView_my_yo;
        textView_love =viewHolder.textView_my_love;
        textView_want =viewHolder.textView_my_want;


        viewHolder.textView_location.setText(i +" "+ data.get(position).getLocation());
        viewHolder.textView_my_yo.setText(data.get(position).getyo()+"");
        viewHolder.textView_my_love.setText(data.get(position).getlove()+"");
        viewHolder.textView_my_want.setText(data.get(position).getHoping()+"");

       if(data.get(position).getisYo()){
            viewHolder.imageView_yo.setImageResource(R.mipmap.yo_5);
        }
        else{
            viewHolder.imageView_yo.setImageResource(R.mipmap.send_3);
        }

        if(data.get(position).getisLove()){
            viewHolder.imageView_love.setImageResource(R.mipmap.heart_5);
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



        isYo = data.get(position).getisYo();
        isLove = data.get(position).getisLove();
        isHoping = data.get(position).getisHoping();


       viewHolder.imageView_yo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).getisYo()){
                    data.get(position).setisYo(false);
                    data.get(position).setyo(data.get(position).getyo() - 1);
                    viewHolder.imageView_yo.setImageResource(R.mipmap.send_3);
                    viewHolder.textView_my_yo.setText(data.get(position).getyo() + "");
                    ((MainActivity)context).changeyowall("3", yowallid);
                    ((MainActivity) context).changeyowallnum(yowallid, Integer.toString(num_yo), Integer.toString(num_love), Integer.toString(num_Want), "4");

                }
                else{
                    data.get(position).setisYo(true);
                    data.get(position).setyo(data.get(position).getyo() + 1);
                    viewHolder.imageView_yo.setImageResource(R.mipmap.yo_5);
                    viewHolder.textView_my_yo.setText(data.get(position).getyo() + "");
                    ((MainActivity)context).changeyowall("0", yowallid);
                    ((MainActivity) context).changeyowallnum(yowallid, Integer.toString(num_yo), Integer.toString(num_love), Integer.toString(num_want), "1");

                }
            }
       });

        viewHolder.imageView_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).getisLove()){
                    viewHolder.imageView_love.setImageResource(R.mipmap.heart_4);
                    data.get(position).setisLove(false);
                    data.get(position).setlove(data.get(position).getlove() - 1);
                    viewHolder.textView_my_love.setText(data.get(position).getlove() + "");
                    ((MainActivity)context).changeyowall("4", yowallid);
                    ((MainActivity)context).changeyowallnum(yowallid, Integer.toString(num_yo), Integer.toString(num_love), Integer.toString(num_want), "5");
                }
                else{
                    viewHolder.imageView_love.setImageResource(R.mipmap.heart_5);
                    data.get(position).setisLove(true);
                    data.get(position).setlove(data.get(position).getlove() + 1);
                    viewHolder.textView_my_love.setText(data.get(position).getlove() + "");
                    ((MainActivity)context).changeyowall("1", yowallid);
                    ((MainActivity)context).changeyowallnum(yowallid, Integer.toString(num_yo), Integer.toString(num_love), Integer.toString(num_want), "2");

                }
            }
        });

        viewHolder.imageView_hoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (data.get(position).getisHoping()) {
                    viewHolder.imageView_hoping.setImageResource(R.mipmap.wish_3);
                    data.get(position).setisHoping(false);
                    data.get(position).setHoping(data.get(position).getHoping() - 1);
                    viewHolder.textView_my_want.setText(data.get(position).getHoping() + "");
                    ((MainActivity) context).changeyowall("5", yowallid);
                    ((MainActivity) context).changeyowallnum(yowallid, Integer.toString(num_yo), Integer.toString(num_love), Integer.toString(num_want), "6");


                    Log.e("yo","56");


                } else {
                    viewHolder.imageView_hoping.setImageResource(R.mipmap.my_hoping_36);
                    data.get(position).setisHoping(true);
                    data.get(position).setHoping(data.get(position).getHoping() + 1);
                    viewHolder.textView_my_want.setText(data.get(position).getHoping() + "");
                    ((MainActivity) context).changeyowall("2", yowallid);
                    ((MainActivity) context).changeyowallnum(yowallid, Integer.toString(num_yo), Integer.toString(num_love), Integer.toString(num_want), "3");

                    Log.e("yo", "23");
                }
            }
        });





        Log.e("yo", ""+data.get(position).getPosition());


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

            imageView = (ImageView)itemView.findViewById(R.id.imageView_yowall);
            imageView_yo =(ImageView)itemView.findViewById(R.id.imageView_my_yo);
            imageView_love =(ImageView)itemView.findViewById(R.id.imageView_my_love);
            imageView_hoping =(ImageView)itemView.findViewById(R.id.imageView_my_hoping);
            textView_message = (TextView)itemView.findViewById(R.id.textView_yowall_message);
            textView_location =(TextView)itemView.findViewById(R.id.textView_location);
            textView_my_yo = (TextView)itemView.findViewById(R.id.textView_my_yo);
            textView_my_love = (TextView)itemView.findViewById(R.id.textView_my_love);
            textView_my_want = (TextView)itemView.findViewById(R.id.textView_my_hoping);

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


    private void sendinfoToMain(){

    }
    private void show(final View view) {

        //得到控件与屏幕底部的距离
        int Height;
        Height = 20;

        ValueAnimator mAnimator = ValueAnimator.ofInt(-Height, 0);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                int animatorValue = (int) animation.getAnimatedValue();
                view.setTranslationY(animatorValue);

            }
        });
        mAnimator.setDuration(300);
        mAnimator.setTarget(view);
        mAnimator.start();
    }


}
