package com.example.lenovo.yot;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/10/27.
 */
public class PushService extends Service implements EMMessageListener {


    String messageFrom;
    List<String> from=new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("yo", "PushService is running");
        EMClient.getInstance().chatManager().addMessageListener(this);

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    EMMessage message = (EMMessage) msg.obj;
                    //获得头像数据
                    //数据处理
                    messageFrom = message.getFrom().toString().trim();

                    //List_send list_new = new  List_send(0,R.mipmap.tx_xiao2_150,message.getFrom().toString().trim(),"","1",1,10,1);
                    //textView_id.setText("\n" + message.getFrom().toString().trim()+message.getMsgTime());
                    //
                    //sendNotification();

                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(PushService.this);
                    Intent mainIntent = new Intent(PushService.this, MainActivity.class);
                    PendingIntent mainPendingIntent = PendingIntent.getActivity(PushService.this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification notification = builder
                            .setContentTitle(messageFrom)
                            .setContentText("yoooooooo!")
                                    //.setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.yo_head)
                            .setContentIntent(mainPendingIntent)
                            .setAutoCancel(true)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.yo_head)).build();

                    //加入信息来的人的判断，如果列表包含，则刷新状态，不包含则加入列表发送通知
                    if(from.contains(messageFrom)){

                        manager.notify(from.indexOf(messageFrom), notification);

                    }else{

                        //先发送然后再加入list
                        manager.notify(from.size(), notification);
                        from.add(messageFrom);

                    }

                    break;
            }
        }
    };

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
}


