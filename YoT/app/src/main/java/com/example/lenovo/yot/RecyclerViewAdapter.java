package com.example.lenovo.yot;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/7/24.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;
    private List<List_send> data;
    View view;
    private OnItemListener listener;

    private final int First_VIEW = 1;
    private final int Normal_VIEW = 2;


    TextView textView_refresh;
    TextView time;
    ImageView imageView_plane;

    public RecyclerViewAdapter(Context context,List<List_send> data){
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

        if(viewType==First_VIEW){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_yo_first, parent, false);
            return new ReceiveViewHolder(view);
        }
        else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_yo, parent, false);
            return new SendViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return First_VIEW;
        }
        else{
            return Normal_VIEW;
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {



        if (holder instanceof SendViewHolder) {
            SendViewHolder viewHolder = (SendViewHolder) holder;
            final String Id;
            Id = data.get(position).getid();
            viewHolder.imageView_tx.setImageResource(data.get(position).getidResource());
            viewHolder.textView_id.setText(data.get(position).getid());
            viewHolder.imageView_tx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).ToIsee(Id);
                }
            });

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



        ImageView imageView_tx;
        TextView textView_id;
        TextView textView_send_distance;
        ImageView imageView_yo;
        public SendViewHolder(final View itemView) {
            super(itemView);

            imageView_tx = (ImageView) itemView.findViewById(R.id.imageView_send_tx);
            textView_id =(TextView)itemView.findViewById(R.id.textView_send_id);

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

            textView_refresh = (TextView)itemView.findViewById(R.id.textView_refresh);
            time = (TextView)itemView.findViewById(R.id.textView_refresh_time);
            imageView_plane = (ImageView)itemView.findViewById(R.id.imageView_recycler_plane);

            //textView_refresh.setVisibility(View.INVISIBLE);
            textView_refresh.setText("下拉刷新");
            time.setVisibility(View.INVISIBLE);
            imageView_plane.setVisibility(View.INVISIBLE);



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


    public void refresh(int x){
        anim_p1(imageView_plane, x);
    }

    private void anim_p1(final ImageView imageView,final int x){

        //得到控件与屏幕底部的距离

        imageView.setVisibility(View.VISIBLE);

        ValueAnimator mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                float animatorValue =(float)  animation.getAnimatedValue();

                textView_refresh.setVisibility(View.INVISIBLE);
                imageView.setX((x*animatorValue));
                imageView.setY( (imageView.getTop()+20-60*animatorValue));

                imageView.setRotation(animatorValue * 20);
              //  imageView.setRotationY(animatorValue * -45);




            }
        });
        mAnimator.setDuration(1000);
        mAnimator.setTarget(imageView);
        mAnimator.start();

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {



            }

            @Override
            public void onAnimationEnd(Animator animation) {

                    textView_refresh.setText("有7个人yo了你！");
                    anim_p2(textView_refresh);
                    anim_p2(time);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }
    private void anim_p2(final TextView textView){

        //得到控件与屏幕底部的距离

        textView.setVisibility(View.VISIBLE);

        final ValueAnimator mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                float animatorValue = (float) animation.getAnimatedValue();

              textView.setAlpha(animatorValue*2);


            }
        });
        mAnimator.setDuration(2000);
        mAnimator.setTarget(textView);
        mAnimator.start();

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                textView.setVisibility(View.INVISIBLE);
                ((MainActivity) context).set0();
                textView_refresh.setText("");
                textView_refresh.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }
    public void setTextView_refresh(){
        textView_refresh.setText("下拉刷新");
    }


}
