package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {
    public MyPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new account();
        }else if (position==1){
            return new calendar();
        }else{
            return new report_forms();
        }
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // 返回页面标题
        switch (position) {
            case 0:
                return "记账";
            case 1:
                return "日历";
            case 2:
                return "报表";
            default:
                return null;
        }
    }
}
