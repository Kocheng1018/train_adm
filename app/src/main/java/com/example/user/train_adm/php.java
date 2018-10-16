package com.example.user.train_adm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class php extends AppCompatActivity {
    Button back;
    WebView wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_php);
        back = findViewById(R.id.back);
        wb = findViewById(R.id.html);

        back.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                Intent intent = new Intent();   //intent實體化
                intent.setClass(php.this, choose.class);
                startActivity(intent);    //startActivity觸發換頁
                finish();
            }
        });
        wb.getSettings().setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient()); //不調用系統瀏覽器
        wb.loadUrl("http://163.17.136.194/lunchparty/ibus/index.php");
    }

    public abstract class OnMultiClickListener implements View.OnClickListener{
        // 两次点击按钮之间的点击间隔不能少于1500毫秒
        private static final int MIN_CLICK_DELAY_TIME = 1500;
        private long lastClickTime;
        public abstract void onMultiClick(View v);
        @Override
        public void onClick(View v) {
            long curClickTime = System.currentTimeMillis();
            if((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                // 超过点击间隔后再将lastClickTime重置为当前点击时间
                lastClickTime = curClickTime;
                onMultiClick(v);
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent = new Intent(php.this, choose.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
