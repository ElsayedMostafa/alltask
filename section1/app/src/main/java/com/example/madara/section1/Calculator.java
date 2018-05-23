package com.example.madara.section1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Button add = (Button) findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText fnum = (EditText) findViewById(R.id.txt_fnum);
                EditText snum = (EditText) findViewById(R.id.txt_snum);
                TextView txtv = (TextView) findViewById(R.id.txtv_show);
                int num1 = Integer.parseInt(fnum.getText().toString());
                int num2 = Integer.parseInt(snum.getText().toString());
                int result = num1 + num2;
                txtv.setText(String.valueOf(result));


            }
        });

    }
}
