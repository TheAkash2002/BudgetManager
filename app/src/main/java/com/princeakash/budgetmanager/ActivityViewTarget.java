package com.princeakash.budgetmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityViewTarget extends AppCompatActivity implements MyAdapter.MyItemListener, EditTargetDialogFragment.EditTargetDialogListener {

    @BindView(R.id.recyclerViewTarget)
    RecyclerView recyclerView;
    @BindView(R.id.fabTarget)
    FloatingActionButton floatingActionButtonTarget;
    RecyclerView.Adapter adapter;
    DatabaseHelper myDb;
    List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_target);
        ButterKnife.bind(this);

        myDb = new DatabaseHelper(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getTargetItems();
        populateRecyclerView();

        floatingActionButtonTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityAddTarget.class);
                startActivity(intent);
            }
        });
    }

    private void getTargetItems() {
        listItems = new ArrayList<>();
        Cursor cursor = myDb.getAllTargets();
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            do {
                ListItem listItem = new ListItem(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                listItems.add(listItem);
            } while (cursor.moveToNext());
        }
    }

    private void populateRecyclerView() {
        adapter = new MyAdapter(listItems, getApplicationContext(), this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClickListener(int position) {
        DialogFragment newFragment = new EditTargetDialogFragment(position, listItems.get(position).getAmount());
        newFragment.show(getSupportFragmentManager(), "EditTargetDialogFragment");
    }

    @Override
    public void onItemLongClickListener(final int position) {
        String[] splitDate = listItems.get(position).getDate().split("-");
        final String wordDate = DatabaseHelper.DateToString(splitDate[0], splitDate[1]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Target")
                .setMessage("Are you sure you want to delete the set target for " + wordDate + "? You cannot undo this later.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isDeleted = myDb.deleteTarget(listItems.get(position).getId());
                        if(isDeleted){
                            Toast.makeText(ActivityViewTarget.this, "Target for " + wordDate + " deleted successfully.", Toast.LENGTH_SHORT).show();
                            getTargetItems();
                            populateRecyclerView();
                        }
                        else{
                            Toast.makeText(ActivityViewTarget.this, "Failed to delete target", Toast.LENGTH_SHORT).show();
                        }
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
    public void onDialogPositiveClick(DialogFragment dialog, String newTarget, int position) {
        boolean isUpdated = myDb.updateTargetData(listItems.get(position).getId(), newTarget, listItems.get(position).getCategory(), listItems.get(position).getDate());
        if(isUpdated){
            Toast.makeText(ActivityViewTarget.this, "Target updated successfully.", Toast.LENGTH_SHORT).show();
            getTargetItems();
            populateRecyclerView();
        }
        else{
            Toast.makeText(ActivityViewTarget.this, "Failed to update target.", Toast.LENGTH_SHORT).show();
        }
    }
}
