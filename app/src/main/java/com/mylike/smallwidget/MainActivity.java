package com.mylike.smallwidget;

import androidx.appcompat.app.AppCompatActivity;

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
        if (!TextUtils.isEmpty(main)) {
            TextView mTextView = findViewById(R.id.textView);
            mTextView.setText(main);
        }
    }
}