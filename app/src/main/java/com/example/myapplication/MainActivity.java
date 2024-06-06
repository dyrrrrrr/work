package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String Tag="jump";
    Runnable runnable= new Runnable() {
        @Override
        public void run() {
            Intent mainIntent=new Intent(MainActivity.this, login.class);
            startActivity(mainIntent);
            Log.i(Tag,"ok");
            finish();
        }
    };
    Handler handler=new Handler();
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(runnable,1000);

    }
}