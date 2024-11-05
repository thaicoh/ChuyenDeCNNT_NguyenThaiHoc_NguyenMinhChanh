package phdhtl.khoa63.doan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private String maPhong;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, String maPhong) {
        super(fm, behavior);
        this.maPhong = maPhong;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment =  new DatPhongNgayFragment();
                break;
            case 1:
                fragment = new DatPhongDemFragment();
                break;
            case 2:
                fragment = new DatPhongGioFragment();
                break;
            default:
                fragment = new DatPhongNgayFragment();
                break;
        }
        Bundle bundle = new Bundle();

        bundle.putString("maPhong", maPhong);
        if (fragment != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position){
            case 0:
                 title = "Phòng Ngày";
                break;
            case 1:
                 title = "Phòng Đêm";
                break;
            case 2:
                 title = "Phòng Giờ";
                 break;

        }
        return title;

        //return super.getPageTitle(position);
    }
}
