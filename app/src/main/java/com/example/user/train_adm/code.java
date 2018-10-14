package com.example.user.train_adm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    String Career;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

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

            }
        });

        pageup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
