package com.devreport.cloud.activity.main.fragment.weather;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.devreport.cloud.activity.main.fragment.ColorPickerFragment;
import com.devreport.cloud.activity.main.fragment.bluetooth.BluetoothFragment;
import com.devreport.cloud.activity.main.fragment.bluetooth.BluetoothService;
import com.devreport.cloud.activity.map.MapActivity;
import com.devreport.cloud.service.AnimateService;
import com.firebase.cloud.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFragment extends Fragment {
    private static final String TAG = "WeatherFragment";

    private static WeatherFragment instance;

    private static final String APPID = "dacdd3ff23cc19ea41e9107e3006a07e";
    private static final int MAP_REQUEST_CODE = 101;

    private View view;

    private TextView cityNameTextView;

    private LinearLayout animationLayout;

    private ImageView cloudImageView;
    private TextView cloudTextView, cloudSubTextView;

    private TextView  tempTextView, humidityTextView, pressureTextView, windTextView;

    private GradientDrawable bgGradient;

    private ImageView dessert1ImageView, dessert2ImageView, dessert3ImageView;

    private ValueAnimator[] animators = new ValueAnimator[10];

    public static WeatherFragment getInstance() {
        if(instance == null) {
            instance = new WeatherFragment();
        }

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ??? ?????? ?????? ??????
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.fragment_weather, null);

        LinearLayout mapButton = view.findViewById(R.id.MapButton);

        cityNameTextView = view.findViewById(R.id.CityNameTextView);

        animationLayout = view.findViewById(R.id.AnimationLayout);

        cloudImageView = view.findViewById(R.id.CloudImageView);
        cloudTextView = view.findViewById(R.id.CloudTextView);
        cloudSubTextView = view.findViewById(R.id.CloudSubTextView);

        tempTextView = view.findViewById(R.id.TempTextView);
        humidityTextView = view.findViewById(R.id.HumidityTextView);
        pressureTextView = view.findViewById(R.id.PressureTextView);
        windTextView = view.findViewById(R.id.WindTextView);

        bgGradient = (GradientDrawable) view.findViewById(R.id.WeatherLayout).getBackground();

        dessert1ImageView = view.findViewById(R.id.Dessert1ImageView);
        dessert2ImageView = view.findViewById(R.id.Dessert2ImageView);
        dessert3ImageView = view.findViewById(R.id.Dessert3ImageView);

        // ??? ?????? ??????????????? ??????
        mapButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MapActivity.class);
            startActivityForResult(intent, MAP_REQUEST_CODE);
        });

        // ????????? ?????? ???????????? ?????? ????????? ??????????????? ??????.
        dessert1ImageView.setTag(Color.parseColor("#884500"));
        dessert2ImageView.setTag(Color.parseColor("#B97738"));
        dessert3ImageView.setTag(Color.parseColor("#CC915C"));
        Log.d(TAG, "origin : " + Color.parseColor("#884500"));

        // ?????? ????????? ??????
        setCurrentWeather(1835847);

        // ??????????????? ?????? ?????????
        for (int i = 0; i < animators.length; i++) {
            animators[i] = new ValueAnimator();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view;
    }

    // ??? ???????????? ???????????? ???
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == MAP_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                cityNameTextView.setText(intent.getExtras().getString("name"));

                setCurrentWeather(intent.getExtras().getInt("id"));
            }
        }
    }

    // API ?????? ???????????? ????????? ???????????? ??????
    private void setCurrentWeather (int id) {
        WeatherInterface weatherInterface = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherInterface.class);

        Call<WeatherAllData> call =  weatherInterface.getCurrentWeather(id, APPID);

        // ?????? ?????????
        call.enqueue(new Callback<WeatherAllData>() {
            @Override
            public void onResponse(Call<WeatherAllData> call, Response<WeatherAllData> response) {
                WeatherAllData result = response.body();

                // ??????????????? ???????????? ??????
                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.weather_anim);
                set.setTarget(animationLayout);
                set.start();

                // ?????? ????????? ??? ????????? ??????
                if (result.getCloudData().getDensity() >= 60) {
                    cloudImageView.setImageResource(R.drawable.icon_cloud_cloudy);

                    cloudTextView.setText("Cloudy Day");
                    cloudSubTextView.setText("???????????? ?????? ????????????!");
                } else if (result.getCloudData().getDensity() >= 30) {
                    cloudImageView.setImageResource(R.drawable.icon_cloud_calm);

                    cloudTextView.setText("Calm Day");
                    cloudSubTextView.setText("?????????????????? ????????????!");
                } else {
                    cloudImageView.setImageResource(R.drawable.icon_cloud_clear);

                    cloudTextView.setText("Clear Day");
                    cloudSubTextView.setText("??? ?????? ?????? ????????????!");
                }

                // ???????????? ?????????
                int temp = (int)(result.getDetailData().getTemp() - 273);

                tempTextView.setText(temp + " ??C");
                humidityTextView.setText(result.getDetailData().getHumidity() + " %");
                pressureTextView.setText(result.getDetailData().getPressure() + " inHg");
                windTextView.setText(result.getWindData().getSpeed() + " mph");

                // ????????? ?????? ??????????????? ?????? LED??? ????????? ?????????
                if (temp >= 30) {
                    String[] colorArray = new String[]{"#FF9A7C", "#F58563", "#F58563", "#792020", "#4A130A", "#000000", "#4A130A", "#76271D", "#943535"};
                    animateByWeatherData(colorArray);

                    BluetoothService.writeColorData("#FF0000",'W');
                } else if (temp >= 20) {
                    String[] colorArray = new String[]{"#EBB589", "#F0B07C", "#F0B07C", "#DE733C", "#884500", "#000000", "#884500", "#B97738", "#CC915C"};
                    animateByWeatherData(colorArray);

                    BluetoothService.writeColorData("#FF5500",'W');
                } else if (temp >= 10) {
                    String[] colorArray = new String[]{"#92DB71", "#81CF5E", "#81CF5E", "#26803C", "#0A4A25", "#000000", "#0A4A25", "#1D7624", "#569435"};
                    animateByWeatherData(colorArray);

                    BluetoothService.writeColorData("#00FF22",'W');
                } else if (temp >= 0) {
                    String[] colorArray = new String[]{"#98D0FF", "#63B2F5", "#63B2F5", "#322079", "#0A194A", "#000000", "#0A194A", "#1D2E76", "#354694"};
                    animateByWeatherData(colorArray);

                    BluetoothService.writeColorData("#0055FF",'W');
                } else {
                    String[] colorArray = new String[]{"#CB92FF", "#B063F5", "#B063F5", "#322079", "#401388", "#000000", "#401388", "#5F24B2", "#7C3493"};
                    animateByWeatherData(colorArray);

                    BluetoothService.writeColorData("#FF00FF",'W');
                }
            }

            @Override
            public void onFailure(Call<WeatherAllData> call, Throwable t) {
                Log.d(TAG, "??????!" + t.getMessage());
            }
        });
    }

    // API ?????? ?????? ???????????? ???????????? CallBack ??? ??????
    public void setCurrentWeather (int id, Callback callback) {
        WeatherInterface weatherInterface = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherInterface.class);

        Call<WeatherAllData> call =  weatherInterface.getCurrentWeather(id, APPID);
        call.enqueue(callback);
    }

    /*
    * 0. ???????????? ?????? Top
    * 1. ???????????? ?????? Bottom
    * 2. ?????? ?????? Top
    * 3. ?????? ?????? Bottom
    * 4. ???????????? ?????? Top
    * 5. ???????????? ?????? Bottom
    * 6. desert1Image
    * 7. desert2Image
    * 8. desert3Image
    * */
    private void animateByWeatherData (String[] colorArray) {
        AnimateService.changeGradientColor(BluetoothFragment.getInstance().getBgGradient(), colorArray[0], colorArray[1], 500);
        AnimateService.changeGradientColor(bgGradient, colorArray[2], colorArray[3], 1000);

        if (ColorPickerFragment.getInstance().getBgGradient() == null) {
            Bundle bundle = new Bundle();

            bundle.putString("topTo", colorArray[4]);
            bundle.putString("bottomTo", colorArray[5]);

            ColorPickerFragment.getInstance().setArguments(bundle);
        } else {
            AnimateService.changeGradientColor(ColorPickerFragment.getInstance().getBgGradient(), colorArray[4], colorArray[5], 1000);
        }

        AnimateService.changeImageColor(dessert1ImageView, colorArray[6], 1000);
        AnimateService.changeImageColor(dessert2ImageView, colorArray[7], 1000);
        AnimateService.changeImageColor(dessert3ImageView, colorArray[8], 1000);
    }
}
