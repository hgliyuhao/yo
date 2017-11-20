package com.example.lenovo.yot;

/**
 * Created by lenovo on 2017/7/25.
 */
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2017/5/27.
 */
public class LoginActivity extends BaseActivity implements Fragment_signup.Callbacks,Fragment_login.login_Callbacks,Fragment_sethead.Callbacks,Fragment_name.login_Callbacks,Fragment_sexc.login_Callbacks{

    final float Yo_place = 0.39f;
    final float Denglu_place = 0.39f;
    final float Fragment_place = 0.07f;
    final double ImageView_move = 0.27;
    final float Textzushi_place = 0.24f;
    int width;
    int height;
    int displayWidth;
    int  displayHeight;
    private FragmentManager fragmentManager;
    private Fragment_login fragment_login;
    private Fragment_signup fragment_signup;
    private Fragment_sethead fragment_sethead;
    private Fragment_name fragment_name;
    private Fragment_sexc fragment_sexc;
    private Fragment_welcome fragment_welcome;
    final  String Tag_login = "1";
    final  String Tag_signup = "2";
    final  String Tag_sex = "3";
    final  String Tag_sethead = "4";
    final  String Tag_name = "5";
    final  String Tag_sexc = "6";
    final  String Tag_welcome = "7";
    LinearLayout linearLayout_1;
    LinearLayout linearLayout_2;
    LinearLayout linearLayout_3;
    LinearLayout linearLayout_4;


    Boolean isMovetoleft;
    Boolean isAnim;
    Boolean is_signup;

    TextView textView_Y;
    TextView textView_o;
    TextView textView_denglu;
    TextView textView_zuce;
    TextView textView_zhushi;
    ImageView imageView_heart;
    String string_userId;
    String string_password;

    String string_userName;
    String string_userSex;


    SharedPreferences Sign_up_state;//访问阶段
    SharedPreferences.Editor editor_sign_up_state;
    int sign_up_state;

    SharedPreferences UserId;//
    SharedPreferences.Editor editor_userId;
    String userId;

    SharedPreferences Password;//
    SharedPreferences.Editor editor_password;
    String password;

    int State_num;

    Intent intent1;

    private RequestQueue queue = ((ECApplication)getApplication()).getRequestQueue();//获取请求队列示例
    String Res;

    //为了测试，Server为有服务器，Local为本地模式
    final static int Model  = IsServer.Server;

    String HXpassword;
    String UserTel;

    Bitmap bitmap_getfromServer;


    double r1;
    double r2;
    ImageView imageView_plane;
    ImageView imageView_head;
    ImageView imageView_hill;

    int p2_x;
    int p2_y;
    double p2_r;

    float start;


    //服务器返回码
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);



        WindowManager wm = this.getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;

        findviews();
        isMovetoleft = true;
        isAnim = false;
        fragmentManager = getFragmentManager();

        getAllSharedPreferences();


        State_num = Num_send.getNum_send();

        if(State_num == 0){
            Num_send.setNum_send(1);
            State_num = Num_send.getNum_send();
        }
        else {

            intent1 =getIntent();
            if(intent1 != null){
                if(intent1.getStringExtra("quit").equals("0")){
                    //数据清0
                    sign_up_state = 0;
                    editor_sign_up_state.putInt("sign_up_state", 0);
                    editor_sign_up_state.commit();

                    editor_password.putString("password","");
                    editor_password.commit();

                    editor_userId.putString("userId","" );
                    editor_userId.commit();

                }
            }
        }

        if(sign_up_state == 0){
            setTabSelection(0);
        }
        else if(sign_up_state == 4){


            setContentView(R.layout.welcome);
            //linearLayout_2.setVisibility(View.GONE);
            //textView_zhushi.setVisibility(View.GONE);
            //自定义界面
            //imageView_heart = (ImageView)findViewById(R.id.imageView);
            //imageView_heart.setVisibility(View.GONE);

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

            EMClient.getInstance().login(userId, password, new EMCallBack() {
                /**
                 * 登陆成功的回调
                 */
                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //Log.e("yo",f_getfromServer.toString());
                            // 加载所有会话到内存
                            EMClient.getInstance().chatManager().loadAllConversations();
                            // 加载所有群组到内存，如果使用了群组的话
                            // EMClient.getInstance().groupManager().loadAllGroups();
                           // setTabSelection(6);
                            // 登录成功跳转界面
                            Handler handler= new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    i.putExtra("tel",userId);
                                    Num_send.setTel(userId);
                                    LoginActivity.this.startActivity(i);
                                    LoginActivity.this.finish();

                                }
                            }, 4750);
                        }
                    });
                }

                /**
                 * 登陆错误的回调
                 * @param i
                 * @param s
                 */
                @Override
                public void onError(final int i, final String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("lzan13", "登录失败 Error code:" + i + ", message:" + s);
                            /**
                             * 关于错误码可以参考官方api详细说明
                             * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                             */
                            switch (i) {
                                // 网络异常 2
                                case EMError.NETWORK_ERROR:
                                    Toast.makeText(LoginActivity.this, "网络错误 ", Toast.LENGTH_LONG).show();
                                    break;
                                // 无效的用户名 101
                                case EMError.INVALID_USER_NAME:
                                    Toast.makeText(LoginActivity.this, "无效的用户名", Toast.LENGTH_LONG).show();
                                    break;
                                // 无效的密码 102
                                case EMError.INVALID_PASSWORD:
                                    Toast.makeText(LoginActivity.this, "无效的密码", Toast.LENGTH_LONG).show();
                                    break;
                                // 用户认证失败，用户名或密码错误 202
                                case EMError.USER_AUTHENTICATION_FAILED:
                                    Toast.makeText(LoginActivity.this, "用户认证失败，用户名或密码错误 ", Toast.LENGTH_LONG).show();
                                    break;
                                // 用户不存在 204
                                case EMError.USER_NOT_FOUND:
                                    Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_LONG).show();
                                    break;
                                // 无法访问到服务器 300
                                case EMError.SERVER_NOT_REACHABLE:
                                    Toast.makeText(LoginActivity.this, "无法访问到服务器", Toast.LENGTH_LONG).show();
                                    break;
                                // 等待服务器响应超时 301
                                case EMError.SERVER_TIMEOUT:
                                    Toast.makeText(LoginActivity.this, "等待服务器响应超时", Toast.LENGTH_LONG).show();
                                    break;
                                // 服务器繁忙 302
                                case EMError.SERVER_BUSY:
                                    Toast.makeText(LoginActivity.this, "服务器繁忙", Toast.LENGTH_LONG).show();
                                    break;
                                // 未知 Server 异常 303 一般断网会出现这个错误
                                case EMError.SERVER_UNKNOWN_ERROR:
                                    Toast.makeText(LoginActivity.this, "未知的服务器异常", Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(LoginActivity.this, "ml_sign_in_failed ", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    });
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
        Log.e("Yo",""+sign_up_state );



        textView_denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelection(0);
                textView_zuce.setAlpha(0.5f);
                textView_denglu.setAlpha(1f);
            }
        });
        textView_zuce.setAlpha(0.5f);
        textView_zuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setTabSelection(1);
                textView_denglu.setAlpha(0.5f);
                textView_zuce.setAlpha(1f);

            }
        });


        setLayoutParams(linearLayout_1, (int) (displayWidth * Yo_place + 0.5f));
        setLayoutParams(linearLayout_2, (int) (displayWidth * Denglu_place + 0.5f));
        setLayoutParams(linearLayout_3, (int) (displayWidth * Fragment_place + 0.5f));
        setLayoutParams(linearLayout_4, (int) (displayWidth * Textzushi_place + 0.5f));




    }

    protected void onResume() {
        super.onResume();

        Num_send.setNum_send(0);
        State_num = Num_send.getNum_send();

    }

    private void getAllSharedPreferences() {

        Sign_up_state=getSharedPreferences("sign_up_state", MODE_PRIVATE);
        editor_sign_up_state = Sign_up_state.edit();
        sign_up_state = Sign_up_state.getInt("sign_up_state", 0);
        editor_sign_up_state.apply();

        UserId=getSharedPreferences("userId", MODE_PRIVATE);
        editor_userId = UserId.edit();
        userId = UserId.getString("userId", "");
        editor_userId.apply();

        Password=getSharedPreferences("password", MODE_PRIVATE);
        editor_password = Password.edit();
        password = Password.getString("password", "");
        editor_password.apply();
    }

    private void findviews(){


        textView_denglu = (TextView)findViewById(R.id.login_textView_denglu);
        textView_zuce = (TextView)findViewById(R.id.login_textView_zuce);
        linearLayout_1 =(LinearLayout)findViewById(R.id.login_linearlayout_yo);
        linearLayout_2 =(LinearLayout)findViewById(R.id.login_linearlayout_zuce);
        linearLayout_3 =(LinearLayout)findViewById(R.id.login_linearlayout_fragment);
        linearLayout_4 =(LinearLayout)findViewById(R.id.login_linearlayout_textzhushi);
        textView_zhushi = (TextView)findViewById(R.id.textView_zhushi);

    }

    public void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        // clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色

                if (fragment_login == null) {

                    // 如果为空，则创建一个并添加到界面上
                    fragment_login = new Fragment_login();

                    //初始化主界面
                    transaction.add(R.id.fragment_container,fragment_login,Tag_login);
                } else {
                    // 如果不为空，则直接将它显示出来
                    transaction.show(fragment_login);
                }
                break;
            case 1:

                if (fragment_signup== null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    fragment_signup = new Fragment_signup();
                    transaction.add(R.id.fragment_container, fragment_signup,Tag_signup);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(fragment_signup);
                }
                break;

            case 2:


                break;

            case 3:

                if (fragment_sethead== null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    fragment_sethead = new Fragment_sethead();
                    transaction.add(R.id.fragment_container, fragment_sethead,Tag_sethead);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                }
                break;
            case 4:

                if (fragment_name== null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    fragment_name = new Fragment_name();
                    transaction.add(R.id.fragment_container, fragment_name,Tag_name);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(fragment_name);
                }
                break;
            case 5:

                if (fragment_sexc== null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    fragment_sexc = new Fragment_sexc();
                    transaction.add(R.id.fragment_container, fragment_sexc,Tag_sexc);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(fragment_sexc);
                }
                break;
            case 6:

                if (fragment_welcome== null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    fragment_welcome = new Fragment_welcome();
                    transaction.add(R.id.fragment_container, fragment_welcome,Tag_welcome);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(fragment_welcome);
                }
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (fragment_signup != null) {
            transaction.hide(fragment_signup);
        }
        if (fragment_login != null) {
            transaction.hide(fragment_login);
        }
        if (fragment_sethead !=null){
            transaction.hide(fragment_sethead);
        }
        if (fragment_name !=null){
            transaction.hide(fragment_name);
        }
        if (fragment_sexc !=null){
            transaction.hide(fragment_sexc);
        }
        if (fragment_welcome !=null){
            transaction.hide(fragment_welcome);
        }

    }

    private void resetFragment(Fragment fragment){
        FragmentTransaction transaction;
        transaction = fragmentManager.beginTransaction();
        if(fragment!=null){
            transaction.remove(fragment);
        }
        if(fragment==fragment_signup){
            fragment_signup = new Fragment_signup();
            transaction.add(R.id.fragment_container, fragment_signup, Tag_signup);
        }
        else{
            fragment_login = new Fragment_login();
            transaction.add(R.id.fragment_container, fragment_login, Tag_login);
        }

        transaction.commit();
    }

    private void translateAnim(final ImageView imageView,int m){

        final int move = m;
        TranslateAnimation animation = new TranslateAnimation(0, m, 0, 0);
        animation.setDuration(500);
        imageView.startAnimation(animation);
        animation.startNow();

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                isAnim = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnim = false;
                imageView.clearAnimation();
                if (move > 0) {
                    imageView.setTranslationX(move);
                    isMovetoleft = false;
                } else {
                    imageView.setTranslationX(3);
                    isMovetoleft = true;
                }
            }
        });
    }

    private  void setLayoutParams(LinearLayout linearLayout,int distance){

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        lp.setMargins(distance, 0, 0, 0);
        linearLayout.setLayoutParams(lp);
    }

    public String getSharedPreferences() {
        SharedPreferences sharedPreferences_read = getSharedPreferences("Data", MODE_PRIVATE);
        String string = sharedPreferences_read.getString("string", null);//默认null
        Set<String> set = sharedPreferences_read.getStringSet("set", null);//默认null

        return  string;
    }


    // --------------------------------- 接收Fragment数据 -------------------------------------

    public void login_sendUserId(String UserId){
        textView_zhushi.setText(UserId);
        if(UserId.equals("*用户名不能为空")){
            setLayoutParams(linearLayout_4, (int) (displayWidth * 0.36f + 0.5f));
        }
        else if(UserId.equals("*请输入密码")){
            setLayoutParams(linearLayout_4,(int) (displayWidth * 0.41f + 0.5f));
        }
        else if(UserId.equals("*当前没有网络")){
            setLayoutParams(linearLayout_4,(int) (displayWidth * 0.38f + 0.5f));
        }
    }

    //直接登录的方法
    public  void login_sendUserinfo(final String UserId, final String password){

        getVerify(UserId, password);

    }

    public void sendUserName(String textView_name){
        if(textView_name.equals("")){
            Toast.makeText(LoginActivity.this, "昵称不能为空", Toast.LENGTH_LONG).show();
        }
        else{
            string_userName = textView_name;
            setTabSelection(5);
        }

    }

    public void sendUserSex(String sex){
        setTabSelection(3);
        string_userSex = sex;
    }

    //注册新用户方法

    public void sendUserinfo(String UserId,String password){
        string_userId =UserId;
        UserTel = UserId;
        string_password = password;
        if(Model==0){
            getSeverIsexist(string_userId, string_password, "0");
        }
        else{
            createAccount();
        }

    }

    public void sendUserId(String UserId){
        textView_zhushi.setText(UserId);
        if(UserId.equals("*用户名过长")){
            setLayoutParams(linearLayout_4, (int) (displayWidth * 0.41f + 0.5f));
        }
        else if(UserId.equals("*用户名由小写字母和数字组成")){
            setLayoutParams(linearLayout_4, (int) (displayWidth * 0.24f + 0.5f));
        }
        else if(UserId.equals("*两次输入的密码不一致")){
            setLayoutParams(linearLayout_4,(int) (displayWidth * 0.31f + 0.5f));
        }
        else if(UserId.equals("*再一次输入密码")){
            setLayoutParams(linearLayout_4,(int) (displayWidth * 0.37f + 0.5f));
        }
        else if(UserId.equals("*密码由小写字母和数字组成")){
            setLayoutParams(linearLayout_4,(int) (displayWidth * 0.26f + 0.5f));
        }
        else if(UserId.equals("*请输入密码")){
            setLayoutParams(linearLayout_4,(int) (displayWidth * 0.41f + 0.5f));
        }
        else if(UserId.equals("*请输入您的手机号")){
            setLayoutParams(linearLayout_4,(int) (displayWidth * 0.36f + 0.5f));
        }
        else if(UserId.equals("*请再次输入密码")){
            setLayoutParams(linearLayout_4,(int) (displayWidth * 0.36f + 0.5f));
        }
        else if(UserId.equals("*当前没有网络")){
            setLayoutParams(linearLayout_4,(int) (displayWidth * 0.38f + 0.5f));
        }
        else if(UserId.equals("*用户名已被注册")){
            setLayoutParams(linearLayout_4, (int) (displayWidth * 0.36f + 0.5f));
        }
        else if(UserId.equals("*请输入正确的手机号")){
            setLayoutParams(linearLayout_4, (int) (displayWidth * 0.33f + 0.5f));
        }
        else if(UserId.equals("*请输入验证码")){
            setLayoutParams(linearLayout_4, (int) (displayWidth * 0.38f + 0.5f));
        }
        else if(UserId.equals("*请输入手机号")){
            setLayoutParams(linearLayout_4, (int) (displayWidth * 0.38f + 0.5f));
        }
    }

    public void send_sex(String zhushi){

        textView_zhushi.setText(zhushi);
        if(zhushi.equals("*请选择你的性别")){
            setLayoutParams(linearLayout_4,(int) (displayWidth * 0.36f + 0.5f));
        }
        else if(zhushi.equals(" ")){

            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_LONG).show();
            setTabSelection(0);
            textView_denglu.setTextColor(textView_denglu.getTextColors().withAlpha(255));
            textView_Y.setTextColor(textView_denglu.getTextColors().withAlpha(255));
            textView_o.setTextColor(textView_zuce.getTextColors().withAlpha(155));
            textView_zuce.setTextColor(textView_zuce.getTextColors().withAlpha(80));

            if (!isAnim) {
                if (isMovetoleft) {

                } else {
                    translateAnim(imageView_heart, (-(int) (width * ImageView_move)));
                    //isMovetoleft = true;
                }

            }
        }
    }

    //跳转到MainActivity的函数

    public void gotoMain(Uri uri,File f){

        uploadinfo(string_userId,string_userName,string_userSex);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        if(uri==null)
        {
            intent.putExtra("headimage_uri", "");
        }
        else{
            intent.putExtra("headimage_uri", uri.toString());
        }
        intent.putExtra("userName",string_userName);
        intent.putExtra("userSex",string_userSex);
        intent.putExtra("tel",string_userId);
        Num_send.setTel(string_userId);
        startActivity(intent);
        finish();
    }





    // --------------------------------- 图片处理 -------------------------------------


    public Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



    // --------------------------------- Volley  -------------------------------------

    public void  getSeverIsexist(String tel,String password,String aori){
        final String getTel;
        final String getpassword;
        final String getaori;
        getTel = tel;
        getpassword = password;
        getaori = aori;

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.Login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.substring(0,2).equals(10)){

                            resetFragment(fragment_signup);
                            textView_zhushi.setText("");
                            Toast.makeText(LoginActivity.this, "用户已存在  " , Toast.LENGTH_LONG).show();
                            textView_zhushi.setText(Res);
                        }
                        else {
                            if(response.length()<3){
                                resetFragment(fragment_signup);
                                textView_zhushi.setText("");
                                Toast.makeText(LoginActivity.this, "用户已存在  " , Toast.LENGTH_LONG).show();
                            }
                            else {
                                Res = response.substring(0, 3);
                                if (Res.equals("111")) {
                                    HXpassword = response.substring(3, 14);
                                    createAccount();
                                    Log.e("yo", HXpassword);
                                }
                            }
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tel",getTel);
                map.put("password",getpassword);
                map.put("aori",getaori);

                return map;
            }
        };

        queue.add(stringRequest1);

    }

    public void  uploadinfo(String tel,String name,String sex){
        final String getName;
        final String getTel;
        final String getSex;
        getSex = sex;
        getTel = tel;
        getName = name;


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.uploadinfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tel",getTel);
                map.put("sex",getSex);
                map.put("name",getName);
                return map;
            }
        };

        queue.add(stringRequest1);

    }

    public void  uploadhead(Bitmap bitmap){



        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bStream);
        byte[] bytes=bStream.toByteArray();
        final String string = Base64.encodeToString(bytes,Base64.DEFAULT);
        Log.e("yo",string);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.uploadhead,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tel",UserTel);
                map.put("head",string);
                return map;
            }
        };

        queue.add(stringRequest1);

    }

    public void gethead(final ImageView imageView,final String tel){

        ImageRequest imageRequest = new ImageRequest(
                Host.ip+"YoServer/head"+tel+".jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(imageRequest);
    }

    public void  deleteTel(String tel){
        final String getTel;
        getTel = tel;


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.delete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tel",getTel);
                return map;
            }
        };

        queue.add(stringRequest1);

    }


    //请求得到验证码
    public void getYanzheng(final String tel){



        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.getyanzheng,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       String  after = getInsideString(response,"{","}");
                        Log.e("yo", after);
                       code = after;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tel",tel);
                return map;
            }
        };

        queue.add(stringRequest1);

    }

    private void getVerify(final String tel,final String password){

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.getVerify,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.length()==2){
                            if (response.equals("00")){
                                Toast.makeText(LoginActivity.this, "用户不存在  " , Toast.LENGTH_LONG).show();
                            }
                            else if(response.equals("10")){
                                Toast.makeText(LoginActivity.this, "密码不正确  " , Toast.LENGTH_LONG).show();
                            }
                            else{

                                string_userId =tel;
                                string_password = tel;
                                LoginHx("",tel);
                                //string_userId = "";
                            }
                        }
                        else{
                            string_userId =tel;
                            string_password = tel;
                            LoginHx(response.substring(2),tel);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tel",tel);
                map.put("password",password);
                return map;
            }
        };

        queue.add(stringRequest1);
    }



    // --------------------------------- 环信注册  -------------------------------------
    //注册
    private void createAccount(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    EMClient.getInstance().createAccount(string_userId,HXpassword);

                    editor_password.putString("password",HXpassword);
                    editor_password.commit();

                    editor_userId.putString("userId",string_userId );
                    editor_userId.commit();

                    editor_sign_up_state.putInt("sign_up_state", 4);
                    editor_sign_up_state.commit();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          /*  if (!MainActivity.this.isFinishing()) {
                                mDialog.dismiss();
                            }*/

                            setTabSelection(4);
                            textView_zhushi.setText("");
                            linearLayout_2.setVisibility(View.GONE);
                            textView_zhushi.setVisibility(View.GONE);
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            resetFragment(fragment_signup);
                            textView_zhushi.setText("");
                            int errorCode = e.getErrorCode();
                            String message = e.getMessage();
                            Log.d("lzan13", String.format("sign up - errorCode:%d, errorMsg:%s", errorCode, e.getMessage()));
                            //transaction = fragmentManager.beginTransaction();
                            //transaction.replace(R.id.fragment_container,fragment_signup,Tag_signup);
                            //transaction.commit();
                            switch (errorCode) {
                                // 网络错误
                                case EMError.NETWORK_ERROR:
                                    Toast.makeText(LoginActivity.this, "网络错误  ", Toast.LENGTH_LONG).show();

                                    break;
                                // 用户已存在
                                case EMError.USER_ALREADY_EXIST:
                                    Toast.makeText(LoginActivity.this, "用户已存在  " , Toast.LENGTH_LONG).show();
                                    break;
                                // 参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册
                                case EMError.USER_ILLEGAL_ARGUMENT:
                                    Toast.makeText(LoginActivity.this, "参数不合法", Toast.LENGTH_LONG).show();
                                    deleteTel(string_userId);
                                    break;
                                // 服务器未知错误
                                case EMError.SERVER_UNKNOWN_ERROR:
                                    Toast.makeText(LoginActivity.this, "服务器未知错误 ", Toast.LENGTH_LONG).show();
                                    deleteTel(string_userId);
                                    break;
                                case EMError.USER_REG_FAILED:
                                    Toast.makeText(LoginActivity.this, "账户注册失败 " + message, Toast.LENGTH_LONG).show();
                                    deleteTel(string_userId);
                                    break;
                                default:
                                    Toast.makeText(LoginActivity.this, "ml_sign_up_failed ", Toast.LENGTH_LONG).show();
                                    deleteTel(string_userId);
                                    break;
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //登录
    private void LoginHx(final String name,final  String tel){
        EMClient.getInstance().login(string_userId, string_password, new EMCallBack() {
            /**
             * 登陆成功的回调
             */
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        // 加载所有会话到内存
                        EMClient.getInstance().chatManager().loadAllConversations();
                        // 加载所有群组到内存，如果使用了群组的话
                        // EMClient.getInstance().groupManager().loadAllGroups();
                        // 登录成功跳转界面

                        editor_password.putString("password", string_userId);
                        editor_password.commit();

                        editor_userId.putString("userId", string_userId);
                        editor_userId.commit();

                        editor_sign_up_state.putInt("sign_up_state", 4);
                        editor_sign_up_state.commit();

                        //得到用户名
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userName", name);
                        intent.putExtra("headimage_uri", "");
                        intent.putExtra("tel", tel);

                        startActivity(intent);


                        finish();
                    }
                });
            }

            /**
             * 登陆错误的回调
             * @param i
             * @param s
             */
            @Override
            public void onError(final int i, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("lzan13", "登录失败 Error code:" + i + ", message:" + s);
                        /**
                         * 关于错误码可以参考官方api详细说明
                         * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                         */
                        switch (i) {
                            // 网络异常 2
                            case EMError.NETWORK_ERROR:
                                Toast.makeText(LoginActivity.this, "网络错误 ", Toast.LENGTH_LONG).show();
                                break;
                            // 无效的用户名 101
                            case EMError.INVALID_USER_NAME:
                                Toast.makeText(LoginActivity.this, "无效的用户名", Toast.LENGTH_LONG).show();
                                break;
                            // 无效的密码 102
                            case EMError.INVALID_PASSWORD:
                                Toast.makeText(LoginActivity.this, "无效的密码", Toast.LENGTH_LONG).show();
                                break;
                            // 用户认证失败，用户名或密码错误 202
                            case EMError.USER_AUTHENTICATION_FAILED:
                                Toast.makeText(LoginActivity.this, "用户认证失败，用户名或密码错误 ", Toast.LENGTH_LONG).show();
                                break;
                            // 用户不存在 204
                            case EMError.USER_NOT_FOUND:
                                Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_LONG).show();
                                break;
                            // 无法访问到服务器 300
                            case EMError.SERVER_NOT_REACHABLE:
                                Toast.makeText(LoginActivity.this, "无法访问到服务器", Toast.LENGTH_LONG).show();
                                break;
                            // 等待服务器响应超时 301
                            case EMError.SERVER_TIMEOUT:
                                Toast.makeText(LoginActivity.this, "等待服务器响应超时", Toast.LENGTH_LONG).show();
                                break;
                            // 服务器繁忙 302
                            case EMError.SERVER_BUSY:
                                Toast.makeText(LoginActivity.this, "服务器繁忙", Toast.LENGTH_LONG).show();
                                break;
                            // 未知 Server 异常 303 一般断网会出现这个错误
                            case EMError.SERVER_UNKNOWN_ERROR:
                                Toast.makeText(LoginActivity.this, "未知的服务器异常", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(LoginActivity.this, "ml_sign_in_failed ", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
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



    //服务器返回码截取
    public  String  getInsideString(String  str, String strStart, String strEnd ) {
        if ( str.indexOf(strStart) < 0 ){
            return "";
        }
        if ( str.indexOf(strEnd) < 0 ){
            return "";
        }

        String mid = str.substring(str.indexOf(strStart) + strStart.length(), str.indexOf(strEnd));
        int code_long = 4;
        return mid.substring(mid.length()-1-code_long,mid.length()-1);
    }

}
