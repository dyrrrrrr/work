package com.example.myapplication;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class day extends AppCompatActivity {
    TextView day,innum,outnum;
    manager_money manager=new manager_money(this);
    private RecyclerView recyclerView;
    private listadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        Intent intent = getIntent();
        String receivedDate = (String) intent.getSerializableExtra("DATE_KEY");
        String username = globaldata.getInstance().getUsername();
        day=findViewById(R.id.day);
        day.setText(receivedDate);
        Log.i("111",username);
        innum=findViewById(R.id.innum1);
        outnum=findViewById(R.id.outnum1);
        float in=0,out=0;
        String[] Args={username,receivedDate};
        Log.i("111",username+receivedDate);
        List<moneyitem> list=manager.list("username like ? AND time like ?",Args);
        recyclerView=findViewById(R.id.list1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new listadapter(list);
        recyclerView.setAdapter(adapter);
        String[] Args1={username,receivedDate,Float.toString(0.0f)};
        List<moneyitem> list1=manager.list("username like ? and time like ? and num >?",Args1);
        List<moneyitem> list2=manager.list("username like ? and time like ? and num <?",Args1);
        for(moneyitem item:list1){
            in+=item.getNum();
        }
        for (moneyitem item:list2){
            out+=item.getNum();
        }
        Log.i("111",String.valueOf(out)+" "+String.valueOf(in));
        innum.setText(String.valueOf(in));
        outnum.setText(String.valueOf(out));

        adapter.setOnLongClickListener(new listadapter.OnLongClickListener() {
            @Override
            public void onLongClick(int position) {
                onItemLongClick(position);
            }
        });

    }
    public void back(View v){

        Intent intent = new Intent(this, viewpager.class);
        intent.putExtra("id",1);
        startActivity(intent);
    }

    private void onItemLongClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认删除").setMessage("您确定要删除这项记录吗？").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (adapter != null) {
                    moneyitem itemToDelete = adapter.getItem(position);
                    manager.delete(itemToDelete.getId());
                    adapter.removeItem(position);
                    updatedata();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("否", null);
        builder.create().show();
    }
    private void updatedata(){
        String username = globaldata.getInstance().getUsername();
        float insum = 0, outsum = 0;
        Intent intent = getIntent();
        String receivedDate = (String) intent.getSerializableExtra("DATE_KEY");
        String[] args = {username,receivedDate,String.valueOf(0.0f)};

        List<moneyitem> list1 = manager.list("username=? and time=? and num > ?", args);
        for (moneyitem item : list1) {
            insum += item.getNum();
        }

        List<moneyitem> list2 = manager.list("username=? and time = ? and num < ?", args);
        for (moneyitem item : list2) {
            outsum += item.getNum();
        }

        innum.setText(String.valueOf(insum));
        outnum.setText(String.valueOf(outsum));

    }


}