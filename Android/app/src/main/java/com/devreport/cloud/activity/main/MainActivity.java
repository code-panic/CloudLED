package com.devreport.cloud.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.firebase.cloud.R;

public class MainActivity extends AppCompatActivity {

    public static ViewPager2 viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager = findViewById(R.id.viewPager);

        FragmentStateAdapter pagerAdapter = new PagerAdapter(this);
        viewpager.setAdapter(pagerAdapter);
    }
}
