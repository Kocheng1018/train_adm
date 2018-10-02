package com.example.user.train_adm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

        Career = getSharedPreferences("name",MODE_PRIVATE)
                .getString("Career","");

        if(Career.equals("train")){
            TXT.setText("請輸入車次代碼");
            imageView2.setImageResource(R.drawable.trainimage);
        }else if(Career.equals("station")){
            TXT.setText("請輸入車站名稱");
            imageView2.setImageResource(R.drawable.stationimage);
        }else {
            TXT.setText("ERROR");
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
                name.edit().putString("code",codein.getText().toString()).commit();
                pagedowm();
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
    }
    public void pageup() {
            Intent intent = new Intent(this,choose.class);
            startActivity(intent);
            finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent = new Intent(code.this, choose.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
