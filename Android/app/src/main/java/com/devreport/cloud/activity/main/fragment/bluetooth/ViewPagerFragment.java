package com.devreport.cloud.activity.main.fragment.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.cloud.R;

public class ViewPagerFragment extends Fragment {
    private static final String TAG = "PagerFragment";

    private BluetoothDevice[] bluetoothDevices;
    private int page;

    private View view;

    public ViewPagerFragment(BluetoothDevice[] bluetoothDevices, int page) {
        this.bluetoothDevices = bluetoothDevices;
        this.page = page;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = layoutInflater.inflate(R.layout.fragment_bluetooth_pager, null);

//        final LinearLayout linearLayout = view.findViewById(R.id.LinearLayout);
//
//        for (int index = 0; index < 4; index++) {
//            final int position = 4 * page + index;
//
//            if (position < bluetoothDevices.length) {
//                View view = layoutInflater.inflate(R.layout.holder_bluetooth, null);
//
//                TextView nameTextView = view.findViewById(R.id.nameTextView);
//                TextView addressTextView = view.findViewById(R.id.addressTextView);
//
//                nameTextView.setText(bluetoothDevices[position].getName());
//                addressTextView.setText(bluetoothDevices[position].getAddress());
//
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Log.d(TAG, position + "");
//                        Log.d(TAG, bluetoothDevices[position].getName());
//                        Log.d(TAG, bluetoothDevices[position].getAddress());
//
//                        BluetoothDialog bluetoothDialog = new BluetoothDialog(getContext(), bluetoothDevices[position].getName(), bluetoothDevices[position].getAddress());
//                        bluetoothDialog.show();
//                    }
//                });
//
//                linearLayout.addView(view);
//            }
//        }

        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), bluetoothDevices, page);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view;
    }
}
