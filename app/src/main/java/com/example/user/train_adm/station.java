package com.example.user.train_adm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
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
    NetworkInfo mNetworkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        final ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        next = findViewById(R.id.next);
        page_up = findViewById(R.id.page_up);
        block = findViewById(R.id.block);
        station = findViewById(R.id.station);

        ArrayAdapter taiwamblock = new ArrayAdapter(station.this,R.layout.view2content,getResources().getStringArray(R.array.list));
        block.setAdapter(taiwamblock);

        SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
        String localation = name.getString("block","0");
        block.setSelection(Integer.valueOf(localation));
        final String code = name.getString("code","0");

        block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = block.getSelectedItemPosition();
                int index = 0; //預設spinner位址
                ArrayAdapter location = change(pos);
                station.setAdapter(location);
                for(int i = 0;i < location.getCount();i++){
                    if(location.getItem(i).equals(code)){
                        index = i;
                        break;
                    }
                }
                station.setSelection(index);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        next.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if(mNetworkInfo != null) {
                    SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
                    name.edit().putString("code", station.getSelectedItem().toString()).commit();
                    name.edit().putString("block", String.valueOf(block.getSelectedItemPosition())).commit();
                    pagedowm();
                }else{
                    new AlertDialog.Builder(station.this)
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

        page_up.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
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
                        R.layout.view2content,
                        getResources().getStringArray(R.array.north));
                return location;

            case 1:
                ArrayAdapter location1 = new ArrayAdapter(station.this,
                        R.layout.view2content,
                        getResources().getStringArray(R.array.middle));
                return location1;

            case 2:
                ArrayAdapter location2 = new ArrayAdapter(station.this,
                        R.layout.view2content,
                        getResources().getStringArray(R.array.south));
                return location2;

            case 3:
                ArrayAdapter location3 = new ArrayAdapter(station.this,
                        R.layout.view2content,
                        getResources().getStringArray(R.array.east));
                return location3;
        }
        return null;
    }

    public void pagedowm() {
        Intent intent = new Intent(this,showlist.class);
        startActivity(intent);
        finish();
    }

    public void backpage() {
        Intent intent = new Intent(this,choose.class);
        startActivity(intent);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent;
            myIntent = new Intent(station.this, choose.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
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
