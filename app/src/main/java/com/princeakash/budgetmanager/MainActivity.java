package com.princeakash.budgetmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MyAdapter.MyItemListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        myDb = new DatabaseHelper(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityAddExpense.class);
                startActivity(intent);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch(menuItem.getItemId())
                {
                    case R.id.addTarget:
                        intent = new Intent(MainActivity.this, ActivityAddTarget.class);
                        startActivity(intent);
                        break;
                    case R.id.viewTarget:
                        intent = new Intent(MainActivity.this, ActivityViewTarget.class);
                        startActivity(intent);
                        break;
                    case R.id.viewAllCat:
                        intent = new Intent(MainActivity.this, ActivityViewAllCategories.class);
                        startActivity(intent);
                        break;
                    case R.id.viewMonthly:
                        intent = new Intent(MainActivity.this, ActivityViewMonthlyExpenses.class);
                        startActivity(intent);
                        break;
                    case R.id.trackRelative:
                        intent = new Intent(MainActivity.this, ActivityTrackRelative.class);
                        startActivity(intent);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }
                return false;
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getExpenseItems();
        populateRecyclerView();

    }

    private void getExpenseItems() {
        listItems = new ArrayList<>();
        Cursor cursor = myDb.viewAllExpenseData();
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            do {
                ListItem listItem = new ListItem(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                listItems.add(listItem);
            } while (cursor.moveToNext());
        }
    }

    private void populateRecyclerView(){
        adapter = new MyAdapter(listItems, getApplicationContext(), this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClickListener(int position) {
        Intent intent = new Intent(MainActivity.this, ActivityEditExpense.class);
        intent.putExtra("expenseID", listItems.get(position).getId());
        intent.putExtra("expenseDate", listItems.get(position).getDate());
        intent.putExtra("expenseAmount", listItems.get(position).getAmount());
        startActivity(intent);
    }

    @Override
    public void onItemLongClickListener(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Expense")
                .setMessage("Are you sure you want to delete this expense? You cannot undo this later.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isDeleted = myDb.deleteExpenseData(listItems.get(position).getId());
                        if(isDeleted){
                            Toast.makeText(MainActivity.this, "Expense deleted successfully.", Toast.LENGTH_SHORT).show();
                            getExpenseItems();
                            populateRecyclerView();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Failed to delete expense.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }
}