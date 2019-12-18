package com.princeakash.budgetmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityViewMonthlyExpenses extends AppCompatActivity {

    RadioGroup radioGroup;
    Button btnViewData;
    Spinner spinnerMonth, spinnerYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_monthly_expenses);
        radioGroup = findViewById(R.id.radioGroup);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear =  findViewById(R.id.spinnerYear);
        btnViewData = findViewById(R.id.buttonViewData);
        btnViewData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );
    }
}
