package com.buivanphuc.foody.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.buivanphuc.foody.R;

import java.util.List;

public class ViewPagerHinhBinhLuan extends PagerAdapter {

    private Context context;
    private List<Bitmap> listHinhAnh;
    public ViewPagerHinhBinhLuan(Context context,List<Bitmap > listHinhAnh){
        this.context = context;
        this.listHinhAnh = listHinhAnh;
    }
    @Override
    public int getCount() {
        return listHinhAnh.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==(LinearLayout) object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager_hinhanh,container,false);
        ImageView imageView = view.findViewById(R.id.imgFullHinhAnh);
        imageView.setImageBitmap(listHinhAnh.get(position));
        container.addView(view);

        return view;
    }
}
