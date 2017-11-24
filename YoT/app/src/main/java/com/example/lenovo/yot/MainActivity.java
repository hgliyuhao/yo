package com.example.lenovo.yot;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends CheckPermissionsActivity implements  View.OnTouchListener,EMMessageListener {

    //得到屏幕大小
    int displayWidth;
    int displayHeight;

    //toolbar
    private Toolbar toolbar;
    private ImageView imageView_set;

    //layout及三个标题
    private LinearLayout linearLayout_title;
    private LinearLayout linearLayout_set;
    private TextView textView_yo;
    private TextView textView_fri;
    private TextView textView_yowall;

    //颜色常量

    static String Color1 = "#4c635b";
    static String Color2 = "#012a40";
    static String Color3 = "#a12525";

    //  ViewPage相关变量
    private View view1, view2, view3;
    private ViewPager viewPager;
    private List<View> viewList;//view数组

    public int postion_state;


    //RecyclerView相关变量

    private List<List_send> datas;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    private List<List_friend> friend_datas;
    private RecyclerView mRecyclerView_friend;
    private ToFriendActivity mAdapter_friend;


    private List<List_yowall> yowall_datas;
    private RecyclerView mRecyclerView_yo;
    private YowallAdapter mAdapter_yo;

    //刷新栏高度
    private int HeaderHeight;
    private boolean isGetHeaderHeight;
    View view_refresh;
    int recycler_state;//0为隐藏刷新，1为正常


    //设置朋友下拉菜单
    View friendwindow;
    PopupWindow friend_window;

    int friendtotalMove;


    Upload upload;

    int lastX;
    int lastY;
    long lastTime;

    int Send_postion;
    int send_visibleItemCount;
    private List<EMConversation> conversationList = new ArrayList<EMConversation>();

    //RecyclerView点击动画

    private ImageView imageView_yo_send;
    private ImageView imageView_yo_cancel;


    //FloatingButton
    private FloatingActionButton floatingActionButton_yo;
    private FloatingActionButton floatingActionButton_message;
    private FloatingActionButton floatingActionButton_wall;
    private boolean isShow_floating_yo;
    private boolean isShow_floating_message;
    private boolean isFirst;
    private static int DistanceMove = 20;
    private int lastpostion;

    //DrawerLayout侧滑菜单
    private DrawerLayout mDrawerLayout;
    private LinearLayout linearLayout_left;
    private RecyclerView mD_RecyclerView;
    private DRecyclerViewAdapter mDAdapter;
    private List<List_set> data;
    private ImageView imageView_DrawLayouthead;
    private TextView textView_DrawLayoutname;
    private TextView textView_DrawLayoutsetname;


    SwipeRefreshLayout swipeRefreshLayout;
    boolean isRefresing;

    //图像处理常数
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
    private static final int CODE_CAMERA_REQUEST = 0xa1;//拍照
    private static final int CODE_RESULT_REQUEST = 0xa2;//最终裁剪后的结果
    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 300;
    private static int output_Y = 300;

    private int totalScrollDistance;


    //悬浮窗


    //访问次数
    SharedPreferences Times;//
    SharedPreferences.Editor editor_times;
    int times;

    //头像本地数据
    SharedPreferences Head;//
    SharedPreferences.Editor editor_head;
    String head;

    //昵称
    SharedPreferences Name;//
    SharedPreferences.Editor editor_name;
    String name;

    //Tel
    SharedPreferences Tel;//
    SharedPreferences.Editor editor_tel;
    String tel;

    //获取请求队列示例
    private RequestQueue queue = ((ECApplication) getApplication()).getRequestQueue();

    //动态权限常数
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;


    ECApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //设置浸入式样式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                //将侧边栏顶部延伸至status bar
                mDrawerLayout.setFitsSystemWindows(true);
                //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
                mDrawerLayout.setClipToPadding(false);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        //开启消息监听server
        Intent i = new Intent(this, PushService.class);
        startService(i);

        //得到屏幕大小
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;

        getPermission();

        findview();
        //获取数据
        initDatas();
        //viewPager处理
        viewpager();
        //设置RecyclerView

        Intent intent1;
        intent1 = getIntent();

        if((intent1.getStringExtra("state")).equals("quit")){
            //如果状态是quit把数据全部置0
            editor_name.putString("name", "");
            editor_name.commit();

            editor_head.putString("head", "");
            editor_head.commit();

            editor_times.putInt("times", 0);
            editor_times.commit();

        }

        setToolbar();
        setDrawLayout();

        setFloatingButton();

        //得到回调

    }


    /* 自定义实现Handler，主要用于刷新UI操作
*/
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    EMMessage message = (EMMessage) msg.obj;
                    //获得头像数据
                    //数据处理
                    //List_send(int position, int idResource, String id,  String time,double distance, int relationship)
                    List_send list1 = new List_send(1, R.mipmap.tx_xiao2_150, "admin", "3", 1, 2);
                    String id = message.getFrom().toString().trim();
                    long time = message.getMsgTime();
                    Log.e("yo", time + "" + Time.timeAgo(time));
                    //List_send list_new = new  List_send(0,R.mipmap.tx_xiao2_150,message.getFrom().toString().trim(),"","1",1,10,1);
                    //textView_id.setText("\n" + message.getFrom().toString().trim()+message.getMsgTime());
                    //要通过tel去取对应的id


                    message.getStringAttribute("location", application.Location_short);
                    message.getStringAttribute("Latitude", application.Location_Latitude + "");
                    message.getStringAttribute("Longitude", application.Location_Longitude + "");
                    //计算位置得到结果给distance
                    //sendNotification();
                    addtoSendrecycler(list1);
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        // 添加消息监听
        EMClient.getInstance().chatManager().addMessageListener(this);
        //应该刷新yo列表
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 移除消息监听
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }


    //找到控件
    private void findview() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        linearLayout_title = (LinearLayout) findViewById(R.id.linearlayout_title);
        linearLayout_set = (LinearLayout) findViewById(R.id.linearlayout_set);
        textView_yo = (TextView) findViewById(R.id.textView_yo);
        textView_fri = (TextView) findViewById(R.id.textView_fri);
        textView_yowall = (TextView) findViewById(R.id.textView_wall);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        floatingActionButton_yo = (FloatingActionButton) findViewById(R.id.floatingActionButton_yo);
        floatingActionButton_message = (FloatingActionButton) findViewById(R.id.floatingActionButton_message);
        floatingActionButton_wall = (FloatingActionButton) findViewById(R.id.floatingActionButton_wall);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        imageView_set = (ImageView) findViewById(R.id.imageView_set);
        linearLayout_left = (LinearLayout) findViewById(R.id.linearlayout_left);
        textView_DrawLayoutname = (TextView) findViewById(R.id.textView_name);
        textView_DrawLayoutsetname = (TextView) findViewById(R.id.textView_setname);
        imageView_DrawLayouthead = (ImageView) findViewById(R.id.imageView_set_head);

    }

    //viewPager处理
    private void viewpager() {


        setPopupwindow();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.viewpage_yo, null);
        view2 = inflater.inflate(R.layout.viewpage_friend, null);
        view3 = inflater.inflate(R.layout.viewpage_will, null);
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        final PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));

                return viewList.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view1.findViewById(R.id.swiperefreshlayout);
        mRecyclerView = (RecyclerView) view1.findViewById(R.id.recyclerView1);
        mRecyclerView_friend = (RecyclerView) view2.findViewById(R.id.recyclerView2);
        mRecyclerView_yo = (RecyclerView) view3.findViewById(R.id.recyclerView3);
        setmRecyclerView();
        setmRecyclerView_yo();
        setmRecyclerView_friend();
        setRefresh();

        //上拉刷新


        /**
         * 滑动监听器OnPageChangeListener
         *  OnPageChangeListener这个接口需要实现三个方法：onPageScrollStateChanged，onPageScrolled ，onPageSelected
         *      1、onPageScrollStateChanged(int arg0) 此方法是在状态改变的时候调用。
         *          其中arg0这个参数有三种状态（0，1，2）
         *              arg0 ==1的时表示正在滑动，arg0==2的时表示滑动完毕了，arg0==0的时表示什么都没做
         *              当页面开始滑动的时候，三种状态的变化顺序为1-->2-->0
         *      2、onPageScrolled(int arg0,float arg1,int arg2) 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直被调用。
         *          其中三个参数的含义分别为：
         *              arg0 :当前页面，及你点击滑动的页面
         *              arg1:当前页面偏移的百分比
         *              arg2:当前页面偏移的像素位置
         *      3、onPageSelected(int arg0) 此方法是页面跳转完后被调用，arg0是你当前选中的页面的Position（位置编号）
         */

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                setTitleColour(position, positionOffset);
            }

            @Override
            public void onPageSelected(int postion) {

                postion_state = postion;
                Log.e("yo", postion_state + "");
                setToolbarColour(postion);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    //viewPager滑动改变toolbar背景颜色
    private void setToolbarColour(int postion) {

        if (postion == 1) {
            toolbar.setBackgroundColor(Color.parseColor(Color2));
            linearLayout_title.setBackgroundColor(Color.parseColor(Color2));
            linearLayout_title.setAlpha(0.8f);
            linearLayout_set.setBackgroundColor(Color.parseColor(Color2));

            // mD_RecyclerView.setBackgroundResource(postion);
            if (lastpostion == 0) {

                if (isShow_floating_yo) {
                    rotate(floatingActionButton_message);
                    floatingActionButton_message.setVisibility(View.VISIBLE);
                    floatingActionButton_yo.setVisibility(View.INVISIBLE);
                    floatingActionButton_wall.setVisibility(View.INVISIBLE);
                } else {
                    floatingActionButton_message.setVisibility(View.VISIBLE);
                    show(floatingActionButton_message);
                }
            } else if (lastpostion == 2) {

                rotate(floatingActionButton_message);
                floatingActionButton_message.setVisibility(View.VISIBLE);
                floatingActionButton_yo.setVisibility(View.INVISIBLE);
                floatingActionButton_wall.setVisibility(View.INVISIBLE);
            }

            lastpostion = 1;
        } else if (postion == 0) {

            toolbar.setBackgroundColor(Color.parseColor(Color1));
            linearLayout_title.setBackgroundColor(Color.parseColor(Color1));
            linearLayout_title.setAlpha(0.8f);
            linearLayout_set.setBackgroundColor(Color.parseColor(Color1));
            //linearLayout_left.setBackgroundColor(Color.parseColor(Color1));

            floatingActionButton_wall.setVisibility(View.INVISIBLE);
            if (isShow_floating_yo) {
                rotate(floatingActionButton_yo);
                floatingActionButton_message.setVisibility(View.INVISIBLE);
                floatingActionButton_yo.setVisibility(View.VISIBLE);
            } else {
                hide(floatingActionButton_message);
                if (isFirst) {

                } else {
                    floatingActionButton_yo.setVisibility(View.VISIBLE);
                }


            }
            lastpostion = 0;
        } else {

            toolbar.setBackgroundColor(Color.parseColor(Color3));
            linearLayout_title.setBackgroundColor(Color.parseColor(Color3));
            linearLayout_title.setAlpha(0.8f);
            linearLayout_set.setBackgroundColor(Color.parseColor(Color3));
            rotate(floatingActionButton_wall);
            floatingActionButton_message.setVisibility(View.INVISIBLE);
            floatingActionButton_wall.setVisibility(View.VISIBLE);
            floatingActionButton_yo.setVisibility(View.INVISIBLE);

            lastpostion = 2;
        }


    }

    //viewPager滑动改变textView字体颜色
    private void setTitleColour(int position, float positionOffset) {
        int lastpostion;
        lastpostion = 0;
        if (position == 1) {
            if (lastpostion == 2) {
                textView_yo.setAlpha(positionOffset * 0.6f + 0.4f);
                textView_fri.setAlpha(1 - positionOffset * 0.6f);
                textView_yowall.setAlpha(0.4f);
            } else {
                textView_yo.setAlpha(0.4f);
                textView_fri.setAlpha(1 - positionOffset * 0.6f);
                textView_yowall.setAlpha(positionOffset * 0.6f + 0.4f);
            }

        } else if (position == 0) {
            textView_yo.setAlpha(1 - positionOffset * 0.6f);
            textView_fri.setAlpha(positionOffset * 0.6f + 0.4f);
            textView_yowall.setAlpha(0.4f);
            lastpostion = 0;
        } else {
            textView_yowall.setAlpha(1 - positionOffset * 0.6f);
            textView_fri.setAlpha(positionOffset * 0.6f + 0.4f);
            textView_yo.setAlpha(0.4f);
            lastpostion = 2;

        }

    }

    //将viewPager的状态传给DRcyclerview

    //设置recyclerView
    private void setmRecyclerView() {

        RecyclerView.LayoutManager mLayoutManager;
        //mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        //mRecyclerView.setOnTouchListener(this);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,        StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new RecyclerViewAdapter(this, datas);
        mRecyclerView.setAdapter(mAdapter);

        //隐藏刷新栏

        //mRecyclerView.scrollToPosition(1);
        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(1, 0);
        //mRecyclerView.scrollBy(0, -180);


        //滑动监测
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private static final int SCROLL_DISTANCE = 50;


            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //得到当前view的item总数
                //visibleItemCount = mRecyclerView.getChildCount();
                //Log.e("Yo", visibleItemCount+"个");

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalScrollDistance = totalScrollDistance + dy;

                // Log.e("yo",recycler_firstVisableItem+"");

                // -1 表示不能向上


                if (totalScrollDistance < 0 && totalScrollDistance > -170) {


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (isRefresing) {

                            } else {
                                mRecyclerView.scrollBy(0, -totalScrollDistance);
                                //隐藏刷新栏
                                mAdapter.setTextView_refresh();
                            }


                        }
                    }, 500);


                }


                int recycler_firstVisableItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (recycler_firstVisableItem == 0) {
                    //当第一个item存在界面上时就不触发隐藏、显示操作
                    if (isShow_floating_yo) {
                        isShow_floating_yo = false;
                        hide(floatingActionButton_yo);
                    }
                } else if (totalScrollDistance > SCROLL_DISTANCE) {

                    if (isFirst) {
                        floatingActionButton_yo.setVisibility(View.VISIBLE);
                        isFirst = false;
                    }
                    if (!isShow_floating_yo) {
                        isShow_floating_yo = true;
                        show(floatingActionButton_yo);
                    }

                }

                getSize();

                if (recycler_state == 0) {

                    view_refresh = (mRecyclerView.getLayoutManager()).getChildAt(0);
                    ViewGroup.LayoutParams params = view_refresh.getLayoutParams();
                    params.height = 1;
                    view_refresh.setLayoutParams(params);

                } else {
                    //((LinearLayoutManager)mRecyclerView.getLayoutManager()).getChildAt(0).setVisibility(View.VISIBLE);
                    //((LinearLayoutManager)mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(1,0);
                }
            }


        });

        mAdapter.setOnItemListener(new RecyclerViewAdapter.OnItemListener() {
            @Override
            public void onClick(View v, int position) {


                //点击响应
                //0位置是下拉刷新
                if (position == 0) {

                    //finish();
                } else if (position == 1) {

                    Date date = new Date(Time.timeNow());
                    String time = Time.dateNow(date);
                    //  String pass =Time.timeAgo(date);
                    //SendYo("13331578811");
                    Log.e("yo", "time=" + time + "pass=");
                    SendYo("13331570000");

                } else if (position == 2) {

                    sendNotification();
                    List_send list1 = new List_send(1, R.mipmap.tx_xiao2_150, "S.s", "3", 1, 2);
                    mRecyclerView.scrollToPosition(1);
                    datas.add(1, list1);
                    mAdapter.notifyItemInserted(1);
                    mAdapter.notifyItemRangeChanged(0, datas.size() - 0);
                    totalScrollDistance = 0;


                }
            }
        });


    }

    public void ToIsee(String UserId) {

        Intent intent = new Intent(MainActivity.this, MyActivity.class);
        intent.putExtra("UserId", UserId);
        startActivity(intent);
    }


    //下面三个方法都是用来recycler下拉刷新
    private void setRefresh() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                getSize();
                if (!isGetHeaderHeight) {

                    isGetHeaderHeight = true;
                    HeaderHeight = totalScrollDistance;

                }

                if (!isRefresing) {

                    isRefresing = true;
                    mAdapter.refresh(displayWidth);


                    if (recycler_state == 0) {
                        ViewGroup.LayoutParams params = view_refresh.getLayoutParams();
                        params.height = 180;
                        view_refresh.setLayoutParams(params);
                    }

                }
                swipeRefreshLayout.setRefreshing(false);


            }
        });
    }

    public void set0() {


        if (!isGetHeaderHeight) {

            mRecyclerView.scrollBy(0, 90);
            isRefresing = false;
            /*ViewGroup.LayoutParams params=view_refresh.getLayoutParams();
            params.height=1;
            view_refresh.setLayoutParams(params);
            isRefresing = false;*/
            if (recycler_state == 0) {
                ViewGroup.LayoutParams params = view_refresh.getLayoutParams();
                params.height = 1;
                view_refresh.setLayoutParams(params);
            }


        } else {
            mRecyclerView.scrollBy(0, 90);
            isRefresing = false;
            if (recycler_state == 0) {
                ViewGroup.LayoutParams params = view_refresh.getLayoutParams();
                params.height = 1;
                view_refresh.setLayoutParams(params);
            }
            /*ViewGroup.LayoutParams params=view_refresh.getLayoutParams();
            params.height=1;
            view_refresh.setLayoutParams(params);
            isRefresing = false;*/

        }


    }

    private void getSize() {

        int itemHeight, size;
        itemHeight = mRecyclerView.getLayoutManager().getChildAt(1).getHeight();
        size = ((displayHeight - linearLayout_title.getBottom()) - (displayHeight - linearLayout_title.getBottom()) % itemHeight) / itemHeight;
        if (datas.size() < size + 1) {
            recycler_state = 0;
        } else {
            recycler_state = 1;
        }

    }

    //设置recyclerView
    private void setmRecyclerView_friend() {


        //mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        //mRecyclerView.setOnTouchListener(this);
        mRecyclerView_friend.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView_friend.setHasFixedSize(true);
        mRecyclerView_friend.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,        StaggeredGridLayoutManager.VERTICAL));
        mAdapter_friend = new ToFriendActivity(this, friend_datas);
        mRecyclerView_friend.setAdapter(mAdapter_friend);


        //滑动监测
        mRecyclerView_friend.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private static final int SCROLL_DISTANCE = 50;


            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //得到当前view的item总数
                //visibleItemCount = mRecyclerView.getChildCount();
                //Log.e("Yo", visibleItemCount+"个");

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                friendtotalMove = friendtotalMove + dy;

                Log.e("yo", friendtotalMove + "");


                int firstVisableItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();


            }


        });

        mAdapter_friend.setOnItemListener(new ToFriendActivity.OnItemListener() {
            @Override
            public void onClick(View v, int position) {


                //点击响应
                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this, IseeActivity.class);
                    startActivity(intent);

                    //finish();
                } else if (position == 1) {


                    //SendYo("13331578811");
                } else {
                    //showPopupwindow(position);
                }
            }
        });


    }

    //设置recyclerView
    private void setmRecyclerView_yo() {

        getYowallDate(Num_send.getTel());
        //mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        //mRecyclerView.setOnTouchListener(this);
        mRecyclerView_yo.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView_yo.setHasFixedSize(true);
        mRecyclerView_yo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // mRecyclerView_yo.setLayoutManager(new StaggeredGridLayoutManager(2,        StaggeredGridLayoutManager.VERTICAL));
        mAdapter_yo = new YowallAdapter(this, yowall_datas);
        mRecyclerView_yo.setAdapter(mAdapter_yo);

        //滑动监测
        mRecyclerView_yo.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private static final int SCROLL_DISTANCE = 50;
            private int totalScrollDistance;

            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //得到当前view的item总数
                //visibleItemCount = mRecyclerView.getChildCount();
                //Log.e("Yo", visibleItemCount+"个");

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int firstVisableItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();


            }


        });

        mAdapter_yo.setOnItemListener(new YowallAdapter.OnItemListener() {
            @Override
            public void onClick(View v, int position) {


                //点击响应
                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this, MyActivity.class);
                    startActivity(intent);
                    //finish();
                } else if (position == 1) {
                }
            }
        });


    }


    //点击recyclerview发送YO
    public boolean onTouch(View v, MotionEvent event) {

        int ea = event.getAction();

        switch (ea) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
                lastY = (int) event.getRawY();
                lastTime = System.currentTimeMillis();


                break;
            case MotionEvent.ACTION_MOVE:
                // x轴方向的位移差
                int dx = (int) event.getRawX() - lastX;
                // y轴方向的位移差
                int dy = (int) event.getRawY() - lastY;

                long dtime = System.currentTimeMillis() - lastTime;
                Log.e("yo", "" + dtime);


                break;
            case MotionEvent.ACTION_UP:

                break;
            default:

                break;
        }


        return true;


    }


    //将数据传入recyclerview,初始化一些数据
    private void initDatas() {


        Intent intent = new Intent(this, PushService.class);
        startService(intent);

        floatingActionButton_message.setVisibility(View.INVISIBLE);
        floatingActionButton_wall.setVisibility(View.INVISIBLE);
        floatingActionButton_yo.setVisibility(View.INVISIBLE);
        isFirst = true;
        isShow_floating_yo = false;
        isShow_floating_message = false;


        Times = getSharedPreferences("times", MODE_PRIVATE);
        editor_times = Times.edit();
        times = Times.getInt("times", 0);
        editor_times.apply();

        Head = getSharedPreferences("head", MODE_PRIVATE);
        editor_head = Head.edit();
        head = Head.getString("head", "");
        editor_head.apply();

        Name = getSharedPreferences("name", MODE_PRIVATE);
        editor_name = Name.edit();
        name = Name.getString("name", "");
        editor_name.apply();

        Tel = getSharedPreferences("tel", MODE_PRIVATE);
        editor_tel = Tel.edit();
        tel = Tel.getString("tel", "");
        editor_tel.apply();


        application = (ECApplication) getApplication();
        datas = new ArrayList<>();
        final List_send list1 = new List_send(1, R.mipmap.tx_xiao2_150, "S.s", "3", 1, 2);
        List_send list2 = new List_send(2, R.mipmap.tx_thirty, application.Location_short, "55", 1, 4);
        datas.add(list1);
        datas.add(list2);
        datas.add(list1);

        for (int i = 0; i < 3; i++) {
            datas.add(list1);
        }


        conversationList.addAll(loadConversationWithRecentChat());

        List_send list_new;
        if (loadConversationWithRecentChat().size() > 0) {
            int k;
            for (k = 0; k < loadConversationWithRecentChat().size(); k++) {
                //显示最后一条来自别人的信息
                if (conversationList.get(k).getLatestMessageFromOthers() != null) {

                    list_new = new List_send(2, R.mipmap.dang, conversationList.get(k).getLatestMessageFromOthers().getFrom(), "9.15", 14.8, 1);
                    datas.add(list_new);
                } else {
                    //询问服务器他的信息
                    //list_new =new List_send(2,R.mipmap.dang,conversationList.get(k).getLatestMessageFromOthers().getFrom(),"9.15",14.8,1);
                }
                //datas.add(list_new);
            }
        } else {

        }


       /* if(datas.size()<10){
            recycler_state = 0;
        }*/

        friend_datas = new ArrayList<>();
        final List_friend list_friend1 = new List_friend(1, R.mipmap.tx_xiao2_150, "S.s", "3", 1, 2);
        List_friend list_friend2 = new List_friend(2, R.mipmap.tx_thirty, "thirty", "55", 1, 4);
        friend_datas.add(list_friend1);
        friend_datas.add(list_friend1);
        friend_datas.add(list_friend1);

        for (int i = 0; i < 20; i++) {
            friend_datas.add(list_friend2);
        }


        data = new ArrayList<>();
        final List_set listSet1 = new List_set(1, R.mipmap.my, "我的空间");
        final List_set listSet2 = new List_set(1, R.mipmap.set5, "设置");
        final List_set listSet3 = new List_set(1, R.mipmap.about, "关于Yo！");
        data.add(listSet1);
        data.add(listSet2);
        data.add(listSet3);


        yowall_datas = new ArrayList<>();

       /* final List_yowall yowall_listSet1 =new List_yowall(1,"no","123","lyh","9.15",2.9,"北京",false,false,false,12321,12312,12312);
        yowall_datas.add(yowall_listSet1);
        for(int i =0;i<20;i++){
            yowall_datas.add(yowall_listSet1);
        }*/


        isGetHeaderHeight = false;
        HeaderHeight = 0;
        isRefresing = false;


    }


    //从服务器得到yo墙数据
    private void getYowallDate(final String getTel) {


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.getYowallDate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray mJSONArray = jsonObject.getJSONArray("userList");
                            for (int i = 0; i < mJSONArray.length(); i++) {


                                JSONObject jsonItem = mJSONArray.getJSONObject(i);
                                int id = jsonItem.getInt("id");
                                String tel = jsonItem.getString("tel");
                                String photo_name = jsonItem.getString("photo_name");
                                String messages = jsonItem.getString("messages");
                                String time = jsonItem.getString("time");
                                String location = jsonItem.getString("location");
                                Boolean isYo = jsonItem.getBoolean("isYo");
                                Boolean isLove = jsonItem.getBoolean("isLove");
                                Boolean isWant = jsonItem.getBoolean("isWant");
                                int num_yo = jsonItem.getInt("num_yo");
                                int num_love = jsonItem.getInt("num_love");
                                int num_want = jsonItem.getInt("num_want");
                                //这才是对应关系int position, String photo,String message, String id,  String time,double distance, String location,boolean isYo,boolean isLove,boolean isHoping,int yo,int love,int hoping
                                List_yowall yowall_listSet1 = new List_yowall(id, photo_name, messages, tel, time, 2.9, location, isYo, isLove, isWant, num_yo, num_love, num_want);
                                // List_yowall yowall_listSet1 =new List_yowall(id,"no","123",tel,time,2.9,location,false,false,false,12,12,12);
                                addyowalldate(yowall_listSet1);
                                //yowall_datas.add(yowall_listSet1);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tel", getTel);
                return map;
            }
        };

        queue.add(stringRequest1);


    }


    //更新recycler数据，并置顶
    private void addyowalldate(List_yowall list_yowall) {


        mRecyclerView_yo.scrollToPosition(0);
        yowall_datas.add(0, list_yowall);
        mAdapter_yo.notifyItemInserted(0);
        mAdapter_yo.notifyItemRangeChanged(0, yowall_datas.size() - 0);

    }


    private void getYowallDate() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Host.getYowallDate, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.e("yo", 123123 + "");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });


        queue.add(jsonArrayRequest);

    }

    //设置floatingbutton
    private void setFloatingButton() {

        floatingActionButton_yo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRecyclerView.scrollBy(0, -totalScrollDistance);
                if (isShow_floating_yo) {
                    isShow_floating_yo = false;
                    hide(floatingActionButton_yo);
                }

            }
        });
        floatingActionButton_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, AddFriendActivity.class);
                startActivity(i);

            }
        });
        floatingActionButton_wall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, YowallActivity.class);
                startActivity(i);

            }
        });


    }
    // ---------------------------------  -------------------------------------
    //  动画


    //floatingbutton的动画
    private void show(final View view) {

        //得到控件与屏幕底部的距离
        int Height;
        Height = displayWidth - view.getTop();

        ValueAnimator mAnimator = ValueAnimator.ofInt(-Height, 0);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                int animatorValue = (int) animation.getAnimatedValue();
                view.setTranslationY(animatorValue);

            }
        });
        mAnimator.setDuration(300);
        mAnimator.setTarget(view);
        mAnimator.start();
    }

    private void hide(final View view) {

        //得到控件与屏幕底部的距离
        int Height;
        Height = displayWidth - view.getTop();

        ValueAnimator mAnimator = ValueAnimator.ofInt(0, Height);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                int animatorValue = (int) animation.getAnimatedValue();
                view.setTranslationY(-animatorValue);

            }
        });
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new AnticipateInterpolator());
        mAnimator.setTarget(view);
        mAnimator.start();

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (view == floatingActionButton_message) {
                    if (!isShow_floating_yo) {
                        floatingActionButton_message.setVisibility(View.INVISIBLE);
                        show(floatingActionButton_message);
                    }
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void rotate(final View view) {
        float k;
        if (view == floatingActionButton_message) {
            if (isShow_floating_yo) {
                k = 90f;
            } else {
                k = -90f;
            }
        } else if (view == floatingActionButton_wall) {
            k = 90f;
        } else {
            k = -90f;
        }
        ValueAnimator mAnimator = ValueAnimator.ofFloat(k, 0);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                float animatorValue = (float) animation.getAnimatedValue();
                view.setRotation(-animatorValue);

            }
        });
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new DecelerateInterpolator());
        mAnimator.setTarget(view);
        mAnimator.start();

    }
    //Yo出现动画


    //设置toolbar的控件
    //导航栏，搜索键
    private void setToolbar() {

        imageView_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getusername(tel);
                editor_name.putString("name", name);
                editor_name.commit();
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });


        setDrawLayoutRecyclerView();
    }

    //设置DrawLayout
    private void setDrawLayout() {

        //得到数据
        Intent intent1;
        intent1 = getIntent();



        if (times == 0) {

            if ((intent1.getStringExtra("headimage_uri")).equals("")) {

                gethead(imageView_DrawLayouthead, intent1.getStringExtra("tel"));


            } else {
                Uri imageUri = Uri.parse(intent1.getStringExtra("headimage_uri"));
                imageView_DrawLayouthead.setImageBitmap(getBitmapFromUri(imageUri));

            }

            editor_times.putInt("times", ++times);
            editor_times.commit();

            getusername(intent1.getStringExtra("tel"));
            editor_name.putString("name", intent1.getStringExtra("userName"));
            editor_name.commit();

            editor_head.putString("head", intent1.getStringExtra("headimage_uri"));
            editor_head.commit();

            editor_tel.putString("tel", intent1.getStringExtra("tel"));
            editor_tel.commit();

            setSharedPreferences(intent1.getStringExtra("headimage_uri"));


        } else {
            //不是第一次访问先读取本地数据，如果没有再去获取网络数据，减少主线程负担

            if (head.equals("")) {

                gethead(imageView_DrawLayouthead, tel);

            } else {

                Uri imageUri = Uri.parse(head);
                imageView_DrawLayouthead.setImageBitmap(getBitmapFromUri(imageUri));

            }


            if (name.equals("")) {
                getusername(tel);
                editor_name.putString("name", intent1.getStringExtra("userName"));
                editor_name.commit();
            } else {
                textView_DrawLayoutname.setText(name);
            }


        }


        imageView_DrawLayouthead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
            }
        });
        textView_DrawLayoutsetname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
            }
        });
    }

    //DrawLayout内的recyclerview
    private void setDrawLayoutRecyclerView() {

        linearLayout_left.setBackgroundColor(Color.parseColor(Color2));
        mD_RecyclerView = (RecyclerView) findViewById(R.id.recyclerView_set);
        mD_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        mD_RecyclerView.setHasFixedSize(true);


        mD_RecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mDAdapter = new DRecyclerViewAdapter(this, data);
        mD_RecyclerView.setAdapter(mDAdapter);
        mDAdapter.setOnItemListener(new DRecyclerViewAdapter.OnItemListener() {
            @Override
            public void onClick(View v, int position) {
                //点击响应
                //我的空间
                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this, MyActivity.class);
                    // intent.putExtra("headimage_uri", "");
                    intent.putExtra("userName", name);
                    //intent.putExtra("userSex", "");
                    intent.putExtra("tel", tel);
                    startActivity(intent);
                    //finish();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDrawerLayout.closeDrawer(Gravity.LEFT);
                        }
                    }, 1000);
                } else if (position == 1) {
                    Intent intent = new Intent(MainActivity.this, SetActivity.class);
                    startActivity(intent);

                } else if (position == 2) {
                    Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    //点击弹框
    private void setPopupwindow() {

        friendwindow = this.getLayoutInflater().inflate(R.layout.window_friend, null);
        friend_window = new PopupWindow(friendwindow, displayWidth, 350);
        //获得焦点
        friend_window.setFocusable(true);
        friend_window.setTouchable(true);
        friend_window.setOutsideTouchable(true);

        friend_window.setAnimationStyle(R.style.popwindow_anim_style);


    }

    private void showPopupwindow(int postion) {

        int item_height = mRecyclerView_friend.getLayoutManager().getChildAt(0).getHeight();
        int dy = friendtotalMove % item_height;
        int Toppostion = (friendtotalMove - dy) / item_height;
        int y = linearLayout_title.getBottom() + item_height - dy + (postion - Toppostion) * item_height;


        Log.e("yo", "item_height=" + item_height + "dy=" + dy + "Toppostion=" + Toppostion + "linearLayout_title.getBottom()=" + linearLayout_title.getBottom());
        mRecyclerView_friend.getTop();
        friend_window.showAtLocation(friendwindow, Gravity.TOP | Gravity.START, 0, y);

    }


    // --------------------------------- setPhoto -------------------------------------
    //  头像处理方法

    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");//选择图片
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        //如果你想在Activity中得到新打开Activity关闭后返回的数据，
        //你需要使用系统提供的startActivityForResult(Intent intent,int requestCode)方法打开新的Activity
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent intent) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == MainActivity.RESULT_CANCELED) {//取消

            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST://如果是来自本地的
                cropRawPhoto(intent.getData());//直接裁剪图片
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);//设置图片框
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }


    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        //把裁剪的数据填入里面

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Bitmap photo_circle = CircleImageView.getCroppedBitmap(photo, 150);

            //imageView.setImageBitmap(photo);
            imageView_DrawLayouthead.setImageBitmap(photo_circle);


            //新建文件夹 先选好路径 再调用mkdir函数 现在是根目录下面的Ask文件夹
            File nf = new File(Environment.getExternalStorageDirectory() + "/Ask");
            nf.mkdir();

            //在根目录下面的ASk文件夹下 创建okkk.jpg文件
            File f = new File(Environment.getExternalStorageDirectory() + "/Ask", "ok.png");
            Uri uri = Uri.fromFile(f);
            //上传给服务器
            uploadhead(photo_circle);


            FileOutputStream out = null;
            try {

                //打开输出流 将图片数据填入文件中
                out = new FileOutputStream(f);
                photo_circle.compress(Bitmap.CompressFormat.PNG, 90, out);

                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    public Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------- setSharedPreferences -------------------------------------
    //  头像信息的传递

    public void setSharedPreferences(String head) {
        //一、根据Context获取SharedPreferences对象
        /**
         * context.getSharedPreferences("bill",MODE);
         * MODE模式支持以下几种:
         *1、 检查文件是否存在，存在就往文件追加内容，否则就创建新文件。
         *SharedPreferences sharedPreferences=context.getSharedPreferences("bill", MODE_APPEND);
         *2、表示当前文件可以被其他应用读取
         *SharedPreferences sharedPreferences=context.getSharedPreferences("bill", MODE_WORLD_READABLE);
         *3、表示当前文件可以被其他应用写入
         *SharedPreferences sharedPreferences=context.getSharedPreferences("bill", MODE_WORLD_WRITEABLE);
         */
        //方法2： 添加数据 利用 SharedPreferences 在不同的Activity传递数据
        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();  //获取Editor对象。
        Set<String> values = new HashSet<String>(); //通过Editor对象存储key-value键值对数据。
        editor.putString("string", head);//存储string类型
        editor.putStringSet("set", values);//存储set多维数组
        editor.commit();    // 提交数据。
    }

    //从服务器得到图片
    public void gethead(final ImageView imageView, String tel) {

        ImageRequest imageRequest = new ImageRequest(
                Host.ip + "YoServer/head" + tel + ".jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                imageView.setImageResource(R.mipmap.yo_head_100);
            }
        });
        queue.add(imageRequest);
    }

    //从服务器得到图片
    public void getyowallphoto(final ImageView imageView, String name) {

        ImageRequest imageRequest = new ImageRequest(
                Host.ip + "YoServer/" + "name" + name + ".jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //失败时候加载额图片
                //imageView.setImageResource(R.mipmap.yo_head_100);
            }
        });
        queue.add(imageRequest);
    }

    //上传图片到服务器
    public void uploadhead(Bitmap bitmap) {


        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        final String string = Base64.encodeToString(bytes, Base64.DEFAULT);
        Log.e("yo", string);


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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tel", tel);
                map.put("head", string);
                return map;
            }
        };

        queue.add(stringRequest1);

    }

    public void getusername(final String tel) {


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.getusername,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        name = response;
                        textView_DrawLayoutname.setText(name);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tel", tel);

                return map;
            }
        };

        queue.add(stringRequest1);

    }

    //点赞及记数功能
    public void changeyowall(final String change, final String yowallid) {

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.hopingchange,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("tel", tel);
                map.put("change", change);
                map.put("yowallid", yowallid);

                return map;
            }
        };

        queue.add(stringRequest1);
    }

    //点赞数增加减少
    public void changeyowallnum(final String id, final String num_yo, final String num_love, final String num_want, final String change_num) {


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.updatehoping,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
               /* if(Integer.valueOf(change_num)==1){
                    map.put("num_yo",(Integer.valueOf(num_yo)+1)+"");
                    map.put("num",num_love);
                    map.put("num",num_want);
                }
                else if(Integer.valueOf(change_num)==2){
                    map.put("num_yo",num_yo);
                    map.put("num",(Integer.valueOf(num_love)+1)+"");
                    map.put("num",num_want);
                }
                else if(Integer.valueOf(change_num)==3){
                    map.put("num_yo",num_yo);
                    map.put("num",num_love);
                    map.put("num",(Integer.valueOf(num_want)+1)+"");
                }
                else if(Integer.valueOf(change_num)==4){
                    map.put("num_yo",(Integer.valueOf(num_yo)-1)+"");
                    map.put("num",num_love);
                    map.put("num",num_want);
                }
                else if(Integer.valueOf(change_num)==5){
                    map.put("num_yo",num_yo);
                    map.put("num",(Integer.valueOf(num_love)-1)+"");
                    map.put("num",num_want);
                }
                else if(Integer.valueOf(change_num)==6){
                    map.put("num_yo",num_yo);
                    map.put("num",num_love);
                    map.put("num",(Integer.valueOf(num_want)-1)+"");
                }*/
                map.put("num_yo", num_yo);
                map.put("num_love", num_love);
                map.put("num_want", num_want);
                map.put("id", id);
                map.put("change_num", change_num);
                Log.e("yo", id + "" + num_love + num_want + num_yo + change_num);
                return map;
            }
        };

        queue.add(stringRequest1);

    }


    // --------------------------------- setHX -------------------------------------
    //  环信发送yo
    private void SendYo(String username) {


        addFriend(username);
        EMMessage message = EMMessage.createTxtSendMessage("yo", username);
        message.setAttribute("location", application.Location_short);
        message.setAttribute("Latitude", application.Location_Latitude + "");
        message.setAttribute("Longitude", application.Location_Longitude + "");
        EMClient.getInstance().chatManager().sendMessage(message);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //
                    }
                });
                // 消息发送成功，打印下日志，正常操作应该去刷新ui
                Log.i("Yo", "send message on success");
            }

            @Override
            public void onError(int i, String s) {
                // 消息发送失败，打印下失败的信息，正常操作应该去刷新ui
                Log.i("Yo", "send message on error " + i + " - " + s);
            }

            @Override
            public void onProgress(int i, String s) {
                // 消息发送进度，一般只有在发送图片和文件等消息才会有回调，txt不回调
            }
        });


    }

    private void addFriend(String username) {
        try {

            //参数为要添加的好友的username和添加理由
            EMClient.getInstance().contactManager().addContact(username, "");
            Log.e("yo", "添加好友" + username + "成功");
        } catch (final HyphenateException e) {
            e.printStackTrace();
            Log.e("yo", "添加好友失败");
        }
    }

    //  环信消息监听主要方法

    @Override
    public void onMessageReceived(List<EMMessage> list) {

        for (EMMessage message : list) {
            // 因为消息监听回调这里是非ui线程，所以要用handler去更新ui
            Message msg = mHandler.obtainMessage();
            msg.what = 0;
            msg.obj = message;
            mHandler.sendMessage(msg);

        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    //增加item方法，如果会话记录里没有则新建，有了则刷新 初期应该把所有的信息都显示出来，后期再考虑这种处理
    public void addtoSendrecycler(List_send list_send) {

        Log.e("yo", list_send.getid());

        conversationList.addAll(loadConversationWithRecentChat());
        if (loadConversationWithRecentChat().size() > 0) {
            int k;
            for (k = 0; k < loadConversationWithRecentChat().size(); k++) {

                if (conversationList.get(k).getLatestMessageFromOthers() != null) {


                    if (list_send.getid().equals(conversationList.get(k).getLatestMessageFromOthers().getFrom())) {

                        mRecyclerView.scrollToPosition(1);
                        datas.add(1, list_send);
                        mAdapter.notifyItemInserted(1);
                        mAdapter.notifyItemRangeChanged(0, datas.size() - 0);
                        totalScrollDistance = 0;

                        //因为先增加，再删除的时候就要+！
                        //delfromSendrecycler(k+1);

                    }
                } else if (list_send.getid().equals(conversationList.get(k).conversationId())) {


                    //此处应该先将getid的前状态删除掉再进行增加操作通过判断idtel等信息判断
                    mRecyclerView.scrollToPosition(1);
                    datas.add(1, list_send);
                    mAdapter.notifyItemInserted(1);
                    mAdapter.notifyItemRangeChanged(0, datas.size() - 0);
                    totalScrollDistance = 0;

                    //delfromSendrecycler(k+1);

                } else {

                    mRecyclerView.scrollToPosition(1);
                    datas.add(1, list_send);
                    mAdapter.notifyItemInserted(1);
                    mAdapter.notifyItemRangeChanged(0, datas.size() - 0);
                    totalScrollDistance = 0;

                }

            }


        } else {

            mRecyclerView.scrollToPosition(1);
            datas.add(1, list_send);
            mAdapter.notifyItemInserted(1);
            mAdapter.notifyItemRangeChanged(0, datas.size() - 0);
            totalScrollDistance = 0;

        }

    }

    public void addtoSendrecycler_new(List_send list_send) {

        Log.e("yo", list_send.getid());
        mRecyclerView.scrollToPosition(1);
        datas.add(1, list_send);
        mAdapter.notifyItemInserted(1);
        mAdapter.notifyItemRangeChanged(0, datas.size() - 0);


    }

    public void delfromSendrecycler() {
        int k = Send_postion;
        if (k > 0) {
            datas.remove(k);
            mAdapter.notifyItemRemoved(k);
            mAdapter.notifyItemRangeChanged(k, datas.size() - k);
            //canScrollVertically(1)的值表示是否能向下滚动， false表示已经滚动到底部
            if (mRecyclerView.canScrollVertically(1)) {

            } else {
                if (mRecyclerView.canScrollVertically(-1)) {
                    if (totalScrollDistance > mRecyclerView.getLayoutManager().getChildAt(0).getHeight())
                        totalScrollDistance = totalScrollDistance - mRecyclerView.getLayoutManager().getChildAt(0).getHeight();
                    else {
                        totalScrollDistance = 0;
                    }
                }

            }
        } else {
            datas.remove(0);
            mAdapter.notifyItemRemoved(0);
            mAdapter.notifyItemRangeChanged(0, datas.size());
        }

    }

    private void delfromSendrecycler(int del_postion) {


        //也要处理环信对话，要删除以前的对话
        int k = del_postion;
        if (k > 0) {
            datas.remove(k);
            mAdapter.notifyItemRemoved(k);
            mAdapter.notifyItemRangeChanged(k, datas.size() - k);
            //canScrollVertically(1)的值表示是否能向下滚动， false表示已经滚动到底部
            if (mRecyclerView.canScrollVertically(1)) {

            } else {
                if (mRecyclerView.canScrollVertically(-1)) {
                    if (totalScrollDistance > mRecyclerView.getLayoutManager().getChildAt(0).getHeight())
                        totalScrollDistance = totalScrollDistance - mRecyclerView.getLayoutManager().getChildAt(0).getHeight();
                    else {
                        totalScrollDistance = 0;
                    }
                }

            }
        } else if (k == 0) {
            datas.remove(0);
            mAdapter.notifyItemRemoved(0);
            mAdapter.notifyItemRangeChanged(0, datas.size());
        }

    }

    private Collection<? extends EMConversation> loadConversationWithRecentChat() {

        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();

        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>
                                    (conversation.getLastMessage().getMsgTime(), conversation)
                    );
                }
            }
        }

        try {
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }

        return list;
    }

    /**
     * 根据最后一条消息的时间排序
     *
     * @param sortList
     */
    private void sortConversationByLastChatTime(
            List<Pair<Long, EMConversation>> sortList) {
        Collections.sort(sortList, new Comparator<Pair<Long, EMConversation>>() {

            @Override
            public int compare(Pair<Long, EMConversation> con1,
                               Pair<Long, EMConversation> con2) {
                if (con1.first == con2.first) {
                    return 0;
                } else if (con2.first > con1.first) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }


    //发送通知栏
    private void sendNotification() {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = builder
                .setContentTitle("hgthirty")
                .setContentText("yoooooooo!")
                        //.setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.yo_head)
                .setContentIntent(mainPendingIntent)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.yo_head)).build();

        manager.notify(1, notification);
    }


    //动态权限
    public void getPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            //do something we want
        }
    }


    //动态权限申请
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //
            } else if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // Permission Denied
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    //借口回调，退出时回调，将初始值置零

}


