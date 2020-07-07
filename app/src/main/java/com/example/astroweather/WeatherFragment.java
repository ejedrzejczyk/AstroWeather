package com.example.astroweather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherFragment extends Fragment {

    private TextView mDate;
    private TextView mCityName;
    private ImageView icon;
    private TextView mDescription;
    private TextView mTemperature;
    private TextView mFeelsLike;
    private TextView mWindSpeed;
    private TextView mWindDir;
    private TextView mHumidity;
    private TextView mPressure;
    private TextView mChanceOfRain;
    private TableRow mFeelsLikeRow;
    private TableRow mWindDirRow;
    private TableRow mPressureRow;
    private TableRow mChanceOfRainRow;


    private String date;
    private String cityName;
    private String iconURL;
    private String description;
    private String temperature;
    private String feelsLike;
    private String windSpeed;
    private String windDir;
    private String humidity;
    private String pressure;
    private String chanceOfRain;
    private int position;

    public WeatherFragment() {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance(JSONObject totalWeather, int unit, int position) throws JSONException {
        WeatherFragment weatherFragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("cityName", totalWeather.getJSONObject("location").getString("name"));
        switch(position){
            case 2:
                bundle.putString("date", totalWeather.getJSONObject("location").getString("localtime"));
                bundle.putString("description", totalWeather.getJSONObject("current").getJSONObject("condition").getString("text"));
                bundle.putString("iconURL", totalWeather.getJSONObject("current").getJSONObject("condition").getString("icon"));
                bundle.putString("windDir", totalWeather.getJSONObject("current").getString("wind_dir"));
                bundle.putString("humidity", totalWeather.getJSONObject("current").getDouble("humidity") + " %");
                if(unit == 0){
                    bundle.putString("temp", totalWeather.getJSONObject("current").getDouble("temp_c") + "°C");
                    bundle.putString("feelsLike", totalWeather.getJSONObject("current").getDouble("feelslike_c") + "°C");
                    bundle.putString("windSpeed", totalWeather.getJSONObject("current").getDouble("wind_kph") + " km/h");
                    bundle.putString("pressure", totalWeather.getJSONObject("current").getDouble("pressure_mb") + " hPa");
                }
                else{
                    bundle.putString("temp", totalWeather.getJSONObject("current").getDouble("temp_f") + " F");
                    bundle.putString("feelsLike", totalWeather.getJSONObject("current").getDouble("feelslike_f") + " F");
                    bundle.putString("windSpeed", totalWeather.getJSONObject("current").getDouble("wind_mph") + " m/h");
                    bundle.putString("pressure", totalWeather.getJSONObject("current").getDouble("pressure_in") + " psi");
                }
                break;
            case 3:
                bundle.putString("date", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1).getString("date"));
                bundle.putString("description", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1).getJSONObject("day").getJSONObject("condition").getString("text"));
                bundle.putString("iconURL", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1).getJSONObject("day").getJSONObject("condition").getString("icon"));
                bundle.putString("humidity", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1).getJSONObject("day").getDouble("avghumidity") + " %");
                bundle.putString("chanceOfRain", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1).getJSONObject("day").getDouble("daily_chance_of_rain") + " %");
                if(unit == 0){
                    bundle.putString("temp", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1).getJSONObject("day").getDouble("avgtemp_c") + "°C");
                    bundle.putString("windSpeed", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1).getJSONObject("day").getDouble("maxwind_kph") + " km/h");
                }
                else{
                    bundle.putString("temp", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1).getJSONObject("day").getDouble("avgtemp_f") + " F");
                    bundle.putString("windSpeed", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1).getJSONObject("day").getDouble("maxwind_mph") + " m/h");
                }
                break;
            case 4:
                bundle.putString("date", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2).getString("date"));
                bundle.putString("description", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2).getJSONObject("day").getJSONObject("condition").getString("text"));
                bundle.putString("iconURL", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2).getJSONObject("day").getJSONObject("condition").getString("icon"));
                bundle.putString("humidity", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2).getJSONObject("day").getDouble("avghumidity") + " %");
                bundle.putString("chanceOfRain", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2).getJSONObject("day").getDouble("daily_chance_of_rain") + " %");
                if(unit == 0){
                    bundle.putString("temp", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2).getJSONObject("day").getDouble("avgtemp_c") + "°C");
                    bundle.putString("windSpeed", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2).getJSONObject("day").getDouble("maxwind_kph") + " km/h");
                }
                else{
                    bundle.putString("temp", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2).getJSONObject("day").getDouble("avgtemp_f") + " F");
                    bundle.putString("windSpeed", totalWeather.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2).getJSONObject("day").getDouble("maxwind_mph") + " m/h");
                }
                break;
        }

        weatherFragment.setArguments(bundle);
        return weatherFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        mDate = view.findViewById(R.id.date);
        mCityName = view.findViewById(R.id.city_name);
        icon = view.findViewById(R.id.icon);
        mDescription = view.findViewById(R.id.description);
        mTemperature = view.findViewById(R.id.temperature);
        mFeelsLike = view.findViewById(R.id.feels_like);
        mWindSpeed = view.findViewById(R.id.wind_speed);
        mWindDir = view.findViewById(R.id.wind_dir);
        mHumidity = view.findViewById(R.id.humidity);
        mPressure = view.findViewById(R.id.pressure);
        mChanceOfRain = view.findViewById(R.id.chance_of_rain);

        mFeelsLikeRow = view.findViewById(R.id.feelslike_row);
        mWindDirRow = view.findViewById(R.id.winddir_row);
        mPressureRow = view.findViewById(R.id.pressure_row);
        mChanceOfRainRow = view.findViewById(R.id.chanceofrain_row);

        if(this.getArguments() != null){
            date = this.getArguments().getString("date");
            cityName = this.getArguments().getString("cityName");
            iconURL = this.getArguments().getString("iconURL");
            description = this.getArguments().getString("description");
            temperature = this.getArguments().getString("temp");
            feelsLike = this.getArguments().getString("feelsLike");
            windSpeed = this.getArguments().getString("windSpeed");
            windDir = this.getArguments().getString("windDir");
            humidity = this.getArguments().getString("humidity");
            pressure = this.getArguments().getString("pressure");
            chanceOfRain = this.getArguments().getString("chanceOfRain");
            position = this.getArguments().getInt("position");
        }

        mDate.setText(date.substring(0, date.lastIndexOf("-")+3));
        mCityName.setText(cityName);
        Picasso.with(getContext()).load("http:" + iconURL).into(icon);
        mDescription.setText(description);
        mTemperature.setText(temperature);
        mWindSpeed.setText(windSpeed);
        mHumidity.setText(humidity);

        if(position == 2){
            mFeelsLike.setText(feelsLike);
            mWindDir.setText(windDir);
            mPressure.setText(pressure);
            mChanceOfRainRow.setVisibility(View.GONE);
        }

        if(position == 3 || position == 4){
            mChanceOfRain.setText(chanceOfRain);
            mFeelsLikeRow.setVisibility(View.GONE);
            mWindDirRow.setVisibility(View.GONE);
            mPressureRow.setVisibility(View.GONE);
        }

        return view;
    }
}
