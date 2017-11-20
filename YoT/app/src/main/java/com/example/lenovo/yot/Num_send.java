package com.example.lenovo.yot;

/**
 * Created by lenovo on 2017/5/21.
 */
public class Num_send {



    private static int a=0;//0为第一次，1为第二次进入登录界面

    private static String  tel="";//0为第一次，1为第二次进入登录界面


    public static int getNum_send() {
        return a;
    }

    public static void setNum_send(int a) {
        Num_send.a = a;
    }

    public static String getTel() {
        return tel;
    }

    public static void setTel(String tel) {
        Num_send.tel = tel;
    }

}
