package com.example.user.train_adm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class station extends AppCompatActivity {

    Button next, page_up;
    String code;
    Spinner block, station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        next = findViewById(R.id.next);
        page_up = findViewById(R.id.page_up);
        block = findViewById(R.id.block);
        station = findViewById(R.id.station);

        block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = block.getSelectedItemPosition();
                int index = 0; //預設spinner位址
                ArrayAdapter location = change(pos);
                station.setAdapter(location);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
                name.edit().putString("code", station.getSelectedItem().toString()).commit();
                pagedowm();
            }
        });

        page_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences name = getSharedPreferences("name",MODE_PRIVATE);
                name.edit().putString("code","");
                backpage();
            }
        });

    }

    //把spinner的資料對應進去
    public ArrayAdapter change(int pos){
        switch (pos) {
            case 0:
                ArrayAdapter location = new ArrayAdapter(station.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.north));
                return location;

            case 1:
                ArrayAdapter location1 = new ArrayAdapter(station.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.middle));
                return location1;

            case 2:
                ArrayAdapter location2 = new ArrayAdapter(station.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.south));
                return location2;

            case 3:
                ArrayAdapter location3 = new ArrayAdapter(station.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.east));
                return location3;
        }
        return null;
    }

    public void pagedowm() {
        Intent intent = new Intent(this,showlist.class);
        startActivity(intent);
    }

    public void backpage() {
        Intent intent = new Intent(this,choose.class);
        startActivity(intent);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent = new Intent(station.this, choose.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
