package com.example.astroweather;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Weather extends AsyncTask<String, Void, String> {

    public JSONObject search(String cityName) throws JSONException, ExecutionException, InterruptedException {
        String content;
        Weather weather = new Weather();

        content = weather.execute("http://api.weatherapi.com/v1/forecast.json?key=d2e14e453dda4494b11153229200207&q="+cityName+"&days=3").get();

        JSONObject total = new JSONObject(content);

        return total;
    }

    @Override
    protected String doInBackground(String... address) {

        try{
            URL url = new URL(address[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int data = isr.read();
            String result = "";
            //char ch;
            while(data > 0){
                //ch = (char) data;
                result = result + (char) data;
                data = isr.read();
            }
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
