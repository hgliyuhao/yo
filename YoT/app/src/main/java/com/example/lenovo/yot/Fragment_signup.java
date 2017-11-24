package com.example.lenovo.yot;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by lenovo on 2017/5/29.
 */
public class Fragment_signup extends Fragment {

    EditText editText_userId;
    EditText editText_password;
    EditText editText_mm;
    Button button_signup;
    Boolean isRight;
    Button button_getyanzheng;
    String code;
    Boolean isAnim;


    private Callbacks mCallbacks;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment_signup = inflater.inflate(R.layout.signup_fragment,
                container, false);


        editText_userId = (EditText)fragment_signup.findViewById(R.id.signup_editTex_userId);
        editText_password = (EditText)fragment_signup.findViewById(R.id.signup_editTex_password);
        editText_mm = (EditText)fragment_signup.findViewById(R.id.signup_editTex_mm);
        button_signup = (Button)fragment_signup.findViewById(R.id.button_signup);
        button_getyanzheng = (Button)fragment_signup.findViewById(R.id.button_getyanzheng);

        //后台功能判断验证码，应该为false，测试时候为true
        isRight = true;

        isAnim = false;




        editText_userId.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText_userId.length() < 11) {
                    //限制长度
                    mCallbacks.sendUserId("*请输入您的手机号");

                }
                else if(editText_userId.length() == 11){

                    mCallbacks.sendUserId("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (editText_userId.length() > 11) {
                    //限制长度
                    mCallbacks.sendUserId("*请输入正确的手机号");
                }
                else if(editText_userId.length() == 11){

                    mCallbacks.sendUserId("");
                }


            }
        });

        editText_userId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //editText_userId.setHint("");
                    mCallbacks.sendUserId("");


                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });

        editText_mm.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCallbacks.sendUserId("*密码由小写字母和数字组成");
            }

            @Override
            public void afterTextChanged(Editable s) {

                mCallbacks.sendUserId("");
            }
        });


        button_getyanzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现动画效果，上传手机号，请求验证码
                if(!isAnim){
                button_change(button_getyanzheng);
                //验证码
              ((LoginActivity)getActivity()).getYanzheng(editText_userId.getText().toString().trim());
                isAnim = true;
                }
            }
        });





        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                code = ((LoginActivity) getActivity()).code;
               // Log.e("yo", code);

                if(editText_userId.getText().toString().trim().equals("")){

                     mCallbacks.sendUserId("*请输入手机号");
                }
                else if(editText_userId.getText().toString().trim().length()!=11){

                    mCallbacks.sendUserId("*请输入正确的手机号");
                }
                else if(editText_password.getText().toString().trim().equals("")){
                    mCallbacks.sendUserId("*请输入验证码");
                }
                else if(editText_mm.getText().toString().trim().equals("")){
                    mCallbacks.sendUserId("*请输入密码");
                }
                //验证码部分
              /* else if(!editText_password.getText().toString().trim().equals(code)){
                    //判断验证码正确性
                    mCallbacks.sendUserId("*验证码不正确");
                }*/
                else if (!CheckNetworkAvailable.checkNetworkAvailable(getActivity())){
                    mCallbacks.sendUserId("*当前没有网络连接");
                }
                else{
                    //验证码通过后
                    mCallbacks.sendUserinfo(editText_userId.getText().toString().trim(),editText_mm.getText().toString().trim());

                }


            }
        });



        return fragment_signup;
    }







    public interface Callbacks{
        void sendUserId(String textView_zhushi);
        void sendUserinfo(String UserId, String password);
    }

    private void button_change(final Button button){

        //得到控件与屏幕底部的距离
        int Time;
        Time = 60;

        ValueAnimator mAnimator = ValueAnimator.ofInt(Time, 0);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                int animatorValue = (int) animation.getAnimatedValue();
                button.setText(animatorValue+"s");

            }
        });
        mAnimator.setDuration(Time * 1000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setTarget(button);
        mAnimator.start();

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                button.setText("重新获取");
                isAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

}
