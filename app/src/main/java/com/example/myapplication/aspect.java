package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class aspect extends AppCompatActivity {
    manager_money manager=new manager_money(this);
    TextView period,sum,aspect;
    private RecyclerView recyclerView;
    private listadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspect);
        Intent intent = getIntent();
        String as = (String) intent.getSerializableExtra("aspect");
        period=findViewById(R.id.period);
        sum=findViewById(R.id.sum);
        aspect=findViewById(R.id.aspect);
        recyclerView=findViewById(R.id.list);

        aspect.setText(as);
        int year = (int) intent.getSerializableExtra("year");
        int month = (int) intent.getSerializableExtra("month");
        boolean ismonthly = (boolean) intent.getSerializableExtra("is");
        String username = globaldata.getInstance().getUsername();
        String time;

        if(ismonthly){
            time=String.format("%d/%02d",year,month);
        }else{
            time=String.valueOf(year);
        }
        period.setText(time);
        float summery=0;
        String[] Args={username,time+"%",as};
        List<moneyitem> list=manager.list("username like ? AND time like ? and aspect like ?",Args);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new listadapter(list);
        recyclerView.setAdapter(adapter);
        for(moneyitem item:list){
            summery+=item.getNum();
        }
        sum.setText(String.valueOf(summery));
    }

    public void back(View v){
        finish();
    }
}