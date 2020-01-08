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

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static java.lang.Integer.parseInt;

public class ActivityAddExpense extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerCategory;
    Button btnAddExpense;
    EditText editAmount;
    String selectedCategory;
    DatabaseHelper myDb;

    private List<CategoryItem> categoryItems;

    private BaseAdapter categoryAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return categoryItems.size();
        }

        @Override
        public Object getItem(int position) {
            return categoryItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CategoryHolder holder = new CategoryHolder();
            View categoryView = convertView;
            if(categoryView==null){
                categoryView = getLayoutInflater().inflate(R.layout.row_category_spinner, parent, false);
                holder = new CategoryHolder();
                holder.textViewCategory = categoryView.findViewById(R.id.textViewCat);
                categoryView.setTag(holder);
            }
            else{
                holder = (CategoryHolder) categoryView.getTag();
            }

            CategoryItem categoryItem = categoryItems.get(position);
            holder.textViewCategory.setText(categoryItem.getCategoryName());

            return categoryView;
        }

        class CategoryHolder{
            private TextView textViewCategory;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);
        myDb = new DatabaseHelper(this);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnAddExpense = findViewById(R.id.buttonAddExpense);
        editAmount = findViewById(R.id.editAmount);
        btnAddExpense.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertExpenseData(selectedCategory, editAmount.getText().toString());
                        if(isInserted == true)
                            Toast.makeText(ActivityAddExpense.this, "Data Inserted", LENGTH_SHORT).show();
                    }
                }
        );

        /*spinnerCategory.setAdapter(categoryAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ActivityAddExpense.this, categoryItems.get(position).getCategoryName(), LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         */
        ArrayAdapter<CharSequence> adapterCats = ArrayAdapter.createFromResource(this, R.array.Cats, android.R.layout.simple_spinner_item);
        adapterCats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCats);
        spinnerCategory.setOnItemSelectedListener(this);
    }

    private void populateCategoryList(){
        categoryItems = new ArrayList<>();
        //categoryItems = myDb.viewAllCategoryData();
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
}
