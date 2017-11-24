package com.example.lenovo.yot;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lenovo on 2017/6/30.
 */
public class Fragment_welcome extends Fragment {

    TextView textView_welcome;
    ImageView imageView_welcome;
    String string;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment_welcome = inflater.inflate(R.layout.welcome_fragment,
                container, false);



        textView_welcome = (TextView)fragment_welcome.findViewById(R.id.textView_welcome);
        imageView_welcome = (ImageView)fragment_welcome.findViewById(R.id.imageView_welcome);
        string =((LoginActivity)getActivity()).getSharedPreferences();

        ((LoginActivity)getActivity()).gethead(imageView_welcome,((LoginActivity) getActivity()).userId);
     /*   Uri imageUri = Uri.parse(string);
        if(string.equals("")){
            imageView_welcome.setImageResource(R.mipmap.head_yo);
        }
        else{
            imageView_welcome.setImageBitmap(((LoginActivity) getActivity()).getBitmapFromUri(imageUri));
        }

*/
        textView_welcome.setVisibility(View.INVISIBLE);
        translateAnim(imageView_welcome);


        return fragment_welcome;
    }

    private void translateAnim(final View view){

        /**
         * @param fromAlpha 开始的透明度，取值是0.0f~1.0f，0.0f表示完全透明， 1.0f表示和原来一样
         * @param toAlpha 结束的透明度，同上
         */
        AlphaAnimation  animation = new AlphaAnimation(0f, 1.0f);
        //设置动画持续时长
        animation.setDuration(1000);
        animation.setFillAfter(false);
        view.startAnimation(animation);
        animation.startNow();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (view == imageView_welcome) {
                    textView_welcome.setVisibility(View.VISIBLE);
                    translateAnim(textView_welcome);
                }


            }
        });

    }





}
