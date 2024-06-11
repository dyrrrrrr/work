package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class viewpager extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
        MyPageAdapter pageAdapter=new MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 当Tab被选中时，获取Tab的位置
                int position = tab.getPosition();
                // 更新ViewPager到相应位置
                viewPager.setCurrentItem(position);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 可选：当Tab未被选中时的处理逻辑
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 可选：当Tab被重新选中时的处理逻辑
            }
        });

        // 设置Tab的图标
        setTabIcons(tabLayout);

        int id = getIntent().getIntExtra("id", 0);
        if (id == 1) {
            viewPager.setCurrentItem(1);
        }//跳转回日历

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogin) {
            // 跳转到登录页面

            Intent intent = new Intent(this, login.class); // 确保Login类存在
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTabIcons(TabLayout tabLayout) {
        //添加图标
        TabLayout.Tab tab1 = tabLayout.getTabAt(0);
        if (tab1 != null) {
            tab1.setIcon(android.R.drawable.ic_menu_zoom);
        }
        TabLayout.Tab tab2 = tabLayout.getTabAt(1);
        if (tab2 != null) {
            tab2.setIcon(android.R.drawable.ic_menu_month);
        }
        TabLayout.Tab tab3 = tabLayout.getTabAt(2);
        if (tab3 != null) {
            tab3.setIcon(android.R.drawable.ic_menu_sort_by_size);
        }
    }



}