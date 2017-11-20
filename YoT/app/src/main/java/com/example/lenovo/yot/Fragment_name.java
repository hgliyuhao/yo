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
 * Created by lenovo on 2017/6/23.
 */
public class Fragment_name extends Fragment {

    Button button;
    EditText editText;
    private login_Callbacks callbacks;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (login_Callbacks) context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment_name = inflater.inflate(R.layout.name_fragment,
                container, false);

        button = (Button)fragment_name.findViewById(R.id.button_gotosexc);
        editText = (EditText)fragment_name.findViewById(R.id.name_editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().trim().equals("")){
                    //用户名不能为空
                }
                else{
                    callbacks.sendUserName(editText.getText().toString().trim());
                }
            }
        });

        return fragment_name;
    }
    public interface login_Callbacks{
        void sendUserName(String textView_name);

    }

}
