package com.devreport.cloud.activity.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.devreport.cloud.activity.main.fragment.ColorPickerFragment;
import com.devreport.cloud.activity.main.fragment.weather.WeatherFragment;
import com.devreport.cloud.activity.main.fragment.bluetooth.BluetoothFragment;

public class PagerAdapter extends FragmentStateAdapter {
    public BluetoothFragment bluetoothFragment = BluetoothFragment.getInstance();
    public WeatherFragment weatherFragment = WeatherFragment.getInstance();
    public ColorPickerFragment colorPickerFragment = ColorPickerFragment.getInstance();

    public PagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return bluetoothFragment;
            case 1:
                return weatherFragment;
            case 2:
                return colorPickerFragment;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
