package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {
    Button register;
    EditText username,password;
    DBHelper myhelper;
    SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register=findViewById(R.id.register);
        username=findViewById(R.id.username);
        password=findViewById(R.id.Password);

        myhelper=new DBHelper(this,"user",null, 1);
        db=myhelper.getWritableDatabase();
    }
    public  void register1(View v){
        String uname=username.getText().toString();
        String pwd=password.getText().toString();
        if(uname.equals("")||pwd.equals("")){
            Toast.makeText(getApplicationContext(),"用户名或密码不能为空！！",Toast.LENGTH_SHORT).show();
        }else {
            String[] columns = { "username" };
            String selection = "username = ?";
            String[] selectionArgs = { uname };
            Cursor cursor = db.query("user", columns, selection, selectionArgs, null, null, null);

            if (cursor.getCount() > 0) {
                // 用户名已存在
                cursor.close();
                Toast.makeText(getApplicationContext(), "注册失败，用户名已存在！", Toast.LENGTH_SHORT).show();
            } else {
                ContentValues cv=new ContentValues();
                cv.put("username",uname);
                cv.put("password",pwd);
                db.insert("user",null,cv);
                Intent intent=new Intent(this, login.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"账号注册成功！！",Toast.LENGTH_SHORT).show();
            }
        }
    }
}