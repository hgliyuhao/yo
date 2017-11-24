package com.example.lenovo.yot;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by lenovo on 2017/6/23.
 */
public class Fragment_sexc extends Fragment{

    ImageView imageView_boy;
    ImageView imageView_girl;
    Button button;
    Boolean Flag;
    private login_Callbacks callbacks;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (login_Callbacks) context;
    }
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View fragment_sexc = inflater.inflate(R.layout.sexc_fragment,
                    container, false);

            button = (Button)fragment_sexc.findViewById(R.id.button_gotohead);

            imageView_boy = (ImageView)fragment_sexc.findViewById(R.id.imageView_boy);
            imageView_girl = (ImageView)fragment_sexc.findViewById(R.id.imageView_girl);


            Flag = false;

            imageView_boy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView_boy.setAlpha(1f);
                    imageView_girl.setAlpha(0.2f);
                    Flag = true;
                }
            });
            imageView_girl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    imageView_boy.setAlpha(0.2f);
                    imageView_girl.setAlpha(1f);
                    Flag = true;
                }
            });




            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //得到上面的性别
                    callbacks.sendUserSex("1");
                }
            });

            return fragment_sexc;
        }

    public interface login_Callbacks{
        void sendUserSex(String sex);

    }
}
