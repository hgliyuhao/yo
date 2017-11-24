package com.example.lenovo.yot;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/9/18.
 */
public class YowallActivity extends BaseActivity {

    //得到屏幕大小
    int displayWidth;
    int displayHeight;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private List<List_others> datas;
    EditText editText;
    TextView textView;
    ImageView imageView;
    ImageView imageView_back;

    String tel;
    String time;
    String location;
    String message;
    String photo_name;
    Bitmap bitmap_yowall;


    Boolean isSetPhoto;
    Boolean isText;

    FloatingActionButton floatingActionButton;
    private RequestQueue queue = ((ECApplication)getApplication()).getRequestQueue();


    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
    private static final int CODE_CAMERA_REQUEST = 0xa1;//拍照
    private static final int CODE_RESULT_REQUEST = 0xa2;//最终裁剪后的结果
    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 300;
    private static int output_Y = 300;
    protected void onCreate(Bundle savedInstanceState) {

        isSetPhoto = false;
        isText = false;
        location = "";
        message = "";

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                //将侧边栏顶部延伸至status bar

            }
        }

        setContentView(R.layout.yowall);
        //得到屏幕大小
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;

        editText = (EditText)findViewById(R.id.editText);
        editText.setHintTextColor(getResources().getColor(R.color.color5));
        textView = (TextView)findViewById(R.id.textView_location);
        imageView = (ImageView)findViewById(R.id.imageView_my_photo);
        floatingActionButton =(FloatingActionButton)findViewById(R.id.floatingActionButton_sendyowall);
        imageView_back = (ImageView)findViewById(R.id.back);
        floatingActionButton.setAlpha(0.4f);

        initDatas();
        setmRecyclerView();

        editText.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().toString().trim().equals("")) {
                    floatingActionButton.setAlpha(0.4f);
                    isText = false;
                } else {
                    floatingActionButton.setAlpha(1f);
                    isText = true;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isText) {
                    tel = Num_send.getTel();
                    time = Time.dateforName();

                    if (isSetPhoto) {

                        photo_name = tel + Time.dateforName();
                        Log.e("yo", photo_name);
                        //上传图片
                        queue.add(Upload.uploadyowallphoto(bitmap_yowall, tel, photo_name));
                    } else {
                        photo_name = "";
                        Log.e("yo", photo_name);
                    }
                    message = editText.getText().toString().trim();


                    //上传其他信息
                    //上传到yowall总表
                    queue.add(Upload.upload_yowall(tel, time, location, message, photo_name));


                    rotate(floatingActionButton);

                } else {
                    //没输入文字则不发送
                }


            }
        });

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YowallActivity.this.finish();
            }
        });

    }

    private void setmRecyclerView(){

        mRecyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        //mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter =  new MyRecyclerViewAdapter(this,datas);
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

        mAdapter.setOnItemListener(new MyRecyclerViewAdapter.OnItemListener() {
            @Override
            public void onClick(View v, int position) {


                //点击响应
                if (position == 0) {
                        showNormalDialog();
                    //finish();
                } else if (position == 1) {
                    showListDialog();

                }
            }
        });


    }

    private void initDatas() {

        //从服务器得到图片


        datas = new ArrayList<>();
        final List_others list1 = new List_others(1,R.mipmap.my_photo_blue,"添加图片","");
        List_others list2 = new List_others(2,R.mipmap.my_location_blue,"位置","");


        datas.add(list2);
        datas.add(list1);



    }

    private void showNormalDialog(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(YowallActivity.this);



        normalDialog.setTitle("订制位置");
        final EditText edText = new EditText(this);
        normalDialog.setView((edText), 30, 20, 30, 0);

        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(edText.getText().toString().trim());
                        location = edText.getText().toString().trim();
                        //上传给服务器id还要增加图片


                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });

        normalDialog.show();

    }

    private void showListDialog() {
        final String[] items = { "从相册中选择","拍摄照片" };
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(YowallActivity.this);
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    choseHeadImageFromGallery();

                    //上传给服务器还要上传原图
                } else {
                    choseHeadImageFromCameraCapture();
                }// which 下标从0开始
                // ...To-do

            }
        });
        listDialog.show();
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

            bitmap_yowall =photo;


            //新建文件夹 先选好路径 再调用mkdir函数 现在是根目录下面的Ask文件夹
            File nf = new File(Environment.getExternalStorageDirectory()+"/Ask");
            nf.mkdir();

            //在根目录下面的ASk文件夹下 创建okkk.jpg文件
            File f = new File(Environment.getExternalStorageDirectory()+"/Ask", "ok.png");
            Uri uri = Uri.fromFile(f);
            //上传给服务器

            isSetPhoto = true;

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

    private void rotate(final View view){
        float k;
        k =360f;

        ValueAnimator mAnimator = ValueAnimator.ofFloat(k, 0);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                float animatorValue = (float) animation.getAnimatedValue();
                view.setRotation(-animatorValue);

            }
        });
        mAnimator.setDuration(600);
        mAnimator.setInterpolator(new DecelerateInterpolator());
        mAnimator.setTarget(view);
        mAnimator.start();

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                Intent intent = new Intent(YowallActivity.this, Myyowall.class);
                startActivity(intent);
                YowallActivity.this.finish();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }



}
