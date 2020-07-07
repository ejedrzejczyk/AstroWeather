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
import com.example.astroweather.DataMenager;

public class MoonFragment extends Fragment {

    private TextView mMoonriseTime;
    private TextView mMoonsetTime;
    private TextView mNextNewMoon;
    private TextView mNextFullMoon;
    private TextView mMoonIllumination;
    private TextView mMoonAge;

    private String moonriseTime;
    private String moonsetTime;
    private String nextNewMoon;
    private String nextFullMoon;
    private String moonIllumination;
    private String moonAge;
    private Handler mHandler = new Handler();

    public MoonFragment() {
        // Required empty public constructor
    }

    public static MoonFragment newInstance(AstroCalculator astroCalculator){
        DataMenager dataMenager = new DataMenager();
        MoonFragment moonFragment = new MoonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("moonriseTime", dataMenager.timeToString(astroCalculator.getMoonInfo().getMoonrise()));
        bundle.putString("moonsetTime", dataMenager.timeToString(astroCalculator.getMoonInfo().getMoonset()));
        bundle.putString("nextNewMoon", dataMenager.dateToString(astroCalculator.getMoonInfo().getNextNewMoon()));
        bundle.putString("nextFullMoon", dataMenager.dateToString(astroCalculator.getMoonInfo().getNextFullMoon()));
        bundle.putString("moonIllumination", Double.toString(astroCalculator.getMoonInfo().getIllumination() * 100.0).substring(0, Double.toString(astroCalculator.getMoonInfo().getIllumination()).lastIndexOf(".") + 1) + " %");
        bundle.putString("moonAge", Double.toString(astroCalculator.getMoonInfo().getAge()/29.53).substring(0, Double.toString(astroCalculator.getMoonInfo().getAge()).lastIndexOf("." ) - 2) + " days");
        moonFragment.setArguments(bundle);

        return moonFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_moon, container, false);

        mMoonriseTime = view.findViewById(R.id.moonrise_time);
        mMoonsetTime = view.findViewById(R.id.moonset_time);
        mNextNewMoon = view.findViewById(R.id.next_new_moon);
        mNextFullMoon = view.findViewById(R.id.next_full_moon);
        mMoonIllumination = view.findViewById(R.id.moon_illumination);
        mMoonAge = view.findViewById(R.id.moon_age);

        if(this.getArguments() != null){
            moonriseTime = this.getArguments().getString("moonriseTime");
            moonsetTime = this.getArguments().getString("moonsetTime");
            nextNewMoon = this.getArguments().getString("nextNewMoon");
            nextFullMoon = this.getArguments().getString("nextFullMoon");
            moonIllumination = this.getArguments().getString("moonIllumination");
            moonAge = this.getArguments().getString("moonAge");
        }

        mMoonriseTime.setText(moonriseTime);
        mMoonsetTime.setText(moonsetTime);
        mNextNewMoon.setText(nextNewMoon);
        mNextFullMoon.setText(nextFullMoon);
        mMoonIllumination.setText(moonIllumination);
        mMoonAge.setText(moonAge);

        return view;
    }
}
