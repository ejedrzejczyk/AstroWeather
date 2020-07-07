package com.example.astroweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AstroweatherActivity extends AppCompatActivity {


    private TextView time;
    private TextView location;
    private ViewPager viewPager;
    private TabLayout tabs;
    private ViewPagerAdapter viewPagerAdapter;
    AstroCalculator astroCalculator;
    JSONObject totalWeather;
    Handler mHandler = new Handler();

    private boolean enabled;
    private double longitude;
    private double latitude;
    private int unit;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astroweather);
        enabled = true;

        time = findViewById(R.id.time);
        location = findViewById(R.id.location);

        unit = getIntent().getBundleExtra("bundle").getInt("unit");

        try {
            totalWeather = new JSONObject(getIntent().getBundleExtra("bundle").getString("weather"));
            latitude = totalWeather.getJSONObject("location").getDouble("lat");
            longitude = totalWeather.getJSONObject("location").getDouble("lon");
        } catch (Exception e) {
            e.printStackTrace();
        }

        location.setText("Longitude: " + longitude + "   Latitude: " + latitude);

        Calendar c = Calendar.getInstance();
        AstroDateTime dateTime = new AstroDateTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND),
                -(c.get(Calendar.ZONE_OFFSET) + c.get(Calendar.DST_OFFSET)) / (60 * 1000), c.getTimeZone().inDaylightTime(c.getTime()));

        astroCalculator = new AstroCalculator(dateTime, new AstroCalculator.Location(latitude, longitude));

        viewPager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabs);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), astroCalculator, totalWeather, unit);
        viewPager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        enabled = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        enabled = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        timer(dateFormat);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putDouble("longitude", longitude);
        outState.putDouble("latitude", latitude);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        location.setText("Longitude: " + savedInstanceState.getDouble("longitude") + "   Latitude: " + savedInstanceState.getDouble("latitude"));
    }

    public void timer(final SimpleDateFormat dateFormat){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(enabled){
                    try {
                        Thread.sleep(1000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                String formattedDate = dateFormat.format(Calendar.getInstance().getTime());
                                time.setText(formattedDate);
                                //Toast.makeText(getApplicationContext(), "refreshed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    catch (Exception e) {
                    }
                }
            }
        }).start();
    }
}
