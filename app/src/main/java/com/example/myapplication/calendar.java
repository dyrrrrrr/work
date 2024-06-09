package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link calendar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class calendar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public calendar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment calendar.
     */
    // TODO: Rename and change types and number of parameters
    public static calendar newInstance(String param1, String param2) {
        calendar fragment = new calendar();
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
        View view=inflater.inflate(R.layout.fragment_calendar, container, false);
        CalendarView calendarView = view.findViewById(R.id.calenderView);
        String username = globaldata.getInstance().getUsername();
        manager_money manager=new manager_money(getContext());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 获取点击的日期
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                // 格式化日期
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth); // 设置日期
                String clickedDate = dateFormat.format(calendar.getTime());
                // 创建Intent，用于启动新页面
                Intent intent = new Intent(getContext(), day.class);
                intent.putExtra("DATE_KEY", clickedDate); // 将日期作为额外数据传递

                // 启动新页面
                startActivity(intent);
            }
        });

        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioButton income,expense,total;
        income=view.findViewById(R.id.Income);
        expense=view.findViewById(R.id.Expense);
        total=view.findViewById(R.id.Total);
        BarChart barChart=view.findViewById(R.id.last7);
        XAxis x=barChart.getXAxis();
        YAxis yl=barChart.getAxisLeft();
        yl.setDrawGridLines(false);//隐藏网格线
        YAxis yr=barChart.getAxisRight();
        yr.setDrawGridLines(false);

        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setGranularity(1f);//条形间隔为1
        barChart.setDescription(null);
        barChart.setTouchEnabled(false);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int color = 0;
                ArrayList<BarEntry> entries = new ArrayList<>();
                if (checkedId == income.getId()) {
                    color= Color.rgb(173, 255, 47);
                    ForIncome(entries);
                    yl.setAxisMinimum(0f);
                    yr.setAxisMinimum(0f);
                } else if (checkedId == expense.getId()) {
                    color=Color.rgb(255, 69, 0);
                    ForExpense(entries);
                    yl.setAxisMinimum(0f);
                    yr.setAxisMinimum(0f);
                } else if (checkedId == total.getId()) {
                    color=Color.rgb(135, 206, 250);
                    ForTotal(entries);
                    yl.resetAxisMinimum();
                    yr.resetAxisMinimum();
                }
                BarDataSet barDataSet=new BarDataSet(entries,"数值");
                barDataSet.setColor(color);
                barChart.setData(new BarData(barDataSet));
                barChart.notifyDataSetChanged();
                barChart.invalidate();
            }

            private void ForTotal(ArrayList<BarEntry> entries) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                calendar.add(Calendar.DAY_OF_MONTH, -6);//6天前
                String Date = dateFormat.format(calendar.getTime());
                String[] part = Date.split("/");
                int day = Integer.parseInt(part[2]);
                float value = 0;
                String[] args = {username, Date};
                List<moneyitem> list = manager.list("username=? and time like ? ", args);
                for (moneyitem item : list) {
                    value += item.getNum();
                }
                entries.add(new BarEntry(day, value));
                Log.i("111", Date);
                for (int i = 0; i < 6; i++) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    Date = dateFormat.format(calendar.getTime());
                    part = Date.split("/");
                    day = Integer.parseInt(part[2]);
                    value = 0;
                    args = new String[]{username, Date};
                    list = manager.list("username=? and time like ?", args);
                    for (moneyitem item : list) {
                        value += item.getNum();
                    }
                    entries.add(new BarEntry(day, value));
                    Log.i("111", Date + String.valueOf(value) + username);
                }

            }

            private void ForIncome(ArrayList<BarEntry> entries) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                calendar.add(Calendar.DAY_OF_MONTH, -6);//14天前
                String Date = dateFormat.format(calendar.getTime());
                String[] part = Date.split("/");
                int day = Integer.parseInt(part[2]);
                float value = 0;
                String[] args = {username, Date, String.valueOf(0.0f)};
                List<moneyitem> list = manager.list("username=? and time like ? and num > ?", args);
                for (moneyitem item : list) {
                    value += item.getNum();
                }
                entries.add(new BarEntry(day, value));
                Log.i("111", Date);
                for (int i = 0; i < 6; i++) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    Date = dateFormat.format(calendar.getTime());
                    part = Date.split("/");
                    day = Integer.parseInt(part[2]);
                    value = 0;
                    args = new String[]{username, Date, Float.toString(0.0f)};
                    list = manager.list("username=? and time like ? and num > ?", args);
                    for (moneyitem item : list) {
                        value += item.getNum();
                    }
                    entries.add(new BarEntry(day, value));
                    Log.i("111", Date + String.valueOf(value) + username);
                }
            }

            private void ForExpense(ArrayList<BarEntry> entries) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                calendar.add(Calendar.DAY_OF_MONTH, -6);//14天前
                String Date = dateFormat.format(calendar.getTime());
                String[] part = Date.split("/");
                int day = Integer.parseInt(part[2]);
                float value = 0;
                String[] args = {username, Date, String.valueOf(0.0f)};
                List<moneyitem> list = manager.list("username=? and time like ? and num < ?", args);
                for (moneyitem item : list) {
                    value += item.getNum();
                }
                entries.add(new BarEntry(day, Math.abs(value)));
                Log.i("111", Date);
                for (int i = 0; i < 6; i++) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    Date = dateFormat.format(calendar.getTime());
                    part = Date.split("/");
                    day = Integer.parseInt(part[2]);
                    value = 0;
                    args = new String[]{username, Date, Float.toString(0.0f)};
                    list = manager.list("username=? and time like ? and num < ?", args);
                    for (moneyitem item : list) {
                        value += item.getNum();
                    }
                    entries.add(new BarEntry(day, Math.abs(value)));
                    Log.i("111", String.valueOf(-value));
                }
            }
        });

        return view;
    }
}