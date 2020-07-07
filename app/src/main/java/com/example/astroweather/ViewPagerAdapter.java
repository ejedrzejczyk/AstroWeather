package com.example.astroweather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.astrocalculator.AstroCalculator;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private AstroCalculator astroCalculator;
    private JSONObject totalWeather;
    private int unit;

    public ViewPagerAdapter(@NonNull FragmentManager fm, AstroCalculator astroCalculator, JSONObject totalWeather, int unit) {
        super(fm);
        this.astroCalculator = astroCalculator;
        this.totalWeather = totalWeather;
        this.unit = unit;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = SunFragment.newInstance(astroCalculator);
                break;
            case 1:
                fragment = MoonFragment.newInstance(astroCalculator);
                break;
            case 2:
                try {
                    fragment = WeatherFragment.newInstance(totalWeather, unit, 2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    fragment = WeatherFragment.newInstance(totalWeather, unit, 3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    fragment = WeatherFragment.newInstance(totalWeather, unit, 4);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "SUN";
                break;
            case 1:
                title = "MOON";
                break;
            case 2:
                title = "TODAY";
                break;
            case 3:
                title = "TOMORROW";
                break;
            case 4:
                title = "NEXT DAY";
                break;
        }
        return title;
    }
}
