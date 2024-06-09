package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class manager_money
{
    private DBHelper helper;
    private String tbname;
    public manager_money(Context context){
        helper=new DBHelper(context);
        tbname=DBHelper.MONEY;
    }
    public void add(moneyitem item){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("USERNAME",item.getUserame());
        values.put("ASPECT",item.getAspect());
        values.put("NUM",item.getNum());
        values.put("TIME",item.getTime());
        db.insert(tbname, null, values);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(tbname,"ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    @SuppressLint("Range")
    public List<moneyitem> list(String select,String[] ARGS){
        List<moneyitem> list=null;
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query(tbname,null,select,ARGS,null,null,"ROWID DESC");
        if(cursor!=null){
            list=new ArrayList<moneyitem>();
            while (cursor.moveToNext()){
                moneyitem item=new moneyitem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setUserame(cursor.getString(cursor.getColumnIndex("USERNAME")));
                item.setNum(cursor.getFloat(cursor.getColumnIndex("NUM")));
                item.setAspect(cursor.getString(cursor.getColumnIndex("ASPECT")));
                item.setTime(cursor.getString(cursor.getColumnIndex("TIME")));
                list.add(item);
            }
            cursor.close();
        }
        db.close();
        return list;
    }

    public void update(int id,float account,String selecttype){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NUM",account);
        values.put("ASPECT", selecttype);
        String selection = "ID = ?";
        String[] selectionArgs = { String.valueOf(id) };
        db.update(tbname, values, selection, selectionArgs);
    }

    @SuppressLint("Range")
    public List<moneyitem> aspectlist(String username, String period){
        List<moneyitem> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] args={username,period+"%"};
        String sql="select aspect,sum(num) as total_num from money where username like ? and time like ? group by aspect";
        Cursor cursor= db.rawQuery(sql,args);
        if(cursor!=null){
            list=new ArrayList<moneyitem>();
            while (cursor.moveToNext()){
                moneyitem item=new moneyitem();
                item.setAspect(cursor.getString(cursor.getColumnIndex("ASPECT")));
                item.setNum(cursor.getFloat(cursor.getColumnIndex("total_num")));
                Log.i("111",item.getAspect()+String.valueOf(item.getNum()));
                list.add(item);
            }
            cursor.close();
        }
        db.close();
        return list;
    }
    public void deleteAll(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(tbname,null,null);
        db.close();
    }
}
