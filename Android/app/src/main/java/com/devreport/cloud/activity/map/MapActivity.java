package com.devreport.cloud.activity.map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.cloud.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private static final String TAG = "MapActivity";

    private SlidingUpPanelLayout slidingLayout;

    private GoogleMap googleMap;

    private TextView nameTextView, latLngTextView;
    private Button setButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.MapFragment);
        mapFragment.getMapAsync(this);

        slidingLayout = findViewById(R.id.SlidingLayout);

        nameTextView = findViewById(R.id.NameTextView);
        latLngTextView = findViewById(R.id.LatLngTextView);

        setButton = findViewById(R.id.setButton);

//        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            // 맵 스타일 지정
            this.googleMap = googleMap;

            this.googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

            // 마커 표시하기
            InputStream inputStream = getAssets().open("map_city.json");

            int fileSize = inputStream.available();

            byte[] buffer = new byte[fileSize];
            inputStream.read(buffer);
            inputStream.close();

            JSONArray cityArray = new JSONArray(new String(buffer, "UTF-8"));

            for (int i = 0; i < cityArray.length(); i++) {
                JSONObject city = cityArray.getJSONObject(i);
                JSONObject cityCoord = city.getJSONObject("coord");

                MarkerOptions markerOptions = new MarkerOptions();
                MapMarkerInfo mapMarkerInfo = new MapMarkerInfo(city.getInt("id"),
                                                                city.getString("name"),
                                                                cityCoord.getDouble("lat"),
                                                                cityCoord.getDouble("lon"));

                markerOptions.position(new LatLng(mapMarkerInfo.getLat(), mapMarkerInfo.getLon()));

                Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.map_marker)).getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, 40, 40, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                this.googleMap.addMarker(markerOptions).setTag(mapMarkerInfo);
            }

            googleMap.setOnMarkerClickListener(this);
        } catch (IOException exception) {

        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        MapMarkerInfo mapMarkerInfo = (MapMarkerInfo) marker.getTag();

        // drawer 정보 바꾸기
        nameTextView.setText(mapMarkerInfo.getName());
        latLngTextView.setText("Lat " + String.format("%.2f", mapMarkerInfo.getLat()) + "\t" + "Lon " + String.format("%.2f", mapMarkerInfo.getLon()));

        setButton.setOnClickListener(view -> {
            Intent intent = new Intent();

            intent.putExtra("id", mapMarkerInfo.getId());
            intent.putExtra("name", mapMarkerInfo.getName());

            setResult(RESULT_OK, intent);
            finish();
        });

        // 카메라 줌 기능
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(mapMarkerInfo.getLat(), mapMarkerInfo.getLon()))
                .build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.animateCamera(cameraUpdate, 500, null);

//        Log.d(TAG, slidingLayout.getPanelState() + "");
//        SlidingUpPanelLayout.PanelState.


        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        return true;
    }
}
