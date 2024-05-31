package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class addact extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private addactadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addact);

        viewpager = findViewById(R.id.viewpager1);
        adapter = new addactadapter(this.getSupportFragmentManager());
        viewpager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewpager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                // 必须调用以下方法来更新ViewPager
                viewpager.setCurrentItem(position);
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
    }
    public void imageexit(View v){
        Intent intent = new Intent(this, com.example.myapplication.viewpager.class);
        startActivity(intent);
    }
}