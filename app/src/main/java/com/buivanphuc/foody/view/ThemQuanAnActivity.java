package com.buivanphuc.foody.view;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.model.ThucDonModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThemQuanAnActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button btnGioMoCua, btnGioDongCua;
    Dialog dialogTimePicker;
    TextView txtGioMoCua, txtGioDongCua;
    Spinner spinnerKhuVuc, spinnerThucDon;
    List<ThucDonModel> thucDonModelList;
    List<String> khuVucList, thucDonList;
    ArrayAdapter<String> adapterKhuVuc, adapterThucDon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themquanan);
        btnGioDongCua = findViewById(R.id.btnGioDongCua);
        btnGioMoCua = findViewById(R.id.btnGioMoCua);
        txtGioDongCua = findViewById(R.id.txtGioDongCua);
        txtGioMoCua = findViewById(R.id.txtGioMoCua);
        spinnerKhuVuc = findViewById(R.id.spinnerKhuVuc);
        spinnerThucDon = findViewById(R.id.spinnerThucDon);

        btnGioMoCua.setOnClickListener(this);
        btnGioDongCua.setOnClickListener(this);
        spinnerThucDon.setOnItemSelectedListener(this);
        spinnerKhuVuc.setOnItemSelectedListener(this);

        thucDonModelList = new ArrayList<>();
        khuVucList = new ArrayList<>();
        thucDonList = new ArrayList<>();
        layDanhSachKhuVuc();
        layDanhSachThucDon();


        adapterKhuVuc = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, khuVucList);
        spinnerKhuVuc.setAdapter(adapterKhuVuc);
        adapterKhuVuc.notifyDataSetChanged();

        adapterThucDon = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, thucDonList);
        spinnerThucDon.setAdapter(adapterThucDon);
        adapterThucDon.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGioDongCua:
                dateTimePickerDialog(txtGioDongCua);
                break;
            case R.id.btnGioMoCua:
                dateTimePickerDialog(txtGioMoCua);
                break;
        }
    }

    String gio;

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void dateTimePickerDialog(final TextView textView) {

        dialogTimePicker = new Dialog(this);
        dialogTimePicker.setContentView(R.layout.dialog_timepicker);
        final TimePicker timePicker = dialogTimePicker.findViewById(R.id.timePicker);
        TextView txtHuy = dialogTimePicker.findViewById(R.id.txtThoat);
        TextView txtOK = dialogTimePicker.findViewById(R.id.txtOK);

        txtHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTimePicker.cancel();
            }
        });
        txtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gio = timePicker.getHour() + ":" + timePicker.getMinute();
                textView.setText(gio);
                textView.setVisibility(View.VISIBLE);
                dialogTimePicker.cancel();
            }
        });
        dialogTimePicker.show();

    }

    private void layDanhSachThucDon() {
        FirebaseDatabase.getInstance().getReference().child("thucdons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ThucDonModel thucDonModel = new ThucDonModel();
                    String key = snapshot.getKey();
                    String value = snapshot.getValue(String.class);

                    thucDonModel.setTenthucdon(value);
                    thucDonModel.setMathucdon(key);
                    thucDonModelList.add(thucDonModel);
                    thucDonList.add(value);
                }
                adapterThucDon.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void layDanhSachKhuVuc() {
        FirebaseDatabase.getInstance().getReference().child("khuvucs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tenKhuVuc = snapshot.getKey();
                    khuVucList.add(tenKhuVuc);
                    adapterKhuVuc.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerKhuVuc:

                break;
            case R.id.spinnerThucDon:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
