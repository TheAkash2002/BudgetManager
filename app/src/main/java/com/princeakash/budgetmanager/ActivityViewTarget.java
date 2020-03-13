package com.princeakash.budgetmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityViewTarget extends AppCompatActivity {

    @BindView(R.id.recyclerViewTarget)
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    DatabaseHelper myDb;
    List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_target);
        ButterKnife.bind(this);

        myDb = new DatabaseHelper(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        Cursor cursor = myDb.getAllTargets();
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            do {
                ListItem listItem = new ListItem(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                listItems.add(listItem);
            } while (cursor.moveToNext());
        }
        adapter = new MyAdapter(listItems, this);

        recyclerView.setAdapter(adapter);
    }

}
