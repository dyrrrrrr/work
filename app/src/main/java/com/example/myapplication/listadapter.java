package com.example.myapplication;

import android.content.Intent;
import android.hardware.display.DeviceProductInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class listadapter extends RecyclerView.Adapter<listadapter.myholder> {
    private List<moneyitem> items=new ArrayList<>();
    private OnLongClickListener onLongClickListener;

    public listadapter(List<moneyitem> list){
        this.items=list;
    }

    @NonNull
    @Override
    public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.act_list,null);
        return new myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myholder holder, int position) {
        //绑定数据
        moneyitem item=items.get(position);
        //设置
        holder.num.setText(String.valueOf(item.getNum()));
        holder.aspect.setText("类型："+item.getAspect());
        holder.time.setText("时间:"+item.getTime());

        holder.itemView.setOnClickListener(v -> {
            // 可以在这里添加跳转到修改页面的逻辑
            // 例如，使用 Intent 传递当前项的数据
            Intent intent = new Intent(holder.itemView.getContext(), fix.class);
            intent.putExtra("ID", item.getId()); // 假设 moneyitem 类有一个 getId 方法
            intent.putExtra("NUM", item.getNum()); // 假设 moneyitem 类有一个 getNum 方法
            intent.putExtra("ASPECT", item.getAspect()); // 假设 moneyitem 类有一个 getAspect 方法
            intent.putExtra("TIME", item.getTime()); // 假设 moneyitem 类有一个 getTime 方法

            // 启动修改页面
            holder.itemView.getContext().startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (onLongClickListener != null) {
                onLongClickListener.onLongClick(holder.getAdapterPosition());
                return true;
            }
            return false;
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class myholder extends RecyclerView.ViewHolder{
        TextView num,aspect,time;

        public myholder(@NonNull View itemView) {
            super(itemView);

            num=itemView.findViewById(R.id.num);
            aspect=itemView.findViewById(R.id.aspect);
            time=itemView.findViewById(R.id.time);


        }
    }
    public void setOnLongClickListener(OnLongClickListener listener) {
        this.onLongClickListener = listener;
    }

    public interface OnLongClickListener {
        void onLongClick(int position);
    }
    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);

    }
    public moneyitem getItem(int position) {
        return items.get(position);
    }
}
