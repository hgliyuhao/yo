package com.example.lenovo.yot;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lenovo on 2017/10/25.
 */
public class AboutPlayActivity extends BaseActivity {

    ImageView back;

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                //将侧边栏顶部延伸至status bar

            }
        }

        setContentView(R.layout.aboutplay);
        //得到屏幕大小


        back = (ImageView)findViewById(R.id.back);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AboutPlayActivity.this.finish();
            }
        });

    }


}
