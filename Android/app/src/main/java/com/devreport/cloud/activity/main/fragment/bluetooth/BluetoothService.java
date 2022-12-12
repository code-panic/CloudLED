package com.devreport.cloud.activity.main.fragment.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothService {
    private static final String TAG = "BluetoothService";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");     //임의의 숫자라도 괜찮다

    private static String macAddress;

    private static BluetoothAdapter bluetoothAdapter;
    private static BluetoothDevice bluetoothDevice;
    private static BluetoothSocket bluetoothSocket;
    private static OutputStream outputStream;


    public static boolean Connect(String macAddress, Context context) {
        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothService.macAddress = macAddress;
            bluetoothDevice = bluetoothAdapter.getRemoteDevice(macAddress);
            bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);

            bluetoothSocket.connect();

            outputStream = bluetoothSocket.getOutputStream();

            Toast.makeText(context, "연결 성공", Toast.LENGTH_SHORT).show();

            return true;
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            return false;
        }
    }

    // #255255255C
    public static boolean writeColorData(String message) {
        try {
            if (outputStream != null)
                outputStream.write(message.getBytes());

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean writeColorData(String colorString, char mode) {
        try {
            if (outputStream != null) {
                int color = Color.parseColor(colorString);

                int red = (color >> 16) & 0xFF;
                int green = (color >> 8) & 0xFF;
                int blue = color & 0xFF;

                String message = "#" + String.format("%03d", red)
                        + String.format("%03d", green)
                        + String.format("%03d", blue) + mode;

                outputStream.write(message.getBytes());
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean writeColorData(int color, char mode) {
        try {
            if (outputStream != null) {
                int red = (color >> 16) & 0xFF;
                int green = (color >> 8) & 0xFF;
                int blue = color & 0xFF;

                String message = "#" + String.format("%03d", red)
                        + String.format("%03d", green)
                        + String.format("%03d", blue) + mode;

                outputStream.write(message.getBytes());
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean writeColorData(int red, int green, int blue, char mode) {
        try {
            if (outputStream != null) {
                String message = "#" + String.format("%03d", red)
                        + String.format("%03d", green)
                        + String.format("%03d", blue) + mode;

                outputStream.write(message.getBytes());
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
