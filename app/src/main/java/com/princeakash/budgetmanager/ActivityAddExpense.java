package com.princeakash.budgetmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static java.lang.Integer.parseInt;

public class ActivityAddExpense extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerCategory;
    Button btnAddExpense;
    EditText editAmount;
    String selectedCategory;
    DatabaseHelper myDb;
    Calendar calendar;
    String date, dateMonth, dateYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);
        myDb = new DatabaseHelper(this);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnAddExpense = findViewById(R.id.buttonAddExpense);
        editAmount = findViewById(R.id.editAmount);
        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.format(calendar.getTime());
        FilterMonthAndYear();
        btnAddExpense.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted;
                        if(myDb.isTargetSet(dateMonth, dateYear)==true) {
                            if(myDb.getTotalExpensesTillNow(dateMonth, dateYear) + Integer.parseInt(editAmount.getText().toString()) < myDb.getTarget(dateMonth, dateYear)) {
                                isInserted = myDb.insertExpenseData(selectedCategory, editAmount.getText().toString());
                                if (isInserted == true)
                                    Toast.makeText(ActivityAddExpense.this, "Data Inserted. You can spend Rs." + (myDb.getTarget(dateMonth, dateYear)-myDb.getTotalExpensesTillNow(dateMonth, dateYear)) + " this month.", LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(ActivityAddExpense.this, "Overflow! You are going overboard by Rs." + (myDb.getTotalExpensesTillNow(dateMonth, dateYear) + Integer.parseInt(editAmount.getText().toString())-myDb.getTarget(dateMonth, dateYear)), LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(ActivityAddExpense.this, "Please set a target for " + dateYear + "-" + dateMonth + " first.", LENGTH_SHORT).show();
                    }
                }
        );

        ArrayAdapter<CharSequence> adapterCats = ArrayAdapter.createFromResource(this, R.array.Cats, android.R.layout.simple_spinner_item);
        adapterCats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCats);
        spinnerCategory.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        selectedCategory = text;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedCategory = "Books and Stationery";
    }

    public void FilterMonthAndYear(){
        String[] dateBroken = date.split("-");
        dateYear = dateBroken[0];
        dateMonth = dateBroken[1];
    }
}
