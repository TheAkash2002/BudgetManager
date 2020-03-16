package com.princeakash.budgetmanager;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityViewMonthlyExpensesList extends AppCompatActivity implements MyAdapter.MyItemListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    DatabaseHelper myDb;
    String eMonth, eYear;

    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_monthly_expenses_list);

        Bundle bundle = getIntent().getExtras();
        eMonth = bundle.getString("TargetMonth");
        eYear = bundle.getString("TargetYear");


        ButterKnife.bind(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myDb = new DatabaseHelper(this);

        listItems = new ArrayList<>();

        Cursor cursor = myDb.viewMonthlyExpenseData(eMonth, eYear);
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            do {
                ListItem listItem = new ListItem(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                listItems.add(listItem);
            } while (cursor.moveToNext());
        }

        adapter = new MyAdapter(listItems, getApplicationContext(), this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClickListener(int position) {

    }

    @Override
    public void onItemLongClickListener(int position) {

    }
}
