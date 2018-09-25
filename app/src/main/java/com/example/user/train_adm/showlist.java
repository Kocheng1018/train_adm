package com.example.user.train_adm;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
import java.util.Calendar;
import java.util.Date;
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

    final Calendar c = Calendar.getInstance();

    ListView record;
    TextView date;
    int Year, Month, Day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlist);

        record = findViewById(R.id.record);
        date = findViewById(R.id.date);

        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date systemdate = new Date(System.currentTimeMillis());
        date.setText(simpleDateFormatDate.format(systemdate));

        Dataget();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Year = c.get(Calendar.YEAR);
                Month = c.get(Calendar.MONTH);
                Day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(showlist.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear+1;
                        date.setText(year + "-" + format(monthOfYear) + "-" + format(dayOfMonth));
                    }
                }, Year, Month, Day);
                datePickerDialog.show();
                Dataget();
            }
        });


        record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                //新增一個彈跳視窗
                AlertDialog.Builder alert = new AlertDialog.Builder(showlist.this);
                alert.setTitle(name.get(position) + sex.get(position));

                alert.setMessage(mix.get(position));

                alert.setNegativeButton(Html.fromHtml("<font color='#FF7F27'>確定</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
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
            URL url = new URL("http://163.17.136.194/lunchparty/" + file + ".php");
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
                    + "=" + URLEncoder.encode("catchservice","UTF8");
            datas += "&" + URLEncoder.encode("date", "UTF-8")
                    + "=" + URLEncoder.encode(date.getText().toString(), "UTF-8");
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

                titlemix.add(time.get(i) + " " + start.get(i) + " " + end.get(i) + " " + name.get(i) + " " + sex.get(i));
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
        private String format(int x) {
            String s = "" + x;
            if (s.length() == 1)
                s = "0" + s;
            return s;
        }


}
