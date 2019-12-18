package com.princeakash.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityViewMonthlyExpenses extends AppCompatActivity {

    RadioGroup radioGroup;
    Button btnViewData;
    Spinner spinnerMonth, spinnerYear;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_monthly_expenses);
        radioGroup = findViewById(R.id.radioGroup);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear =  findViewById(R.id.spinnerYear);
        btnViewData = findViewById(R.id.buttonViewData);

        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this, R.array.Months, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapterMonth);

        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this, R.array.Years, android.R.layout.simple_spinner_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);

        btnViewData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        switch(radioGroup.getCheckedRadioButtonId()){
                            case R.id.radioList:
                                intent = new Intent(ActivityViewMonthlyExpenses.this, ActivityViewMonthlyExpensesList.class);
                                startActivity(intent);
                                break;
                            case R.id.radioBar:
                                intent = new Intent(ActivityViewMonthlyExpenses.this, ActivityViewMonthlyExpensesBar.class);
                                startActivity(intent);
                                break;
                            case R.id.radioPie:
                                intent = new Intent(ActivityViewMonthlyExpenses.this, ActivityViewMonthlyExpensesPie.class);
                                startActivity(intent);
                                break;
                        }

                    }
                }
        );
    }
}
