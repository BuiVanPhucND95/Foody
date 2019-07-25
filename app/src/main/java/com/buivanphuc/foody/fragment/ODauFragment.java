package com.buivanphuc.foody.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.controller.ODauController;

public class ODauFragment extends Fragment {
    private RecyclerView recyclerODau;
    private ODauController oDauController;
    private ProgressBar progressBarODau;
    private SharedPreferences sharedPreferences;
    private NestedScrollView nestedScrollViewODau;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragent_odau, container, false);
        nestedScrollViewODau = view.findViewById(R.id.nestedScrollViewODau);
        recyclerODau = view.findViewById(R.id.recyclerODau);
        progressBarODau = view.findViewById(R.id.progressBarODau);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = getContext().getSharedPreferences("TOADO", Context.MODE_PRIVATE);

        Location viTriHienTai = new Location("");
        viTriHienTai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        viTriHienTai.setLongitude(Double.parseDouble(sharedPreferences.getString("longtitude","0")));

        oDauController = new ODauController(getContext());
        oDauController.getDanhSachQuanAnController(recyclerODau, progressBarODau,viTriHienTai,nestedScrollViewODau);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
