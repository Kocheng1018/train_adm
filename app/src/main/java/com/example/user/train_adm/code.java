package com.example.user.train_adm;

import android.content.Context;
<<<<<<< HEAD
import android.content.DialogInterface;
=======
<<<<<<< HEAD
import android.content.DialogInterface;
=======
>>>>>>> ad62006ae9c37db108928c62f2c4e301a89ca42b
>>>>>>> d9260aaea814d07176238938eb11a9fc76d13ddb
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class code extends AppCompatActivity {

    NetworkInfo mNetworkInfo;
    ImageView imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        final ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final Button search, pageup;
        final EditText codein;
        TextView TXT;

        search = findViewById(R.id.search);
        pageup = findViewById(R.id.pageup);
        codein = findViewById(R.id.codein);
        TXT = findViewById(R.id.TXT);
        imageView2 =findViewById(R.id.imageView2);

        TXT.setText("請輸入車次代碼");
        imageView2.setImageResource(R.drawable.trainimage);

        SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
        String coco = name.getString("train_code","");
        codein.setText(coco);
<<<<<<< HEAD
        search.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if(mNetworkInfo != null) {
                    SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
                    name.edit().putString("train_code", codein.getText().toString()).commit();
                    if (!(codein.getText().toString().equals(""))) {
                        pagedowm();
                    } else {
                        Toast.makeText(code.this, "請輸入班次!!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    new AlertDialog.Builder(code.this)
                            .setTitle("網路偵測")
                            .setMessage("請檢查網路連線!")
                            .setPositiveButton("確定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,int which) {
                                        }
                                    }).show();
                }
=======
<<<<<<< HEAD
        search.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if(mNetworkInfo != null) {
                    SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
                    name.edit().putString("train_code", codein.getText().toString()).commit();
                    if (!(codein.getText().toString().equals(""))) {
                        pagedowm();
                    } else {
                        Toast.makeText(code.this, "請輸入班次!!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    new AlertDialog.Builder(code.this)
                            .setTitle("網路偵測")
                            .setMessage("請檢查網路連線!")
                            .setPositiveButton("確定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,int which) {
                                        }
                                    }).show();
                }
=======
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
                name.edit().putString("train_code",codein.getText().toString()).commit();
                if(!(codein.getText().toString().equals(""))){
                    pagedowm();
                }else{
                    Toast.makeText(code.this,"請輸入班次!!", Toast.LENGTH_SHORT).show();
                }

>>>>>>> ad62006ae9c37db108928c62f2c4e301a89ca42b
>>>>>>> d9260aaea814d07176238938eb11a9fc76d13ddb
            }
        });
        pageup.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                pageup();
            }
        });
    }

    public void pagedowm() {
        Intent intent = new Intent(this,showlist.class);
        startActivity(intent);
        finish();
    }
    public void pageup() {
            Intent intent = new Intent(this,choose.class);
            startActivity(intent);
            finish();
    }
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======

>>>>>>> ad62006ae9c37db108928c62f2c4e301a89ca42b
>>>>>>> d9260aaea814d07176238938eb11a9fc76d13ddb
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent = new Intent(code.this, choose.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> d9260aaea814d07176238938eb11a9fc76d13ddb
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
<<<<<<< HEAD
=======
=======
>>>>>>> ad62006ae9c37db108928c62f2c4e301a89ca42b
>>>>>>> d9260aaea814d07176238938eb11a9fc76d13ddb
}
