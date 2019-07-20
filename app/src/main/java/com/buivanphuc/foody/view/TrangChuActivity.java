package com.buivanphuc.foody.view;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.adapter.ViewPagerHomeAdapter;

public class TrangChuActivity extends AppCompatActivity {

    ViewPager mViewPagerHome;
    ViewPagerHomeAdapter adapter;
    RadioButton mRadioAnGi, mRadioODau;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);
        mViewPagerHome = findViewById(R.id.viewPagerTrangChu);
        mRadioAnGi = findViewById(R.id.rd_angi);
        mRadioODau = findViewById(R.id.rd_odau);

        adapter = new ViewPagerHomeAdapter(getSupportFragmentManager());
        mViewPagerHome.setAdapter(adapter);

        mViewPagerHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mRadioODau.setChecked(true);
                        break;
                    case 1:
                        mRadioAnGi.setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioAnGi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPagerHome.setCurrentItem(1);
            }
        });
        mRadioODau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPagerHome.setCurrentItem(0);
            }
        });

    }
}
