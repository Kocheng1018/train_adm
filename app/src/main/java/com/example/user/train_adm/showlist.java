package com.example.user.train_adm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class showlist extends AppCompatActivity {

    private static int check_status = -1;
    NetworkInfo mNetworkInfo;
    List<String> wheel = new ArrayList<>();
    List<String> crutch  = new ArrayList<>();
    List<String> board = new ArrayList<>();
    List<String> travelhelp = new ArrayList<>();
    List<String> notice = new ArrayList<>();
    List<String> num = new ArrayList<>();
    List<String> seat = new ArrayList<>();
    List<Map<String, Object>> titlemix = new ArrayList();
    List<String> mix = new ArrayList<>();
    SimpleAdapter adapter;
    ListView record;
    TextView detail;
    String date, Career, key, code;
    Button status,backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlist);

        final ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        record = findViewById(R.id.record);
        detail = findViewById(R.id.detail);
        status = findViewById(R.id.finish);
        backbtn = findViewById(R.id.backbtn);

        detail.setMovementMethod(new ScrollingMovementMethod());

        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormatDate.format(new java.util.Date());

        Career = getSharedPreferences("name",MODE_PRIVATE)
                .getString("Career", "");


        if(Career.equals("train")){
            key = "catchtrain";
            code = getSharedPreferences("name",MODE_PRIVATE)
                    .getString("train_code", "");
        }else if(Career.equals("station")){
            key = "catchstation";
            code = getSharedPreferences("name",MODE_PRIVATE)
                    .getString("code", "");
        }
        adapter = new SimpleAdapter(this,Dataget(),
                R.layout.db_service,
                new String[] {"time","start","end","client"},
                new int[] {R.id.time,R.id.start,R.id.end,R.id.client});
        record.setAdapter(adapter);

        if(num.size() == 0){
            new AlertDialog.Builder(showlist.this)
                    .setTitle("確認視窗")
                    .setMessage("無服務需求!")
                    .setPositiveButton("確定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,int which) {
                                    if(Career.equals("train")){
                                        Intent myIntent = new Intent();
                                        myIntent = new Intent(showlist.this,code.class);
                                        startActivity(myIntent);
                                        finish();
                                    }else{
                                        Intent myIntent = new Intent();
                                        myIntent = new Intent(showlist.this,station.class);
                                        startActivity(myIntent);
                                        finish();
                                    }
                                }
                            }).show().setCancelable(false);
        }

        backbtn.setOnClickListener(new OnMultiClickListener(){
            @Override
            public void onMultiClick(View v) {
                if(key.equals("catchtrain"))
                    backpage();
                else if (key.equals("catchstation"))
                    backpage1();
            }
        });

        record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                check_status = position;
                detail.setText(mix.get(position));
            }
        });

        status.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    if (check_status != -1) {
                        AlertDialog.Builder aa = new AlertDialog.Builder(showlist.this);
                        aa.setTitle("確認視窗");
                        aa.setMessage("確定完成此訂單?");
                        aa.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                statuscheck();
                            }
                        });
                        aa.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        aa.show();
                    } else {
                        Toast toast = Toast.makeText(showlist.this, "請點選訂單", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }else{
                    new AlertDialog.Builder(showlist.this)
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

    //連線
    public static class DBConnector extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            String file = params[0];
            String datas = params[1];
            String result = post(file, datas);
            return result;
        }
    }
    //Send Post
    public static String post(String file,String datas){
        String result = "";
        HttpURLConnection urlConnection = null;
        final StringBuilder builder = new StringBuilder();
        try{
            URL url = new URL("http://163.17.136.194/lunchparty/train/" + file + ".php");
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(datas);
            wr.flush();

            //從database開啟stream
            InputStream is = urlConnection.getInputStream();
            int d = -1;
            byte[] data= new byte[256];
            while((d = is.read(data)) > -1){
                builder.append(new String(data,0,d));
            }
            result = builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    //catchData
    public List<Map<String, Object>> Dataget() {
        DBConnector dbConnector = new DBConnector();
        String datas = null;
        try {
            datas = URLEncoder.encode("key","UTF8")
                    + "=" + URLEncoder.encode(key,"UTF8");
            datas += "&" + URLEncoder.encode("date", "UTF-8")
                    + "=" + URLEncoder.encode(date, "UTF-8");
            datas += "&" + URLEncoder.encode("code", "UTF-8")
                    + "=" + URLEncoder.encode(code, "UTF-8");
            String result = dbConnector.execute("actionadm",datas).get();
            JSONArray records = new JSONArray(result);
            for (int i = 0;i < records.length(); i++){
                Map map = new HashMap();
                JSONObject record = records.getJSONObject(i);

                num.add(record.getString("num"));

                map.put("time",record.getString("time")); //時間
                map.put("start",record.getString("start")); //起站
                map.put("end",record.getString("end")); //終站
                if(record.getString("sex").equals("0")){
                    map.put("client",record.getString("name") + "  先生"); //名字
                }else {
                    map.put("client",record.getString("name") + "  小姐"); //名字
                }

                wheel.add(record.getString("wheel"));
                if(wheel.get(i).equals("1"))
                    wheel.set(i,"輪椅\n");
                else
                    wheel.set(i,"");

                crutch.add(record.getString("crutch"));
                if(crutch.get(i).equals("1"))
                    crutch.set(i,"拐杖\n");
                else
                    crutch.set(i,"");

                board.add(record.getString("board"));
                if(board.get(i).equals("1"))
                    board.set(i,"鍍板\n");
                else
                    board.set(i,"");

                travelhelp.add(record.getString("travelhelp"));
                if(travelhelp.get(i).equals("1"))
                    travelhelp.set(i,"乘車提醒\n");
                else
                    travelhelp.set(i,"");

                notice.add(record.getString("notice"));
                if(notice.get(i).equals("1"))
                    notice.set(i,"下車提醒\n");
                else
                    notice.set(i,"");

                seat.add(record.getString("seat"));
                if(seat.get(i).equals("1"))
                    seat.set(i,"博愛座\n");
                else
                    seat.set(i,"");

                mix.add(wheel.get(i) + crutch.get(i) + board.get(i) + travelhelp.get(i) + notice.get(i) + seat.get(i));
                titlemix.add(map);
            }
            return titlemix;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return titlemix;
    }

    public void statuscheck() {
        DBConnector dbConnector = new DBConnector();
        String datas = null;
        try {
            datas = URLEncoder.encode("key","UTF8")
                    + "=" + URLEncoder.encode("statuscheck","UTF8");
            datas += "&" + URLEncoder.encode("num", "UTF-8")
                    + "=" + URLEncoder.encode(num.get(check_status), "UTF-8");
            String result = dbConnector.execute("actionadm",datas).get();

            JSONObject record = new JSONObject(result);

                if (record.getString("code").equals("1")) {
                    num.remove(check_status);
                    wheel.remove(check_status);
                    crutch.remove(check_status);
                    board.remove(check_status);
                    travelhelp.remove(check_status);
                    notice.remove(check_status);
                    seat.remove(check_status);
                    titlemix.remove(check_status);
                    mix.remove(check_status);
                    adapter.notifyDataSetChanged();
                    detail.setText("此訂單已完成!");
                    check_status = -1;
                } else {
                    Toast tosat = Toast.makeText(showlist.this, "Error!", Toast.LENGTH_SHORT);
                    tosat.show();
                }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void backpage() {
        Intent intent = new Intent(this,code.class);
        startActivity(intent);
        SharedPreferences name = getSharedPreferences(code,MODE_PRIVATE);
        name.edit().putString(code,"");
        finish();
    }
    public void backpage1() {
        Intent intent = new Intent(this,station.class);
        startActivity(intent);
        SharedPreferences name = getSharedPreferences(code,MODE_PRIVATE);
        name.edit().putString(code,"");
        finish();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_BACK){
                if(Career.equals("train")){
                    Intent myIntent = new Intent();
                    myIntent = new Intent(showlist.this,code.class);
                    startActivity(myIntent);
                    this.finish();
                }else{
                    Intent myIntent = new Intent();
                    myIntent = new Intent(showlist.this,station.class);
                    startActivity(myIntent);
                    this.finish();
                }
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
