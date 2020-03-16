package com.princeakash.budgetmanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_SHORT;
import static com.princeakash.budgetmanager.DatabaseHelper.DateToString;
import static java.lang.Integer.parseInt;

public class ActivityEditExpense extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AddCategoryDialogFragment.AddCategoryDialogListener {

    @BindView(R.id.spinnerCategory)
    Spinner spinnerCategory;
    @BindView(R.id.buttonUpdateExpense)
    Button btnUpdateExpense;
    @BindView(R.id.editAmount)
    EditText editAmount;
    @BindView(R.id.buttonCancel)
    Button btnCancel;
    @BindView(R.id.buttonAddCategory)
    Button btnAddCategory;

    String selectedCategory;
    DatabaseHelper myDb;
    Calendar calendar;
    String date, dateMonth, dateYear;
    BaseAdapter adapterCats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_expense);

        ButterKnife.bind(this);
        myDb = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        final String id = bundle.getString("expenseID");
        date = bundle.getString("expenseDate");
        String amount = bundle.getString("expenseAmount");
        editAmount.setText(amount);

        FilterMonthAndYear();
        btnUpdateExpense.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated;
                        if(myDb.isTargetSet(dateMonth, dateYear)==true) {
                            if(myDb.getTotalExpensesTillNow(dateMonth, dateYear) + parseInt(editAmount.getText().toString()) <= myDb.getTarget(dateMonth, dateYear)) {
                                isUpdated = myDb.updateExpenseData(id, selectedCategory, editAmount.getText().toString(), date);
                                if (isUpdated) {
                                    Toast.makeText(ActivityEditExpense.this, "Data Updated. You could spend Rs." + (myDb.getTarget(dateMonth, dateYear) - myDb.getTotalExpensesTillNow(dateMonth, dateYear)) + " in " + DatabaseHelper.DateToString(dateYear, dateMonth), LENGTH_SHORT).show();
                                    Intent intent = new Intent(ActivityEditExpense.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(ActivityEditExpense.this, "Failed to update data.", LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(ActivityEditExpense.this, "Overflow! You are going overboard by Rs." + (myDb.getTotalExpensesTillNow(dateMonth, dateYear) + parseInt(editAmount.getText().toString())-myDb.getTarget(dateMonth, dateYear)) + " for " + DatabaseHelper.DateToString(dateYear, dateMonth), LENGTH_SHORT).show();
                        }
                        else {
                            String datestr = DateToString(dateYear, dateMonth);
                            Toast.makeText(ActivityEditExpense.this, "Please set a target for " + datestr + " first.", LENGTH_SHORT).show();
                        }
                    }
                }
        );

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityEditExpense.this, MainActivity.class);
                startActivity(intent);
            }
        });

        PopulateSpinner();
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new AddCategoryDialogFragment();
                newFragment.show(getSupportFragmentManager(), "AddCategoryDialogFragment");
            }
        });
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String categoryName) {
        boolean isInserted = myDb.insertCategory(categoryName);
        if(isInserted){
            Toast.makeText(this, "Inserted new category successfully.", LENGTH_SHORT).show();
            PopulateSpinner();
        }
        else{
            Toast.makeText(this, "Failed to insert new category.", LENGTH_SHORT).show();
        }
    }

    public void PopulateSpinner(){
        Cursor cursor = myDb.viewAllCategoryData();
        final ArrayList<String> categories = new ArrayList<String>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                categories.add(cursor.getString(1));
            } while(cursor.moveToNext());
        }
        adapterCats = new BaseAdapter() {
            @Override
            public int getCount() {
                return categories.size();
            }

            @Override
            public Object getItem(int position) {
                return categories.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                CategoryHolder holder;
                View categoryView = convertView;
                if(categoryView == null){
                    categoryView = getLayoutInflater().inflate(android.R.layout.simple_spinner_item, parent, false);

                    holder = new CategoryHolder();
                    holder.textViewCategory = categoryView.findViewById(android.R.id.text1);
                    categoryView.setTag(holder);
                }
                else{
                    holder  = (CategoryHolder) categoryView.getTag();
                }
                String category = categories.get(position);
                holder.textViewCategory.setText(category);
                return categoryView;
            }

            class CategoryHolder{
                public TextView textViewCategory;
            }
        };
        spinnerCategory.setAdapter(adapterCats);
        spinnerCategory.setOnItemSelectedListener(this);
    }
}