package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class addactadapter extends FragmentPagerAdapter {
    public addactadapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new shouru();
        }else {
            return new zhichu();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    public CharSequence getPageTitle(int position) {
        // 返回页面标题
        switch (position) {
            case 0:
                return "收入";
            case 1:
                return "支出";
            default:
                return null;
        }
    }
}
