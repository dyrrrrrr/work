package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link report_forms#newInstance} factory method to
 * create an instance of this fragment.
 */
public class report_forms extends Fragment {
    Spinner spinner;
    TextView period;
    boolean isMonthly = true;
    PieChart p1;
    PieChart p2;

    ArrayList<PieEntry> entries1 = new ArrayList<>();
    ArrayList<PieEntry> entries2 = new ArrayList<>();

    private int year,month;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, ea.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public report_forms() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment report_forms.
     */
    // TODO: Rename and change types and number of parameters
    public static report_forms newInstance(String param1, String param2) {
        report_forms fragment = new report_forms();
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
        setHasOptionsMenu(true); // 调用这个来启用菜单
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_forms, container, false);
        period=view.findViewById(R.id.period);
        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0); // 设置默认值

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String Date = dateFormat.format(calendar.getTime());
        String[] part = Date.split("/");
        year= Integer.parseInt(part[0]);
        month=Integer.parseInt(part[1]);
        Button pre=view.findViewById(R.id.qian);
        Button next=view.findViewById(R.id.hou);

        p1=view.findViewById(R.id.costpc);
        p2=view.findViewById(R.id.savepc);

        p1.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                if (entry == null)
                    return;
                String aspect = (String) entry.getData();
                Intent intent = new Intent(getContext(), aspect.class);
                Log.i("111",aspect+year+month+isMonthly);
                intent.putExtra("aspect", aspect);
                intent.putExtra("year",year);
                intent.putExtra("month",month);
                intent.putExtra("is",isMonthly);
                startActivity(intent);
            }
            @Override
            public void onNothingSelected() {
            }
        });
        p2.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                if (entry == null)
                    return;
                String aspect = (String) entry.getData();
                Log.i("111",aspect+year+month+isMonthly);
                Intent intent = new Intent(getContext(), aspect.class);
                intent.putExtra("aspect", aspect);
                intent.putExtra("year",year);
                intent.putExtra("month",month);
                intent.putExtra("is",isMonthly);
                startActivity(intent);
            }
            @Override
            public void onNothingSelected() {
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 处理选中项
                isMonthly = position == 0;
                updatePeriodDisplay();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMonthly) {
                    year--;
                } else { // 否则是月视图，前一个月
                    if (month == 1) {
                        year--;
                        month = 12;
                    } else {
                        month--;
                    }
                }
                updatePeriodDisplay();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 如果是年视图，后一年
                if (!isMonthly) {
                    year++;
                } else { // 否则是月视图，后一个月
                    if (month == 12) {
                        year++;
                        month = 1;
                    } else {
                        month++;
                    }
                }
                updatePeriodDisplay();
            }

        });


        return view;

    }

    private void updatePeriodDisplay() {
        String username=globaldata.getInstance().getUsername();
        manager_money manager=new manager_money(getContext());
        entries1.clear();
        entries2.clear();

        if(!isMonthly){
            period.setText(String.valueOf(year));
            List<moneyitem> list=manager.aspectlist(username,String.valueOf(year));

            for (moneyitem item:list){
                if (item.getNum()>0){
                    PieEntry pieEntry=new PieEntry(item.getNum(), item.getAspect()+"("+item.getNum()+")");
                    pieEntry.setData(item.getAspect());
                    entries2.add(pieEntry);

                }else {
                    PieEntry pieEntry = new PieEntry(Math.abs(item.getNum()), item.getAspect()+"("+Math.abs(item.getNum())+")");
                    pieEntry.setData(item.getAspect()); // 设置 data 属性
                    entries1.add(pieEntry);

                }
                Log.i("222",item.getAspect()+String.valueOf(item.getNum()));
            }

        }else {
            period.setText(String.format("%d/%02d",year,month));
            List<moneyitem> list=manager.aspectlist(username,String.format("%d/%02d",year,month));

            for (moneyitem item:list){
                if (item.getNum()>0){
                    PieEntry pieEntry=new PieEntry(item.getNum(), item.getAspect()+"("+item.getNum()+")");
                    pieEntry.setData(item.getAspect());
                    Log.i("111",item.getAspect());
                    entries2.add(pieEntry);
                }else {
                    PieEntry pieEntry = new PieEntry(Math.abs(item.getNum()), item.getAspect()+"("+Math.abs(item.getNum())+")");
                    pieEntry.setData(item.getAspect()); // 设置 data 属性
                    entries1.add(pieEntry);
                }
            }


        }

        PieDataSet pset1=new PieDataSet(entries1,null);
        PieDataSet pset2=new PieDataSet(entries2,null);


        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.rgb(144,238,144));
        colors.add(Color.rgb(127,255,212));
        colors.add(Color.rgb(255,140,0));
        colors.add(Color.rgb(255,20,147));
        colors.add(Color.rgb(255,192,203));
        colors.add(Color.rgb(255,255,0));
        colors.add(Color.rgb(255,215,0));
        colors.add(Color.rgb(222,184,135));
        pset1.setColors(colors);
        pset2.setColors(colors);


        p1.setDescription(null);
        p1.setData(new PieData(pset1));
        p1.setUsePercentValues(true);
        p1.setDrawSliceText(false);
        p1.setHoleRadius(0f);
        p1.setTransparentCircleRadius(0f);
        p1.notifyDataSetChanged();
        p1.invalidate();

        p2.setDescription(null);
        p2.setData(new PieData(pset2));
        p2.setUsePercentValues(true);
        p2.setDrawSliceText(false);
        p2.setHoleRadius(0f);
        p2.notifyDataSetChanged();
        p2.invalidate();
        p2.setTransparentCircleRadius(0f);

    }

}
