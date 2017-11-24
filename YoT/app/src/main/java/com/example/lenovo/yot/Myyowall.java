package com.example.lenovo.yot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/10/11.
 */
public class Myyowall extends BaseActivity {



    private List<List_yowall> yowall_datas;
    private RecyclerView mRecyclerView_yo;
    private MyyowallAdapter mAdapter_yo;

    FloatingActionButton floatingActionButton;
    ImageView imageView_back;
    //获取请求队列示例
    private RequestQueue queue = ((ECApplication)getApplication()).getRequestQueue();

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                //将侧边栏顶部延伸至status bar

            }
        }

        setContentView(R.layout.myyowall);
        //得到屏幕大小
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);



        floatingActionButton =(FloatingActionButton)findViewById(R.id.floatingActionButton_sendyowall);
        imageView_back = (ImageView)findViewById(R.id.back);


        setmRecyclerView_yo();



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Myyowall.this, YowallActivity.class);
                startActivity(intent);
                Myyowall.this.finish();
            }
        });

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Myyowall.this.finish();
            }
        });

    }


    //设置recyclerView
    private void setmRecyclerView_yo(){


        mRecyclerView_yo = (RecyclerView) findViewById(R.id.recyclerView_myyowall);
        yowall_datas = new ArrayList<>();
        //mRecyclerView.setOnTouchListener(this);
        mRecyclerView_yo.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView_yo.setHasFixedSize(true);
        mRecyclerView_yo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // mRecyclerView_yo.setLayoutManager(new StaggeredGridLayoutManager(2,        StaggeredGridLayoutManager.VERTICAL));
        getMyYowallDate(Num_send.getTel());
        mAdapter_yo =  new MyyowallAdapter(this,yowall_datas);
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

        mAdapter_yo.setOnItemListener(new MyyowallAdapter.OnItemListener() {
            @Override
            public void onClick(View v, int position) {


                //点击响应
                if (position == 0) {
                    //finish();
                } else if (position == 1) {
                }
            }
        });


    }



    //从服务器的得到墙列表
    private  void getYowallDate(){

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Host.getYowallDate, null,
                new Response.Listener<JSONObject>() {
                    //在这个方法里，成功获取到了数据
                    @Override
                    public void onResponse(JSONObject response) {


                        try{
                            JSONArray mJSONArray= response.getJSONArray("userList");
                            for(int i =  0 ; i < mJSONArray.length(); i++)
                            {


                                JSONObject jsonItem = mJSONArray.getJSONObject(i);
                                int id = jsonItem.getInt("id");
                                String tel = jsonItem.getString("tel");
                                String photo_name = jsonItem.getString("photo_name");
                                String messages = jsonItem.getString("messages");
                                String time =jsonItem.getString("time");
                                String location = jsonItem.getString("location");


                                List_yowall yowall_listSet1 =new List_yowall(id,photo_name,messages,tel,time,2.9,location,false,false,false,12,12,12);
                                // List_yowall yowall_listSet1 =new List_yowall(id,"no","123",tel,time,2.9,location,false,false,false,12,12,12);
                                addyowalldate(yowall_listSet1);
                                //yowall_datas.add(yowall_listSet1);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            //在这个方法里，打印错误信息
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        queue.add(jsonObjReq);

    }


    private  void getMyYowallDate(final String getTel){


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.getMyYowallDate,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                JSONArray mJSONArray=jsonObject.getJSONArray("userList");
                                for(int i =  0 ; i < mJSONArray.length(); i++)
                                {


                                    JSONObject jsonItem = mJSONArray.getJSONObject(i);
                                    int id = jsonItem.getInt("id");
                                    String tel = jsonItem.getString("tel");
                                    String photo_name = jsonItem.getString("photo_name");
                                    String messages = jsonItem.getString("messages");
                                    String time =jsonItem.getString("time");
                                    String location = jsonItem.getString("location");


                                    List_yowall yowall_listSet1 =new List_yowall(id,photo_name,messages,tel,time,2.9,location,false,false,false,12,12,12);
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

    private void addyowalldate(List_yowall list_yowall){


        mRecyclerView_yo.scrollToPosition(0);
        yowall_datas.add(0,list_yowall);
        mAdapter_yo.notifyItemInserted(0);
        mAdapter_yo.notifyItemRangeChanged(0, yowall_datas.size() - 0);

    }

    public void getyowallphoto(final ImageView imageView,String name){

        ImageRequest imageRequest = new ImageRequest(
                Host.ip+"YoServer/"+"name"+name+".jpg",
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

}
