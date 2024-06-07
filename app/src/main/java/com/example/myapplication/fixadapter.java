package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class fixadapter extends FragmentPagerAdapter {
    private int mId; // 用于保存ID

    public fixadapter(@NonNull FragmentManager fm, int id) {
        super(fm);
        this.mId = id;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            shouru1 shouru1 = new shouru1();
            Bundle args = new Bundle();
            args.putInt("ID", mId);
            shouru1.setArguments(args);
            return shouru1;
        }else {
            zhichu1 zhichu1 = new zhichu1();
            Bundle args = new Bundle();
            args.putInt("ID", mId);
            zhichu1.setArguments(args);
            return zhichu1;
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
