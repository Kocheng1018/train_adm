package com.example.user.train_adm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class choose extends AppCompatActivity {
    NetworkInfo mNetworkInfo;
    Button trainbtn,stationbtn,htmlbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        final ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        trainbtn = findViewById(R.id.trainbtn);
        stationbtn = findViewById(R.id.stationbtn);
        htmlbtn = findViewById(R.id.html);

        trainbtn.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
                name.edit().putString("Career","train").commit();
                pageup();
            }
        });
        stationbtn.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
                name.edit().putString("Career","station").commit();
                pageup1();
            }
        });
        htmlbtn.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    Intent intent = new Intent();   //intent實體化
                    intent.setClass(choose.this, php.class);
                    startActivity(intent);    //startActivity觸發換頁
                    finish();
                }else{
                    new AlertDialog.Builder(choose.this)
                            .setTitle("網路偵測")
                            .setMessage("請檢查網路連線!")
                            .setPositiveButton("確定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,int which) {
                                        }
                                    }).show();
                }
            }
        });
    }
    public void pageup() {
        Intent intent = new Intent(this,code.class);
        startActivity(intent);
        finish();
    }
    public void pageup1() {
        Intent intent = new Intent(this,station.class);
        startActivity(intent);
        finish();
    }
    public abstract class OnMultiClickListener implements View.OnClickListener{
        private static final int MIN_CLICK_DELAY_TIME = 1500;
        private long lastClickTime;
        public abstract void onMultiClick(View v);
        @Override
        public void onClick(View v) {
            long curClickTime = System.currentTimeMillis();
            if((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                lastClickTime = curClickTime;
                onMultiClick(v);
            }
        }
    }
}
