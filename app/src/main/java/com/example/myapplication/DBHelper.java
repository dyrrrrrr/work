package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;//版本号
    private static final String DB_NAME = "app.db";//数据库名字
    public static final String USER = "user";//表名
    public static final String MONEY = "money";//表名
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//创建数据库
        db.execSQL("CREATE TABLE "+USER+"(USERNAME TEXT primary key,PASSWORD TEXT NOT NULL)");
        db.execSQL("CREATE TABLE "+MONEY+"(USERNAME TEXT,ASPECT TEXT,NUM REAL,TIME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
