package com.example.lenovo.yot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lenovo on 2017/9/14.
 */
public class IseeActivity extends BaseActivity{
    private List<List_others> datas;
    private RecyclerView mRecyclerView;
    private ORecyclerViewAdapter mAdapter;
    private ImageView imageView;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
    private static final int CODE_CAMERA_REQUEST = 0xa1;//拍照
    private static final int CODE_RESULT_REQUEST = 0xa2;//最终裁剪后的结果
    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 300;
    private static int output_Y = 300;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                //将侧边栏顶部延伸至status bar

            }
        }

        setContentView(R.layout.isee);
        initDatas();
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_content);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        setmRecyclerView();
        imageView = (ImageView)findViewById(R.id.imageView_1);
        //imageView.setBackgroundResource(R.mipmap.my_bg_2);
        i =ran();
        if(i==0){
            imageView.setImageResource(R.mipmap.my_bg_1);
        }
        else if(i==1){
            imageView.setImageResource(R.mipmap.my_bg_2);
        }
        else if(i==2){
            imageView.setImageResource(R.mipmap.my_bg_3);
        }
        else if(i==3){
            imageView.setImageResource(R.mipmap.my_bg_4);
        }
        else if (i==4){
            imageView.setImageResource(R.mipmap.my_bg_5);
        }




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
            }
        });
        //得到名称
        collapsingToolbarLayout.setTitle("付珊珊");
    }


    private void setmRecyclerView(){


        //mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter =  new ORecyclerViewAdapter(this,datas);
        mRecyclerView.setAdapter(mAdapter);
        //滑动监测
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

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

                totalScrollDistance = totalScrollDistance + dy;

                int firstVisableItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

            }

        });


    }
    private void initDatas() {

        //从服务器得到图片


        datas = new ArrayList<>();
        final List_others list1 = new List_others(1,R.mipmap.wall,"Yo墙","分享心愿");
        List_others list2 = new List_others(2,R.mipmap.ic_compare_arrows_48px,"足迹","了解更多");
        //List_others list3 = new List_others(3,R.mipmap.tel,"联系","最近的距离");
        List_others list4 = new List_others(4,R.mipmap.share,"分享","介绍给其他朋友");

        //第一个是点赞状态栏，插入什么都无所谓
        datas.add(list2);


        datas.add(list1);
        datas.add(list2);
        datas.add(list4);

    }


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
            Bitmap photo_circle =CircleImageView.getCroppedBitmap(photo, 150);
            imageView.setImageBitmap(photo);


            //新建文件夹 先选好路径 再调用mkdir函数 现在是根目录下面的Ask文件夹
            File nf = new File(Environment.getExternalStorageDirectory()+"/Ask");
            nf.mkdir();

            //在根目录下面的ASk文件夹下 创建okkk.jpg文件
            File f = new File(Environment.getExternalStorageDirectory()+"/Ask", "ok.png");
            Uri uri = Uri.fromFile(f);
            //上传给服务器



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


    //随机产生0到5的整数
    private int ran(){
        int c;
        Random random=new Random();
        c= random.nextInt(6);
        return c;
    }


}

