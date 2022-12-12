package com.devreport.cloud.activity.main.fragment.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.cloud.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {
    private Context context;

    private BluetoothDevice[] bluetoothDevices;

    private int page;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;

        public TextView nameTextView;
        public TextView stateTextView;

        public ViewHolder(View view) {
            super(view);

            linearLayout = view.findViewById(R.id.LinearLayout);
            nameTextView = view.findViewById(R.id.nameTextView);
            stateTextView = view.findViewById(R.id.stateTextView);
        }
    }

    public RecyclerViewAdapter(Context context, BluetoothDevice[] bluetoothDevices, int page) {
        this.context = context;
        this.bluetoothDevices = bluetoothDevices;
        this.page = page;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_bluetooth, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(bluetoothDevices[4 * page + position].getName());
        holder.stateTextView.setText("Not Connected");

        holder.linearLayout.setOnClickListener(view -> {
            BluetoothDialog bluetoothDialog = new BluetoothDialog(context, bluetoothDevices[4 * page + position].getName(), bluetoothDevices[4 * page + position].getAddress());
            bluetoothDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        for (int i = 0; i < 4; i++) {
            if (4 * page + i >= bluetoothDevices.length)
                return i;
        }

        return 4;
    }
}
