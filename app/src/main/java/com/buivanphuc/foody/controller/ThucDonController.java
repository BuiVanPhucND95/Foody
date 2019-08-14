package com.buivanphuc.foody.controller;


import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.adapter.ThucDonAdapter;
import com.buivanphuc.foody.controller.Interfaces.ThucDonInterface;
import com.buivanphuc.foody.model.ThucDonModel;

import java.util.List;

public class ThucDonController {
   private ThucDonModel thucDonModel;

    public ThucDonController() {
        thucDonModel = new ThucDonModel();
    }

    public void getDanhSachThucDonQuanAn(String maQuanAn, final RecyclerView recyclerThucDon, final Context context) {
        recyclerThucDon.setLayoutManager(new LinearLayoutManager(context));

        ThucDonInterface thucDonInterface = new ThucDonInterface() {
            @Override
            public void getThucDonThanhCong(List<ThucDonModel> thucDonModelList) {
                ThucDonAdapter adapter = new ThucDonAdapter(context, R.layout.custom_layout_thucdon,thucDonModelList);
                recyclerThucDon.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        thucDonModel.getDanhSachThucDonQuanAn(maQuanAn, thucDonInterface);
    }
}
