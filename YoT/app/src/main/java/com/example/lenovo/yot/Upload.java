package com.example.lenovo.yot;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/9/26.
 */
public class Upload extends BaseActivity {

    private String username;

    private RequestQueue queue = ((ECApplication)getApplication()).getRequestQueue();
    //更改昵称
    public static StringRequest upload_nameChange(String tel,String name){

        final String getName;
        final String getTel;
        getTel = tel;
        getName = name;


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.uploadname,
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
                map.put("name",getName);
                return map;
            }
        };

        return  stringRequest1;

    }




    public static StringRequest upload_yowall(final String tel,final String time,final String location,final  String message ,final String photo_name){



        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.uploadyowall,
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
                map.put("tel",tel);
                map.put("time",time);
                map.put("location",location);
                map.put("messages",message);
                map.put("photo_name",photo_name);




                return map;
            }
        };

        return  stringRequest1;

    }


    //上传图片到服务器
    public static StringRequest uploadyowallphoto(Bitmap bitmap ,final String tel,final String name ){



        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bStream);
        byte[] bytes=bStream.toByteArray();
        final String string = Base64.encodeToString(bytes, Base64.DEFAULT);
        Log.e("yo", string);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.uploadyowallphoto,
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
                map.put("tel",tel);
                map.put("head",string);
                map.put("photo",name);
                return map;
            }
        };

        return stringRequest1;

    }

    //上传到yoyowall表
    public static StringRequest upload_yoyowall(String tel,String name){

        final String getName;
        final String getTel;
        getTel = tel;
        getName = name;


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Host.uploadname,
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
                map.put("name",getName);
                return map;
            }
        };

        return  stringRequest1;

    }

}
