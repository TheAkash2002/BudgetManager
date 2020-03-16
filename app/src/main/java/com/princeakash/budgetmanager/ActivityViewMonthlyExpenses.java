package com.princeakash.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class ActivityViewMonthlyExpenses extends AppCompatActivity {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.buttonViewData)
    Button btnViewData;
    @BindView(R.id.spinnerMonth)
    Spinner spinnerMonth;
    @BindView(R.id.spinnerYear)
    Spinner spinnerYear;
    RadioButton radioButton;
    String targetMonth, targetYear;
    DatabaseHelper myDb;

    public void TextToNum(){
        String tMonth = spinnerMonth.getSelectedItem().toString();
        targetYear = spinnerYear.getSelectedItem().toString();
        switch (tMonth){
            case "January": targetMonth = "01";
                break;
            case "February": targetMonth = "02";
                break;
            case "March": targetMonth = "03";
                break;
            case "April": targetMonth = "04";
                break;
            case "May": targetMonth = "05";
                break;
            case "June": targetMonth = "06";
                break;
            case "July": targetMonth = "07";
                break;
            case "August": targetMonth = "08";
                break;
            case "September": targetMonth = "09";
                break;
            case "October": targetMonth = "10";
                break;
            case "November": targetMonth = "11";
                break;
            case "December": targetMonth = "12";
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_monthly_expenses);
        ButterKnife.bind(this);
        myDb = new DatabaseHelper(this);

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
                        boolean isFound = true;
                        switch(radioGroup.getCheckedRadioButtonId()){
                            case R.id.radioList:
                                TextToNum();
                                if(myDb.viewMonthlyExpenseData(targetMonth, targetYear).getCount()==0){
                                    isFound = false;
                                    break;
                                }
                                intent = new Intent(ActivityViewMonthlyExpenses.this, ActivityViewMonthlyExpensesList.class);
                                intent.putExtra("TargetYear", targetYear);
                                intent.putExtra("TargetMonth", targetMonth);
                                startActivity(intent);
                                break;
                            case R.id.radioBar:
                                TextToNum();
                                if(myDb.viewMonthlyExpenseData(targetMonth, targetYear).getCount()==0){
                                    isFound = false;
                                    break;
                                }
                                intent = new Intent(ActivityViewMonthlyExpenses.this, ActivityViewMonthlyExpensesBar.class);
                                intent.putExtra("TargetYear", targetYear);
                                intent.putExtra("TargetMonth", targetMonth);
                                startActivity(intent);
                                break;
                            case R.id.radioPie:
                                TextToNum();
                                if(myDb.viewMonthlyExpenseData(targetMonth, targetYear).getCount()==0){
                                    isFound = false;
                                    break;
                                }
                                intent = new Intent(ActivityViewMonthlyExpenses.this, ActivityViewMonthlyExpensesPie.class);
                                intent.putExtra("TargetYear", targetYear);
                                intent.putExtra("TargetMonth", targetMonth);
                                startActivity(intent);
                                break;
                        }
                        if(!isFound)
                        Toast.makeText(ActivityViewMonthlyExpenses.this, "No data found for given query.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
