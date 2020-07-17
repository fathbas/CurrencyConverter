package com.fatihb.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tr;
    TextView us;
    TextView ch;
    TextView ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tr = findViewById(R.id.tryTxt);
        us = findViewById(R.id.usdTxt);
        ch = findViewById(R.id.chfTxt);
        ca = findViewById(R.id.cadTxt);
    }

    public void getRates(View view){
        Data data = new Data();

        try {
            String url = "http://data.fixer.io/api/latest?access_key=606af03a367ae2411096a8038b811164&format=1";
            data.execute(url);
        }catch (Exception e){

        }

    }

    private class Data extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStream.read();
                while (data>0){
                    char datas = (char)data;
                    result+=datas;
                    data=inputStreamReader.read();
                }
                return result;
            }catch (Exception e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                String base = jsonObject.getString("base");
                String rates = jsonObject.getString("rates");

                JSONObject jsonObject1 = new JSONObject(rates);
                String tryL = jsonObject1.getString("TRY");
                String usdL = jsonObject1.getString("USD");
                String chfL = jsonObject1.getString("CHF");
                String cadL = jsonObject1.getString("CAD");

                tr.setText("TRY: "+tryL);
                us.setText("USD: "+usdL);
                ch.setText("CHF: "+chfL);
                ca.setText("CAD: "+cadL);

            }catch (Exception e){

            }
        }
    }
}
