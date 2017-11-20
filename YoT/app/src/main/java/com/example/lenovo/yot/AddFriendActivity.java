package com.example.lenovo.yot;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/11/6.
 */
public class AddFriendActivity extends BaseActivity {


    ImageView back;
    EditText user_tel;
    ImageView button_search;
    ImageView search_tx;
    TextView search_id;
    TextView search_time;
    TextView search_distance;

    private List<List_send> datas;
    private RecyclerView mRecyclerView;
    private AddFriendAdapter mAdapter;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                //将侧边栏顶部延伸至status bar

            }
        }

        setContentView(R.layout.addfriend);
        //得到屏幕大小



        datas = new ArrayList<>();
        final List_send list1 = new List_send(1,R.mipmap.tx_xiao2_150,"S.s","3",1,2);
        List_send list2 = new List_send(2,R.mipmap.tx_thirty,"thirty","55",1,4);
        datas.add(list1);
        datas.add(list2);

        setmRecyclerView();

        user_tel = (EditText)findViewById(R.id.user_tel);
        button_search = (ImageView)findViewById(R.id.search_gray);
        search_tx = (ImageView)findViewById(R.id.imageView_search_tx);
        search_id = (TextView)findViewById(R.id.textView_search_id);
        search_time = (TextView)findViewById(R.id.textView_search_time);
        search_distance = (TextView)findViewById(R.id.textView_search_distance);



        search_tx.setVisibility(View.INVISIBLE);
        search_id.setVisibility(View.INVISIBLE);
        search_time.setVisibility(View.INVISIBLE);
        search_distance.setVisibility(View.INVISIBLE);


        back = (ImageView)findViewById(R.id.back);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);






        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendActivity.this.finish();
            }
        });
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ShowAnswer();
            }
        });

    }

    private  void  ShowAnswer(){




        if(user_tel.getText().toString().trim().equals("")){


        }
        else if((user_tel.getText().toString().length())!=11){


        }
        else {

            //上传给服务器得到资料 此处测试
            search_tx.setVisibility(View.VISIBLE);
            search_id.setVisibility(View.VISIBLE);
            search_time.setVisibility(View.VISIBLE);
            search_distance.setVisibility(View.VISIBLE);

        }

    }


    private void setmRecyclerView(){

        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_addFriend);
        //mRecyclerView.setOnTouchListener(this);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,        StaggeredGridLayoutManager.VERTICAL));
        mAdapter =  new AddFriendAdapter(this,datas);
        mRecyclerView.setAdapter(mAdapter);

        //隐藏刷新栏

        //mRecyclerView.scrollToPosition(1);
        ((LinearLayoutManager)mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(1,0);
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



                // Log.e("yo",recycler_firstVisableItem+"");

                // -1 表示不能向上



                int recycler_firstVisableItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();





            }


        });

        mAdapter.setOnItemListener(new AddFriendAdapter.OnItemListener() {
            @Override
            public void onClick(View v, int position) {


                //点击响应
                //0位置是下拉刷新
                if (position == 0) {

                    //finish();
                } else if (position == 1) {


                } else if (position == 2) {


                }
            }
        });



    }

}
