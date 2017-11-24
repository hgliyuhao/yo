package com.example.lenovo.yot;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by lenovo on 2017/8/10.
 */
public class WelcomeActivity extends AppCompatActivity {

    int displayWidth;
    int displayHeight;
    double r1;
    double r2;
    ImageView imageView_plane;
    ImageView imageView_head;
    ImageView imageView_hill;

    int p2_x;
    int p2_y;
    double p2_r;

    float start;






    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        //得到屏幕大小
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;

        imageView_plane = (ImageView)findViewById(R.id.imageView_plane);
        imageView_head = (ImageView)findViewById(R.id.imageView_welcome_head);
        imageView_hill = (ImageView)findViewById(R.id.imageView_welcome_hill);


        start = 0.3f;

        imageView_head.setScaleX(start);
        imageView_head.setScaleY(start);
        imageView_head.setAlpha(start);
        r1 = 0.5*displayWidth;
        r2 = 0.2*displayWidth;

        p2_y =90;
        p2_x =displayWidth/2;
        p2_r =0.6*displayWidth;

        imageView_plane.setVisibility(View.INVISIBLE);

        anim_p1(imageView_plane);



    }


    private void anim_p1(final ImageView imageView){

        //得到控件与屏幕底部的距离

        imageView.setVisibility(View.VISIBLE);
        ValueAnimator mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                float animatorValue =(float)  animation.getAnimatedValue();
                double angle = animatorValue*3;

                imageView.setX((float) (0.8 * displayWidth - r1 * Math.cos(angle)));
                imageView.setY((float) (0.75 * displayHeight - r1 * Math.sin(angle) * 3 / 2));

                imageView.setRotation(animatorValue * 70 * 2);
                imageView.setRotationY(animatorValue * -45);

                imageView.setScaleX(1 - 0.4f * animatorValue);
                imageView.setScaleY(1 - 0.4f * animatorValue);

                imageView.setAlpha((float) (1 - animatorValue * 0.4));

               // imageView_hill.setScaleX(1-animatorValue*0.4f);
                imageView_hill.setScaleY(1-animatorValue*0.2f);
                imageView_hill.setAlpha((float) (1 - animatorValue * 0.4));

                //imageView_head.setScaleX(start + 0.1f * animatorValue);
                //imageView_head.setScaleY(start + 0.1f * animatorValue);
                //imageView_head.setAlpha(start+0.1f*animatorValue);

            }
        });
        mAnimator.setDuration(2500);
        mAnimator.setInterpolator(new AnticipateInterpolator());
        mAnimator.setTarget(imageView);
        mAnimator.start();

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                imageView.setRotation(0);
                imageView.setRotationY(0);
                anim_p2(imageView_plane);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void anim_p2(final ImageView imageView){


        ValueAnimator mAnimator = ValueAnimator.ofFloat(0, 1);

        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                float animatorValue = (float) animation.getAnimatedValue();

                double angle = animatorValue * 90;

                imageView.setX((float) (p2_x + 3 / 2 * p2_r * Math.cos(Math.toRadians(angle))));
                imageView.setY((float) (p2_y + p2_r - p2_r * Math.sin(Math.toRadians(angle))));
                imageView.setRotation(-animatorValue * 90);
                imageView.setRotationY(animatorValue * 30);

                imageView.setScaleX(0.6f + 0.4f * animatorValue);
                imageView.setScaleY(0.6f + 0.4f * animatorValue);

                // imageView_hill.setScaleX(0.6f-animatorValue * 0.3f);
                imageView_hill.setScaleY(0.8f - animatorValue * 0.15f);

                imageView_hill.setAlpha((float) (0.6 - animatorValue * 0.3));


                imageView_head.setScaleX(start + 0.6f * animatorValue);
                imageView_head.setScaleY(start + 0.6f * animatorValue);
                imageView_head.setAlpha(start + 0.6f * animatorValue);

            }
        });
        mAnimator.setDuration(1000);
       // mAnimator.setInterpolator(new AnticipateInterpolator());
        mAnimator.setTarget(imageView);
        mAnimator.start();

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                anim_p3(imageView_plane);
                startShakeByViewAnim(imageView_head, 0.95f, 1f, 0.8f, 500);
                sound();


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }


    private void anim_p3(final ImageView imageView){


        ValueAnimator mAnimator = ValueAnimator.ofFloat(0, 1);

        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                float animatorValue = (float) animation.getAnimatedValue();

                imageView_plane.setAlpha(1 - animatorValue);

            }
        });
        mAnimator.setDuration(300);
        // mAnimator.setInterpolator(new AnticipateInterpolator());
        mAnimator.setTarget(imageView);
        mAnimator.start();

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                Handler handler= new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i=new Intent(WelcomeActivity.this,MainActivity.class);
                        WelcomeActivity.this.startActivity(i);
                        WelcomeActivity.this.finish();

                    }
                },1000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void startShakeByViewAnim(View view, float scaleSmall, float scaleLarge, float shakeDegrees, long duration) {
        if (view == null) {
            return;
        }

        //由小变大
        Animation scaleAnim = new ScaleAnimation(scaleSmall, scaleLarge, scaleSmall, scaleLarge);
        //从左向右
        Animation rotateAnim = new RotateAnimation(-shakeDegrees, shakeDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        scaleAnim.setDuration(duration);
        rotateAnim.setDuration(duration / 10);
        rotateAnim.setRepeatMode(Animation.REVERSE);
        rotateAnim.setRepeatCount(10);

        AnimationSet smallAnimationSet = new AnimationSet(false);
        smallAnimationSet.addAnimation(scaleAnim);
        smallAnimationSet.addAnimation(rotateAnim);

        view.startAnimation(smallAnimationSet);
    }



    private void sound(){
        MediaPlayer mPlayer =MediaPlayer.create(this,R.raw.yo_3);
        mPlayer.start();
    }



}
