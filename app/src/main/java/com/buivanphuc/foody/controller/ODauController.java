package com.buivanphuc.foody.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.adapter.RecyclerODauAdapter;
import com.buivanphuc.foody.controller.Interfaces.IODauInterface;
import com.buivanphuc.foody.model.QuanAnModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ODauController {

    private Context context;
    private QuanAnModel quanAnModel;
    private RecyclerODauAdapter adapter;
    int itemDaCo = 10;

    public ODauController(Context context) {
        this.context = context;
        quanAnModel = new QuanAnModel();
    }

    public void getDanhSachQuanAnController(RecyclerView recyclerViewODau, final ProgressBar progressBar, final Location viTriHienTai, NestedScrollView nestedScrollViewODau) {
        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewODau.setLayoutManager(layoutManager);
        adapter = new RecyclerODauAdapter(context, quanAnModelList, R.layout.item_recycler_odau);
        recyclerViewODau.setAdapter(adapter);


        final IODauInterface ioDauInterface = new IODauInterface() {

            @Override
            public void getDanhSachQuanAnModel(final QuanAnModel quanAnModel) {
                quanAnModelList.add(quanAnModel);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

//                Log.d("KiemTra","OK");
//                final List<Bitmap> bitmapList = new ArrayList<>();
//                for (String linkHinh : quanAnModel.getHinhanhquanan()) {
//
//                    StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").
//                            child(linkHinh);
//                    long ONE_MEGABYTE = 1024 * 1024;
//
//                    storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                            bitmapList.add(bitmap);
//
//                            Log.d("KiemTra", bitmapList.size() + "--"+quanAnModel.getHinhanhquanan().size());
//
//                            if (bitmapList.size() == quanAnModel.getHinhanhquanan().size()) {
//                                quanAnModel.setBitmapList(bitmapList);
//                                quanAnModelList.add(quanAnModel);
//                                adapter.notifyDataSetChanged();
//                                progressBar.setVisibility(View.GONE);
//                            }
//
//
//                        }
//                    });
//                }


            }
        };

        // Lắng nghe sự kiện Scroll của nestedScrollViewODau
        nestedScrollViewODau.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if (scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) {
                        itemDaCo += 10;
                        quanAnModel.getDanhSachQuanAn(ioDauInterface, viTriHienTai, itemDaCo, itemDaCo - 10);

                    }
                }
            }
        });

        quanAnModel.getDanhSachQuanAn(ioDauInterface, viTriHienTai, itemDaCo, 0);
    }
}
