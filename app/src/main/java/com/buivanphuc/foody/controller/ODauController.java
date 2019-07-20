package com.buivanphuc.foody.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.adapter.RecyclerODauAdapter;
import com.buivanphuc.foody.controller.Interfaces.IODauInterface;
import com.buivanphuc.foody.model.QuanAnModel;

import java.util.ArrayList;
import java.util.List;

public class ODauController {

    private Context context;
    private QuanAnModel quanAnModel;
    private RecyclerODauAdapter adapter;

    public ODauController(Context context) {
        this.context = context;
        quanAnModel = new QuanAnModel();
    }

    public void getDanhSachQuanAnController(RecyclerView recyclerViewODau, final ProgressBar progressBar) {
        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewODau.setLayoutManager(layoutManager);
        adapter = new RecyclerODauAdapter(context, quanAnModelList, R.layout.item_recycler_odau);
        recyclerViewODau.setAdapter(adapter);

        IODauInterface ioDauInterface = new IODauInterface() {
            @Override
            public void getDanhSachQuanAnModel(QuanAnModel quanAnModel) {

                quanAnModelList.add(quanAnModel);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }
        };
        quanAnModel.getDanhSachQuanAn(ioDauInterface);

    }
}
