package com.princeakash.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this, R.array.Months, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromMonth.setAdapter(adapterMonth);
        spinnerToMonth.setAdapter(adapterMonth);

        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this, R.array.Years, android.R.layout.simple_spinner_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromYear.setAdapter(adapterYear);
        spinnerToYear.setAdapter(adapterYear);

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
