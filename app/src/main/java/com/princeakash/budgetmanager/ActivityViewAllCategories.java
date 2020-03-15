package com.princeakash.budgetmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityViewAllCategories extends AppCompatActivity {

    @BindView(R.id.recyclerViewTarget)
    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;
    DatabaseHelper myDb;
    List<String> categoryItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_categories);
        ButterKnife.bind(this);
        myDb = new DatabaseHelper(this);
        categoryItems = new ArrayList<>();

        getCategoryItems();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(categoryItems, this);
        recyclerView.setAdapter(adapter);
    }

    public void getCategoryItems(){
        Cursor cursor = myDb.viewAllCategoryData();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                String categoryItem = cursor.getString(1);
                categoryItems.add(categoryItem);
            } while(cursor.moveToNext());
        }
    }
}
