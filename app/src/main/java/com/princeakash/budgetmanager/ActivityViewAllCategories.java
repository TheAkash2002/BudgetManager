package com.princeakash.budgetmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_SHORT;

public class ActivityViewAllCategories extends AppCompatActivity implements CategoryAdapter.OnCategoryListener, EditCategoryDialogFragment.EditCategoryDialogListener, AddCategoryDialogFragment.AddCategoryDialogListener {

    @BindView(R.id.recyclerViewTarget)
    RecyclerView recyclerView;
    @BindView(R.id.fabCat)
    FloatingActionButton floatingActionButtonCategory;
    RecyclerView.Adapter adapter;
    DatabaseHelper myDb;
    List<String> categoryItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_categories);
        ButterKnife.bind(this);
        myDb = new DatabaseHelper(this);

        getCategoryItems();

        populateRecyclerView();

        floatingActionButtonCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new AddCategoryDialogFragment();
                newFragment.show(getSupportFragmentManager(), "AddCategoryDialogFragment");
            }
        });
    }

    public void populateRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(categoryItems, getApplicationContext(), this);
        recyclerView.setAdapter(adapter);
    }

    public void getCategoryItems(){
        categoryItems = new ArrayList<>();
        Cursor cursor = myDb.viewAllCategoryData();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                String categoryItem = cursor.getString(1);
                categoryItems.add(categoryItem);
            } while(cursor.moveToNext());
        }
    }

    @Override
    public void onCategoryClick(int position) {
        DialogFragment newFragment = new EditCategoryDialogFragment(categoryItems.get(position));
        newFragment.show(getSupportFragmentManager(), "EditCategoryDialogFragment");
    }

    @Override
    public void onCategoryLongClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete " + categoryItems.get(position) + "? You cannot undo this later.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cat = categoryItems.get(position);
                        if(myDb.deleteCategory(cat)) {
                            Toast.makeText(getApplicationContext(), cat + " was deleted successfully.", Toast.LENGTH_SHORT).show();
                            getCategoryItems();
                            populateRecyclerView();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Failed to delete " + cat, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, final String oldCategoryName, final String newCategoryName) {
        boolean isUpdated = myDb.updateCategory(oldCategoryName, newCategoryName);
        if(isUpdated){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Rename Category in Old Expenses")
                    .setMessage("Do you want the category rename to reflect in all old expenses?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(myDb.renameExpenseCategories(oldCategoryName, newCategoryName))
                                Toast.makeText(getApplicationContext(), "Old Expenses renamed successfully.", LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(), "Old expenses not renamed.", LENGTH_SHORT);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), oldCategoryName + " was successfully renamed to " + newCategoryName, Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.create().show();
            getCategoryItems();
            populateRecyclerView();
        }
        else
            Toast.makeText(this, "Rename operation not successful.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String categoryName) {
        boolean isInserted = myDb.insertCategory(categoryName);
        if(isInserted){
            Toast.makeText(this, "Inserted new category successfully.", LENGTH_SHORT).show();
            getCategoryItems();
            populateRecyclerView();
        }
        else{
            Toast.makeText(this, "Failed to insert new category.", LENGTH_SHORT).show();
        }
    }
}
