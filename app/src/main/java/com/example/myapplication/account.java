package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class account extends Fragment {

    private RecyclerView recyclerView;
    private listadapter adapter;
    TextView today,innum,outnum,thismonthnum;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment account.
     */
    // TODO: Rename and change types and number of parameters
    public static account newInstance(String param1, String param2) {
        account fragment = new account();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // 获取按钮并设置点击事件监听器
        ImageButton transactionButton = view.findViewById(R.id.transaction);
        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction(v); // 调用 transaction 方法
            }
        });


        //初始化控件
        recyclerView=view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        today=view.findViewById(R.id.today);
        Date cur=new Date();
        SimpleDateFormat Format=new SimpleDateFormat("yyyy/MM/dd");
        String time=Format.format(cur);
        today.setText(time);

        String username = globaldata.getInstance().getUsername();
        manager_money manager=new manager_money(getContext());
        innum=view.findViewById(R.id.innum);
        outnum=view.findViewById(R.id.outnum);
        float insum=0;
        float outsum=0;
        thismonthnum=view.findViewById(R.id.thismonthnum);
        float thsum=0;
        Calendar cal=Calendar.getInstance();
        cal.setTime(cur);
        cal.set(Calendar.DAY_OF_MONTH,1);
        String start= Format.format(cal.getTime());
        cal.add(Calendar.MONTH,1);
        cal.set(Calendar.DAY_OF_MONTH,0);
        String end=Format.format(cal.getTime());
        String[]ARgs={username,start,end};
        String[] Args={username,start,end,Float.toString(0.0f)};
        List<moneyitem> list1=manager.list("username=? and time between ? and ? and num >?",Args);
        for(moneyitem item:list1){
            insum+=item.getNum();
        }
        List<moneyitem> list2=manager.list("username=? and time between ? and ? and num <?",Args);
        for (moneyitem item:list2){
            outsum+=item.getNum();
        }
        innum.setText(String.valueOf(insum));
        outnum.setText(String.valueOf(outsum));
        List<moneyitem> List=manager.list("username like ? AND time between ? and ?",ARgs);
        for(moneyitem item:List){
            thsum+=item.getNum();
        }
        thismonthnum.setText(String.valueOf(thsum));

        String[] args={username};
        List<moneyitem> list=manager.list("username like ?",args);
        //初始化适配器
        adapter=new listadapter(list);
        recyclerView.setAdapter(adapter);

        adapter.setOnLongClickListener(new listadapter.OnLongClickListener() {
            @Override
            public void onLongClick(int position) {
                onItemLongClick(position);
            }
        });

        return view;
    }


    public void transaction(View v){
        Intent intent=new Intent(getActivity(), addact.class);
        startActivity(intent);
    }

    private void onItemLongClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("确认删除").setMessage("您确定要删除这项记录吗？").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (adapter != null) {
                    moneyitem itemToDelete = adapter.getItem(position);
                    manager_money manager = new manager_money(getContext());
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
        manager_money manager = new manager_money(getContext());
        float insum = 0, outsum = 0, thsum = 0;
        Calendar cal = Calendar.getInstance();
        Date cur = new Date();
        cal.setTime(cur);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String start = new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime());
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        String end = new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime());
        String[] args = {username, start, end};

        List<moneyitem> list1 = manager.list("username=? and time between ? and ? and num > ?", args);
        for (moneyitem item : list1) {
            insum += item.getNum();
        }

        List<moneyitem> list2 = manager.list("username=? and time between ? and ? and num < ?", args);
        for (moneyitem item : list2) {
            outsum += item.getNum();
        }

        List<moneyitem> list3 = manager.list("username like ? AND time between ? and ?", new String[]{username, start, end});
        for (moneyitem item : list3) {
            thsum += item.getNum();
        }

        innum.setText(String.valueOf(insum));
        outnum.setText(String.valueOf(outsum));
        thismonthnum.setText(String.valueOf(thsum));

    }

}