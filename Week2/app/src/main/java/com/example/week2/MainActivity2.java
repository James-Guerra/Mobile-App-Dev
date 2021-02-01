package com.example.week2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void getArea(View v) {
        EditText input = (EditText) findViewById(R.id.inputCircleSize);
        TextView output = (TextView) findViewById(R.id.areadOutput);
        double radius = Double.parseDouble(input.getText().toString());
        double area = Math.round((Math.PI * Math.pow(radius, 2) * 100.0)) / 100.0;
        output.setText(Double.toString(area));
    }
}