package com.example.user.train_adm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Button next;
        final RadioGroup group;

        next = findViewById(R.id.next);
        group = findViewById(R.id.group);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(group.getCheckedRadioButtonId() == R.id.train){
                    SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
                    name.edit().putString("Career","train").commit();
                    pageup();
                }else if(group.getCheckedRadioButtonId() == R.id.station){
                    SharedPreferences name = getSharedPreferences("name",MODE_PRIVATE);
                    name.edit().putString("Career","station").commit();
                    pageup();
                }
            }
        });

    }
    public void pageup() {
        Intent intent = new Intent(this,code.class);
        startActivity(intent);
        finish();
    }
}
