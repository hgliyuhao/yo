package com.example.lenovo.yot;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by lenovo on 2017/5/29.
 */
public class Fragment_login extends Fragment{

    EditText editText_userId;
    EditText editText_password;
    Button button;
    private login_Callbacks callbacks;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (login_Callbacks) context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment_login = inflater.inflate(R.layout.login_fragment,
                container, false);
        editText_userId = (EditText)fragment_login.findViewById(R.id.login_editText_userId);
        editText_password = (EditText)fragment_login.findViewById(R.id.login_editText_password);
        button = (Button)fragment_login.findViewById(R.id.login_button_login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(editText_userId.getText().toString().trim().equals("")){

                    callbacks.login_sendUserId("*用户名不能为空");

                }
                else if(editText_password.getText().toString().trim().equals("")){

                    callbacks.login_sendUserId("*请输入密码");

                }
                else{
                   callbacks.login_sendUserinfo(editText_userId.getText().toString().trim(),editText_password.getText().toString().trim());
                }
            }
        });



        return fragment_login;
    }
    public interface login_Callbacks{
        void login_sendUserId(String textView_zhushi);
        void login_sendUserinfo(String UserId, String password);
    }
}
