package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    Button login;
    EditText username,password;
    DBHelper myhelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=findViewById(R.id.login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.Password);

        myhelper=new DBHelper(this);
        db=myhelper.getWritableDatabase();
    }
    public void login(View btn){
        String uname=username.getText().toString();
        String pwd=password.getText().toString();
        //查询用户名和密码相同的数据
        Cursor cursor = db.query("user",new String[]{"username","password"}," username=? and password=?",
                new String[]{uname,pwd},null,null,null);
        int flag = cursor.getCount();
        if (flag!=0){
            //如果密码正确，进入主页
            globaldata.getInstance().setUsername(uname);
            Intent main=new Intent(this, viewpager.class);
            startActivity(main);
        }else{
            //密码错误，弹出提示
            Toast.makeText(this, "密码或用户名错误", Toast.LENGTH_SHORT).show();
        }



    }
    public void toregister(View btn){
        Intent main=new Intent(this,register.class);
        startActivity(main);
    }
}