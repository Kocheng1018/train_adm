package com.example.user.train_adm;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

public class showlist extends AppCompatActivity {

    private static int check_status = -1;

    List<String> start = new ArrayList<>();
    List<String> end = new ArrayList<>();
    List<String> name = new ArrayList<>();
    List<String> sex = new ArrayList<>();
    List<String> wheel = new ArrayList<>();
    List<String> crutch  = new ArrayList<>();
    List<String> board = new ArrayList<>();
    List<String> travelhelp = new ArrayList<>();
    List<String> notice = new ArrayList<>();
    List<String> seat = new ArrayList<>();
    List<String> time = new ArrayList<>();
    List<String> num = new ArrayList<>();
    List<String> titlemix = new ArrayList<>();
    List<String> mix = new ArrayList<>();

    ArrayAdapter adapter;
    ListView record;
    TextView detail;
    String date, Career, key, code, statusnum;
    Button status,backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlist);

        record = findViewById(R.id.record);
        detail = findViewById(R.id.detail);
        status = findViewById(R.id.finish);
        backbtn = findViewById(R.id.backbtn);

        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormatDate.format(new java.util.Date());

        Career = getSharedPreferences("name",MODE_PRIVATE)
                .getString("Career", "");
        code = getSharedPreferences("name",MODE_PRIVATE)
                .getString("code", "");

        if(Career.equals("train")){
            key = "catchtrain";
        }else if(Career.equals("station")){
            key = "catchstation";
        }

        Dataget();
        detail.setText(Career);
        detail.append(code);
        detail.append(date);

        backbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
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
                detail.setText(name.get(position) + sex.get(position));
                detail.setText(mix.get(position));
                statusnum = num.get(position);
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_status != -1){
                    AlertDialog.Builder aa = new AlertDialog.Builder(showlist.this);
                    aa.setTitle("確認視窗");
                    aa.setMessage("確定刪除此訂單");
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
                }else {
                    Toast toast = Toast.makeText(showlist.this, "請點選訂單", Toast.LENGTH_LONG);
                    toast.show();
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
    public void Dataget() {
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
                JSONObject record = records.getJSONObject(i);

                num.add(record.getString("num"));
                time.add(record.getString("time"));
                start.add(record.getString("start"));
                end.add(record.getString("end"));
                name.add(record.getString("name"));
                sex.add(record.getString("sex"));
                if(sex.get(i).equals("0"))
                    sex.set(i,"先生");
                else
                    sex.set(i,"小姐");

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

                titlemix.add(format(time.get(i),4) + " " + format(start.get(i),4) + " " + format(end.get(i),4) + " " + format(name.get(i),4) + " " + sex.get(i));
                mix.add(wheel.get(i) + crutch.get(i) + board.get(i) + travelhelp.get(i) + notice.get(i) + seat.get(i));

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

        adapter = new ArrayAdapter(showlist.this, android.R.layout.simple_list_item_1,titlemix);
        record.setAdapter(adapter);
    }

    public void statuscheck() {
        DBConnector dbConnector = new DBConnector();
        String datas = null;
        try {
            datas = URLEncoder.encode("key","UTF8")
                    + "=" + URLEncoder.encode("statuscheck","UTF8");
            datas += "&" + URLEncoder.encode("num", "UTF-8")
                    + "=" + URLEncoder.encode(statusnum, "UTF-8");
            String result = dbConnector.execute("actionadm",datas).get();

            JSONObject record = new JSONObject(result);

                if (record.getString("code").equals("1")) {
                    start.remove(check_status);
                    end.remove(check_status);
                    name.remove(check_status);
                    sex.remove(check_status);
                    wheel.remove(check_status);
                    crutch.remove(check_status);
                    board.remove(check_status);
                    travelhelp.remove(check_status);
                    notice.remove(check_status);
                    seat.remove(check_status);
                    time.remove(check_status);
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

    private String format(String x, int y) {
        String s = "" + x;
        while (s.length() <= y){
            s = "  " + s;
        }
        return s;
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
}
