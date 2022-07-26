package com.mylike.smallwidget;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String main = getIntent().getStringExtra("main");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addWidget(View view) {
        new WidgetController().addToMainScreen(this);
    }
}