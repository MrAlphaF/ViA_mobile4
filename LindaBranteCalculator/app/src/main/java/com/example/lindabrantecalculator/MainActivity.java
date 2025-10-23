package com.example.lindabrantecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    float number = 0;
    EditText inputField;
    TextView outputField;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputField = findViewById(R.id.numInput);
        outputField = findViewById(R.id.numOutput);
        sp = getSharedPreferences("Memory", Context.MODE_PRIVATE);
    }

    public float getInput(){
        try {
            if (!inputField.getText().toString().trim().isEmpty()){
                return Float.parseFloat(inputField.getText().toString());
            }
            return 0;
        } catch (Exception e){
            Toast.makeText(this,"INVALID INPUT. INPUT CHANGED TO 1", Toast.LENGTH_LONG).show();

            Log.e("TAG", "Calculation error", e);
        }
        return 1;
    }

    @SuppressLint("SetTextI18n")
    public void equals(View v){
        inputField.setText("");
        outputField.setText(number + "");
    }

    public void add(View v){
        number += getInput();
        equals(v);
    }

    public void subtract(View v){
        number -= getInput();
        equals(v);
    }

    public void multiply(View v){
        number *= getInput();
        equals(v);
    }

    public void divide(View v){
        float tempNum = getInput();
        if (tempNum != 0) {
            number = number / getInput();
        } else {
            Toast.makeText(this,"DIVISION BY 0 IS ILLEGAL", Toast.LENGTH_LONG).show();
            number /= 1;
        }
        equals(v);
    }

    public void memoryEdit(float num){
        editor = sp.edit();
        editor.putFloat("memoryNumber", num);
        editor.commit();
    }

    public void memorySave(View v){
        memoryEdit(number);
    }

    public void memoryClear(View v){
        memoryEdit(0);
    }

    @SuppressLint("SetTextI18n")
    public void memoryRead(View v){
        inputField.setText(sp.getFloat("memoryNumber",0)+"");
    }

    public void reset(View v){
        number = 0;
        equals(v);
    }
}
