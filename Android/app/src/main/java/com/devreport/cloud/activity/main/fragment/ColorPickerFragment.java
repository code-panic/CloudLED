package com.devreport.cloud.activity.main.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.devreport.cloud.activity.main.fragment.bluetooth.BluetoothService;
import com.firebase.cloud.R;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SVBar;

public class ColorPickerFragment extends Fragment {
    public static final String TAG = "ColorPickerFragment";

    private static ColorPickerFragment instance;

    private View view;

    private GradientDrawable bgGradient;

    public static ColorPickerFragment getInstance() {
        if(instance == null) {
            instance = new ColorPickerFragment();
        }

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.fragment_colorpicker, null);

        bgGradient = (GradientDrawable) view.findViewById(R.id.ColorPickerLayout).getBackground();

        final ColorPicker colorPicker = view.findViewById(R.id.ColorPicker);
        final TextView RGBTextView = view.findViewById(R.id.RGBTextView);
        final ImageView iconImageView = view.findViewById(R.id.IconImageView);
        final SVBar svBar = view.findViewById(R.id.SVBar);

        // 배경 그라디언트 설정
        if (getArguments() != null) {
            int[] colorArray = {Color.parseColor(getArguments().getString("topTo")), Color.parseColor(getArguments().getString("bottomTo"))};
            bgGradient.setColors(colorArray);
        }

        // 컬러피커 세팅
        colorPicker.addSVBar(svBar);

        colorPicker.setOnColorChangedListener(color -> {
            int red = (color >> 16) & 0xff;
            int green = (color >> 8) & 0xff;
            int blue = color & 0xff;

            String message = "#" + String.format("%02X", red)
                            + String.format("%02X", green)
                            + String.format("%02X", blue);

            RGBTextView.setText(message);
            iconImageView.setColorFilter(Color.parseColor(message), PorterDuff.Mode.SRC_IN);

            BluetoothService.writeColorData(red, green, blue, 'C');

            Log.d(TAG, message + "");
        });

//        colorPicker.setOnColorSelectedListener(new ColorPicker.OnColorSelectedListener() {
//            @Override
//            public void onColorSelected(int color) {
//                int red = (color >> 16) & 0xff;
//                int green = (color >> 8) & 0xff;
//                int blue = color & 0xff;
//
//                String message = "#" + String.format("%02X", red)
//                            + String.format("%02X", green)
//                            + String.format("%02X", blue);
//
//                Log.d(TAG, message);
//
//                RGBTextView.setText(message);
//                iconImageView.setColorFilter(Color.parseColor(message), PorterDuff.Mode.SRC_IN);
//
//                BluetoothService.writeColorData(red, green, blue, 'C');
//            }
//        });



        int red = (colorPicker.getColor() >> 16) & 0xff;
        int blue = (colorPicker.getColor() >> 8) & 0xff;
        int green = colorPicker.getColor() & 0xff;

        RGBTextView.setText("#" + red + blue + green);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view;
    }

    public GradientDrawable getBgGradient() {
        return bgGradient;
    }
}
