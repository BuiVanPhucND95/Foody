package com.buivanphuc.foody.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.buivanphuc.foody.fragment.AnGiFragment;
import com.buivanphuc.foody.fragment.ODauFragment;

public class ViewPagerHomeAdapter extends FragmentStatePagerAdapter {

    public ViewPagerHomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ODauFragment();
            case 1:
                return new AnGiFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
