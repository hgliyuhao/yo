package com.example.lenovo.yot;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/10/1.
 */
public class ParseJson {

    public static String id ;
    public static String tel ;
    public static String time ;
    public static String location;
    public static String messages ;
    public static String photo_name;
    public static String num;


    //方法一：使用JSONObject
    public static void parseJSONWithJSONObject(String jsonData) {
            try
            {
                    JSONArray jsonArray = new JSONArray(jsonData);
                     for (int i=0; i < jsonArray.length(); i++)    {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                         num  = jsonObject.getString("num");
                         id  = jsonObject.getString("id");
                         tel = jsonObject.getString("tel");
                         time  = jsonObject.getString("time");
                         location  = jsonObject.getString("location");
                         messages  = jsonObject.getString("messages");
                         photo_name  = jsonObject.getString("photo_name");

                         Log.e("yo",num+id+tel+time);

                     }
                 }
             catch (Exception e)
             {
                     e.printStackTrace();
                 }
         }

}
