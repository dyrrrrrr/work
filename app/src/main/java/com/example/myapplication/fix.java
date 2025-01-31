package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
public class fix extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewpager;
    private fixadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix);

        //获取点击的ID
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", -1);
        adapter = new fixadapter(this.getSupportFragmentManager(), id);

        viewpager = findViewById(R.id.viewpager2);
        viewpager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(viewpager);

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "默认用户名");

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
    public void imageexit1(View v){
        finish();
    }
}