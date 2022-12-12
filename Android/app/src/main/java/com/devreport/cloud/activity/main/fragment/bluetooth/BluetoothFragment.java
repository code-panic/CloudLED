package com.devreport.cloud.activity.main.fragment.bluetooth;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.cloud.R;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class BluetoothFragment extends Fragment {
    private static final String TAG = "BluetoothFragment";

    private static BluetoothFragment instance;

    private static final int REQUEST_ENABLE_BT = 101;

    private View mView;

    private GradientDrawable bgGradient;

    public static BluetoothFragment getInstance() {
        if(instance == null) {
            instance = new BluetoothFragment();
        }

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 뷰 관련 객체 선언
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.fragment_bluetooth, null);

        ViewPager2 pairingViewPager = mView.findViewById(R.id.pairingViewPager);
        DotsIndicator indicator = mView.findViewById(R.id.DotsIndicator);

        bgGradient = (GradientDrawable) mView.findViewById(R.id.BluetoothLayout).getBackground();

        // 블루투스 기능 활성화
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // 페어링된 기기확인
        BluetoothDevice[] bluetoothDevices = bluetoothAdapter.getBondedDevices().toArray(new BluetoothDevice[0]);

        ViewPagerAdapter pagerAdapter= new ViewPagerAdapter(this, bluetoothDevices);
        pairingViewPager.setAdapter(pagerAdapter);

        indicator.setViewPager2(pairingViewPager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mView;
    }


    public GradientDrawable getBgGradient() {
        return bgGradient;
    }
}
