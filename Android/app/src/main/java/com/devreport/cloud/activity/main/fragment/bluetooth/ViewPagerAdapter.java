package com.devreport.cloud.activity.main.fragment.bluetooth;

import android.bluetooth.BluetoothDevice;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private BluetoothDevice[] bluetoothDevices;

    public ViewPagerAdapter(Fragment fragment, BluetoothDevice[] bluetoothDevices) {
        super(fragment);

        this.bluetoothDevices = bluetoothDevices;
    }

    @Override
    public Fragment createFragment(int pos) {
        return new ViewPagerFragment(bluetoothDevices, pos);
    }

    @Override
    public int getItemCount() {
        return bluetoothDevices.length / 4 + 1;
    }
}
