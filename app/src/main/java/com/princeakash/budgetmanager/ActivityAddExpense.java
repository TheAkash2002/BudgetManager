package com.princeakash.budgetmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;
import static java.lang.Integer.parseInt;

public class ActivityAddExpense extends AppCompatActivity {

    Spinner spinnerCategory;
    Button btnAddExpense;
    EditText editAmount;
    String selectedCategory;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnAddExpense = findViewById(R.id.buttonAddExpense);
        editAmount = findViewById(R.id.editAmount);
        btnAddExpense.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertExpenseData(selectedCategory, parseInt(editAmount.getText().toString()));
                        if(isInserted == true)
                            Toast.makeText(ActivityAddExpense.this, "Data Inserted", LENGTH_SHORT).show();
                    }
                }
        );
    }
}
