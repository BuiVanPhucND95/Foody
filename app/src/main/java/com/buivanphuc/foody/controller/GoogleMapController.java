package com.buivanphuc.foody.controller;

import android.util.Log;

import com.buivanphuc.foody.model.DowloadPolyLine;
import com.buivanphuc.foody.model.ParserPolyline;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class GoogleMapController {
    private ParserPolyline parserPolyline;
    private DowloadPolyLine dowloadPolyLine;

    public GoogleMapController() {
        parserPolyline = new ParserPolyline();
        dowloadPolyLine = new DowloadPolyLine();
    }

    public void hienThiDanDuongToiQuanAn(GoogleMap googleMap, String duongDan) {
        dowloadPolyLine.execute(duongDan);


        try {
            String dataJSOn = dowloadPolyLine.get();
            List<LatLng> latLngList = parserPolyline.layDanhSachToaDo(dataJSOn);
            PolylineOptions polylineOptions = new PolylineOptions();
            for (LatLng toaDo: latLngList){
                polylineOptions.add(toaDo);
            }

            Polyline polyline = googleMap.addPolyline(polylineOptions);


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
