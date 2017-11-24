package com.example.lenovo.yot;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.hyphenate.chat.EMClient;

/**
 * Created by lenovo on 2017/11/8.
 */
public class SetActivity extends BaseActivity {

    ImageView back;
    Button button;

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                //将侧边栏顶部延伸至status bar

            }
        }

        setContentView(R.layout.set);
        //得到屏幕大小


        back = (ImageView)findViewById(R.id.back);
        button = (Button)findViewById(R.id.button_quit);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetActivity.this.finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EMClient.getInstance().logout(true);
                Log.e("yo", "退出成功");


                Num_send.setNum_send(1);

                /*
                editor_name.putString("name", "");
                editor_name.commit();

                editor_head.putString("head", "");
                editor_head.commit();

                editor_times.putInt("times", 0);
                editor_times.commit();

                setSharedPreferences("");*/


                Intent intent = new Intent(SetActivity.this, LoginActivity.class);

                //在此activity启动之前，任何与此activity相关联的task都会被清除。也就是说，
                // 此activity将变成一个空栈中新的最底端的activity，所有的旧activity都会被finish掉，
                // 这个标识仅仅和FLAG_ACTIVITY_NEW_TASK联合起来才能使用。
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("quit", "0");
                startActivity(intent);
                finish();
                //intent.putExtra("quit","0");
                //startActivity(intent);
                //finish();



            }
        });
    }




}