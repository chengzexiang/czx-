package com.bwei.czx.jinritoutiao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText phonenumber;
    private EditText yanzheng;
    private Button huoqu;
    private Button degnlu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        SMSSDK.registerEventHandler(eh); //注册短信回调

        phonenumber = (EditText) findViewById(R.id.phonenumber);
        yanzheng = (EditText) findViewById(R.id.yianzhengnumber);
        huoqu = (Button) findViewById(R.id.huoqu);
        degnlu = (Button) findViewById(R.id.dengluphone);
        huoqu.setOnClickListener(this);
        degnlu.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.huoqu:
                SMSSDK.getVerificationCode("86",phonenumber.getText().toString() , new OnSendMessageHandler() {
                    @Override
                    public boolean onSendMessage(String s, String s1) {

                        return false;
                    }
                });
                break;
            case R.id.dengluphone:
                SMSSDK.submitVerificationCode("86", phonenumber.getText().toString(), yanzheng.getText().toString());
                Intent intent = new Intent(PhoneActivity.this,SecondActivity.class);
                intent.putExtra("phonenumber",phonenumber.getText().toString());
                startActivity(intent);
                break;
        }
    }


    EventHandler eh=new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PhoneActivity.this,"提交验证码成功",Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PhoneActivity.this,"已发送",Toast.LENGTH_SHORT).show();

                                }
                            }
                    );

                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                ((Throwable)data).printStackTrace();
                //失败后
                Toast.makeText(PhoneActivity.this,"发送失败",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PhoneActivity.this,SecondActivity.class);
                startActivity(intent);

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }
}
