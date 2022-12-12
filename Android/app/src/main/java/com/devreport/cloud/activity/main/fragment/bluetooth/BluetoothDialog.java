package com.devreport.cloud.activity.main.fragment.bluetooth;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.cloud.R;

public class BluetoothDialog extends Dialog {
    private String deviceName;
    private String macAddress;

    public BluetoothDialog(Context context, String deviceName, String macAddress) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.deviceName = deviceName;
        this.macAddress = macAddress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.3f;
        getWindow().setAttributes(params);

        setContentView(R.layout.dialog_bluetooth);

        TextView descTextView = findViewById(R.id.descTextView);

        Button cancelButton = findViewById(R.id.CancelButton);
        Button confirmButton = findViewById(R.id.ConfirmButton);

        descTextView.setText("'" + deviceName +  "' 와\n블루투스를 연결할까요?");

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                BluetoothService.Connect(macAddress, getContext());
            }
        });
    }
}
