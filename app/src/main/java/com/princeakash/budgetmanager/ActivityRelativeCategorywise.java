package com.princeakash.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityRelativeCategorywise extends AppCompatActivity {

    Spinner spinnerFromMonth, spinnerFromYear, spinnerToMonth, spinnerToYear, spinnerCategory;
    Button btnSeeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_categorywise);
        spinnerFromMonth =  findViewById(R.id.spinnerFromMonth);
        spinnerFromYear = findViewById(R.id.spinnerFromYear);
        spinnerToMonth = findViewById(R.id.spinnerToMonth);
        spinnerToYear = findViewById(R.id.spinnerToYear);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSeeData = findViewById(R.id.buttonSeeData);
        btnSeeData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityRelativeCategorywise.this, ActivityRelativeLine.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
