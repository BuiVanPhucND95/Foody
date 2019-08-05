package com.buivanphuc.foody.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.controller.GoogleMapController;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    MapFragment mapFragment;
    double latiude;
    double longtiude;
    String tenQuanAn;
    Location viTriHienTai;
    private SharedPreferences sharedPreferences;
    GoogleMapController controller;
    String duongdan="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        controller = new GoogleMapController();

        latiude = getIntent().getDoubleExtra("latitude", 0);
        longtiude = getIntent().getDoubleExtra("longitude", 0);
        tenQuanAn = getIntent().getStringExtra("TenQuanAn");


        sharedPreferences = getSharedPreferences("TOADO", Context.MODE_PRIVATE);

        viTriHienTai = new Location("");
        viTriHienTai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        viTriHienTai.setLongitude(Double.parseDouble(sharedPreferences.getString("longtitude","0")));

        duongdan = "https://maps.googleapis.com/maps/api/directions/json?origin=" + viTriHienTai.getLatitude() + "," + viTriHienTai.getLongitude() + "&destination=" +latiude+","
                + longtiude + "&language=vi&key=AIzaSyCSNQCX6UYnoiq-BSoaHRdQvmPovWRQeSY";

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.clear();

        LatLng latLng = new LatLng(viTriHienTai.getLatitude(), viTriHienTai.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(tenQuanAn);
        googleMap.addMarker(markerOptions);


        LatLng vtQuanAn = new LatLng(latiude, longtiude);

        MarkerOptions markerViTriQuanAn= new MarkerOptions();
        markerViTriQuanAn.position(vtQuanAn);
        markerViTriQuanAn.title(tenQuanAn);
        googleMap.addMarker(markerViTriQuanAn);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);

        controller.hienThiDanDuongToiQuanAn(googleMap,duongdan);
    }
}
