package com.princeakash.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityRelativeMonthly extends AppCompatActivity {
    Spinner spinnerFromMonth, spinnerFromYear, spinnerToMonth, spinnerToYear;
    Button btnSeeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_monthly);
        spinnerFromMonth =  findViewById(R.id.spinnerFromMonth);
        spinnerFromYear = findViewById(R.id.spinnerFromYear);
        spinnerToMonth = findViewById(R.id.spinnerToMonth);
        spinnerToYear = findViewById(R.id.spinnerToYear);
        btnSeeData = findViewById(R.id.buttonSeeData);
        btnSeeData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityRelativeMonthly.this, ActivityRelativeLine.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
