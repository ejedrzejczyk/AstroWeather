package com.example.astroweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText mCity;
    private Spinner mUnit;
    private Button mLoad;
    private CheckBox mFav;
    private DBManager dbManager;
    JSONObject totalWeather;

    private int unit;
    private boolean isNull = true;
    private boolean fav;

    SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE);

        mCity = findViewById(R.id.cityName);
        mUnit = findViewById(R.id.unit);
        mLoad = findViewById(R.id.enter);
        mFav = findViewById(R.id.fav);

        mCity.setText(sharedPreferences.getString("favCity", ""));

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.units, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        mUnit.setAdapter(adapter);
        mUnit.setOnItemSelectedListener(this);



        dbManager = new DBManager(getApplicationContext());
        dbManager.open();

        mLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCity.getText().length() != 0){
                    Bundle bundle = new Bundle();
                    bundle.putInt("unit", unit);
                    Intent intent = new Intent(MainActivity.this, AstroweatherActivity.class);

                    if(isNetworkConnected()){
                        Weather weather = new Weather();
                        try {
                            totalWeather = weather.search(mCity.getText().toString());
                            bundle.putString("weather", totalWeather.toString());
                            if(isCityInDB(mCity.getText().toString())){
                                dbManager.update(mCity.getText().toString(), totalWeather.toString());
                            }
                            else {
                                dbManager.insert(mCity.getText().toString(), totalWeather.toString());
                            }
                            Toast.makeText(getApplicationContext(), "INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        intent.putExtra("bundle", bundle);
                        startActivity(intent);
                    }
                    else{
                        try{
                            Cursor cursor = dbManager.fetch(mCity.getText().toString());
                            bundle.putString("weather", cursor.getString(cursor.getColumnIndex(DBHelper.WEATHER_INFO)));
                            intent.putExtra("bundle", bundle);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "FROM DATABASE", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                fav = mFav.isChecked();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(fav){
                    editor.putString("favCity", mCity.getText().toString());
                    editor.apply();
                }
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private boolean isCityInDB(String cityName){
        Cursor cursor = dbManager.fetch(cityName);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mCity.getText().length() != 0){
            outState.putString("city", mCity.getText().toString());
            isNull = false;
        }
        outState.putInt("unit", mUnit.getSelectedItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(!isNull){
            mCity.setText(savedInstanceState.getString("city"));
        }
        mUnit.setSelection(savedInstanceState.getInt("unit"));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                unit = 0;
                break;
            case 1:
                unit = 1;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
