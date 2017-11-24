package com.example.lenovo.yot;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by lenovo on 2017/9/14.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    // 捕获返回键的方法2
    @Override
    public void onBackPressed() {
        Log.d("yo", "onBackPressed()");
        super.onBackPressed();
        BaseActivity.this.finish();
    }

}
