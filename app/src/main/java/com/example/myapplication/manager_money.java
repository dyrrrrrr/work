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
    public void deleteAll(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(tbname,null,null);
        db.close();
    }
}
