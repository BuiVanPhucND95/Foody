package com.buivanphuc.foody.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.buivanphuc.foody.R;


public class SlashScreenActivity extends AppCompatActivity {
    TextView mTxtVersion;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashscreen);
        mTxtVersion = findViewById(R.id.txtVersion);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            mTxtVersion.setText(getResources().getString(R.string.phienban) + " " + packageInfo.versionName);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SlashScreenActivity.this, DangNhapActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
            }, 2000);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
