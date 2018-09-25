package com.example.user.train_adm;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
    List<String> titlemix = new ArrayList<>();
    List<String> mix = new ArrayList<>();

    ListView record;
    TextView detail;
    String date, Career, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlist);

        record = findViewById(R.id.record);
        detail = findViewById(R.id.detail);

        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormatDate.format(new java.util.Date());

        Career = getSharedPreferences("name",MODE_PRIVATE)
                .getString("Career", "");

        if(Career.equals("train")){
            key = "catchtrain";
        }else if(Career.equals("station")){
            key = "catchstation";
        }

        Dataget();

        record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                detail.setText(name.get(position) + sex.get(position));
                detail.setText(mix.get(position));
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
            String result = dbConnector.execute("action",datas).get();
            JSONArray records = new JSONArray(result);
            for (int i = 0;i < records.length(); i++){
                JSONObject record = records.getJSONObject(i);

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
                    wheel.set(i,"輪椅");
                else
                    wheel.set(i,"");

                crutch.add(record.getString("crutch"));
                if(crutch.get(i).equals("1"))
                    crutch.set(i,"拐杖");
                else
                    crutch.set(i,"");

                board.add(record.getString("board"));
                if(board.get(i).equals("1"))
                    board.set(i,"鍍板");
                else
                    board.set(i,"");

                travelhelp.add(record.getString("travelhelp"));
                if(travelhelp.get(i).equals("1"))
                    travelhelp.set(i,"乘車提醒");
                else
                    travelhelp.set(i,"");

                notice.add(record.getString("notice"));
                if(notice.get(i).equals("1"))
                    notice.set(i,"下車提醒");
                else
                    notice.set(i,"");

                seat.add(record.getString("seat"));
                if(seat.get(i).equals("1"))
                    seat.set(i,"博愛座");
                else
                    seat.set(i,"");

                titlemix.add(format(time.get(i),4) + " " + format(start.get(i),4) + " " + format(end.get(i),4) + " " + format(name.get(i),4) + " " + sex.get(i));
                mix.add(wheel.get(i) + "\n" + crutch.get(i) + "\n" + board.get(i) + "\n" + travelhelp.get(i) + "\n" + notice.get(i) + "\n" + seat.get(i));

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

        ArrayAdapter adapter = new ArrayAdapter(showlist.this, android.R.layout.simple_list_item_1,titlemix);
        record.setAdapter(adapter);

    }
        private String format(String x, int y) {
            String s = "" + x;
            while (s.length() <= y){
                s = "  " + s;
            }
            return s;
        }


}
