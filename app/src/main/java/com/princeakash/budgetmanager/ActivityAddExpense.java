package com.princeakash.budgetmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_SHORT;
import static com.princeakash.budgetmanager.DatabaseHelper.DateToString;
import static java.lang.Integer.parseInt;

public class ActivityAddExpense extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.spinnerCategory)
    Spinner spinnerCategory;
    @BindView(R.id.buttonAddExpense)
    Button btnAddExpense;
    @BindView(R.id.editAmount)
    EditText editAmount;

    String selectedCategory;
    DatabaseHelper myDb;
    Calendar calendar;
    String date, dateMonth, dateYear;
    BaseAdapter adapterCats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);

        ButterKnife.bind(this);
        myDb = new DatabaseHelper(this);

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
                            if(myDb.getTotalExpensesTillNow(dateMonth, dateYear) + parseInt(editAmount.getText().toString()) <= myDb.getTarget(dateMonth, dateYear)) {
                                isInserted = myDb.insertExpenseData(selectedCategory, editAmount.getText().toString());
                                if (isInserted == true)
                                    Toast.makeText(ActivityAddExpense.this, "Data Inserted. You can spend Rs." + (myDb.getTarget(dateMonth, dateYear)-myDb.getTotalExpensesTillNow(dateMonth, dateYear)) + " this month.", LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(ActivityAddExpense.this, "Overflow! You are going overboard by Rs." + (myDb.getTotalExpensesTillNow(dateMonth, dateYear) + parseInt(editAmount.getText().toString())-myDb.getTarget(dateMonth, dateYear)), LENGTH_SHORT).show();
                        }
                        else {
                            String datestr = DateToString(dateYear, dateMonth);
                            Toast.makeText(ActivityAddExpense.this, "Please set a target for " + datestr + " first.", LENGTH_SHORT).show();
                        }
                    }
                }
        );

        //ArrayAdapter<CharSequence> adapterCats = ArrayAdapter.createFromResource(this, R.array.Cats, android.R.layout.simple_spinner_item);
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
        //adapterCats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
