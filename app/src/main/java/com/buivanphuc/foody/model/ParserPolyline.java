package com.buivanphuc.foody.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParserPolyline {

    public ParserPolyline(){

    }

    public List<LatLng> layDanhSachToaDo(String dataJson) {
        List<LatLng> latLngs = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(dataJson);
            //JSONObject overviewPolyline = jsonObject.getJSONObject("overview_polyline");
            JSONArray routes = jsonObject.getJSONArray("routes");
            for (int i = 0; i < routes.length(); i++) {
                JSONObject jsonObjectArray = routes.getJSONObject(i);
                JSONObject overviewPolyline = jsonObjectArray.getJSONObject("overview_polyline");

                String point = overviewPolyline.getString("points");
                Log.d("KiemTra",point);

                latLngs = PolyUtil.decode(point);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return latLngs;
    }
}
