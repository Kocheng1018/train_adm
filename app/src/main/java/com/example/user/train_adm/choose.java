package com.example.user.train_adm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Button trainbtn,stationbtn;

        trainbtn = findViewById(R.id.trainbtn);
        stationbtn = findViewById(R.id.stationbtn);

        trainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
                    name.edit().putString("Career","train").commit();
                    pageup();
            }
        });
        stationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
                name.edit().putString("Career","station").commit();
                pageup();

            }
        });
    }
    public void pageup() {
        Intent intent = new Intent(this,code.class);
        startActivity(intent);
        finish();
    }
}
