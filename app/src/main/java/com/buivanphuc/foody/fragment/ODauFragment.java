package com.buivanphuc.foody.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.controller.ODauController;

public class ODauFragment extends Fragment {
    private RecyclerView recyclerODau;
    private ODauController oDauController;
    private ProgressBar progressBarODau;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragent_odau, container, false);

        recyclerODau = view.findViewById(R.id.recyclerODau);
        progressBarODau = view.findViewById(R.id.progressBarODau);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        oDauController = new ODauController(getContext());
        oDauController.getDanhSachQuanAnController(recyclerODau,progressBarODau);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
