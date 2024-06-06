package com.example.myapplication;

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
