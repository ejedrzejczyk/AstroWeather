package com.example.astroweather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;

public class SunFragment extends Fragment {

    private TextView mSunriseTime;
    private TextView mSunriseAzimuth;
    private TextView mSunsetTime;
    private TextView mSunsetAzimuth;
    private TextView mTwilightMorning;
    private TextView mTwilightEvening;

    private String sunriseTime;
    private String sunriseAzimuth;
    private String sunsetTime;
    private String sunsetAzimuth;
    private String twilightMorning;
    private String twilightEvening;

    public SunFragment() {
        // Required empty public constructor
    }

    public static SunFragment newInstance(AstroCalculator astroCalculator){
        DataMenager dataMenager = new DataMenager();
        SunFragment sunFragment = new SunFragment();
        Bundle bundle = new Bundle();
        bundle.putString("sunriseTime", dataMenager.timeToString(astroCalculator.getSunInfo().getSunrise()));
        bundle.putString("sunriseAzimuth", Double.toString(astroCalculator.getSunInfo().getAzimuthRise()).substring(0, Double.toString(astroCalculator.getSunInfo().getAzimuthRise()).lastIndexOf(".") + 3));
        bundle.putString("sunsetTime", dataMenager.timeToString(astroCalculator.getSunInfo().getSunset()));
        bundle.putString("sunsetAzimuth", Double.toString(astroCalculator.getSunInfo().getAzimuthSet()).substring(0, Double.toString(astroCalculator.getSunInfo().getAzimuthSet()).lastIndexOf(".") + 3));
        bundle.putString("twilightMorning", dataMenager.timeToString(astroCalculator.getSunInfo().getTwilightMorning()));
        bundle.putString("twilightEvening", dataMenager.timeToString(astroCalculator.getSunInfo().getTwilightEvening()));
        sunFragment.setArguments(bundle);

        return sunFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sun, container, false);

        mSunriseTime = view.findViewById(R.id.sunrise_time);
        mSunriseAzimuth = view.findViewById(R.id.sunrise_azimuth);
        mSunsetTime = view.findViewById(R.id.sunset_time);
        mSunsetAzimuth = view.findViewById(R.id.sunset_azimuth);
        mTwilightMorning = view.findViewById(R.id.twilight_morning);
        mTwilightEvening = view.findViewById(R.id.twilight_evening);

        if(this.getArguments() != null){
            sunriseTime = this.getArguments().getString("sunriseTime");
            sunriseAzimuth = this.getArguments().getString("sunriseAzimuth");
            sunsetTime = this.getArguments().getString("sunsetTime");
            sunsetAzimuth = this.getArguments().getString("sunsetAzimuth");
            twilightMorning = this.getArguments().getString("twilightMorning");
            twilightEvening = this.getArguments().getString("twilightEvening");
        }

        mSunriseTime.setText(sunriseTime);
        mSunriseAzimuth.setText(sunriseAzimuth);
        mSunsetTime.setText(sunsetTime);
        mSunsetAzimuth.setText(sunsetAzimuth);
        mTwilightMorning.setText(twilightMorning);
        mTwilightEvening.setText(twilightEvening);

        return view;
    }
}
