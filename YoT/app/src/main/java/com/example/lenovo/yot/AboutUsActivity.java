package com.example.lenovo.yot;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lenovo on 2017/10/24.
 */
public class AboutUsActivity extends BaseActivity {


    TextView textView_yo;
    TextView textView_contect;
    TextView textView_update;
    ImageView update;
    ImageView back;
    LinearLayout lin_yo;
    LinearLayout lin_update;
    LinearLayout lin_contect;



    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                //将侧边栏顶部延伸至status bar

            }
        }

        setContentView(R.layout.aboutus);
        //得到屏幕大小

        textView_yo = (TextView)findViewById(R.id.textView_about_yo);
        textView_update =(TextView)findViewById(R.id.textView_update);
        textView_contect = (TextView)findViewById(R.id.textView_contect);
        back = (ImageView)findViewById(R.id.back);
        update = (ImageView)findViewById(R.id.imageView_update);
        lin_contect = (LinearLayout)findViewById(R.id.lin_contect);
        lin_update = (LinearLayout)findViewById(R.id.lin_update);
        lin_yo = (LinearLayout)findViewById(R.id.lin_yo);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutUsActivity.this.finish();
            }
        });

        lin_yo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AboutUsActivity.this, AboutPlayActivity.class);
                startActivity(intent);

            }
        });

       lin_contect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, AboutContactActivity.class);
                startActivity(intent);
            }
        });
       lin_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
