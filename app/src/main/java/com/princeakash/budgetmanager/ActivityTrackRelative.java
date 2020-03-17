package com.princeakash.budgetmanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityTrackRelative extends AppCompatActivity {

    @BindView(R.id.buttonCategory)
    Button buttonCategorywise;
    @BindView(R.id.buttonMonthly)
    Button buttonMonthly;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_relative);
        ButterKnife.bind(this);
        myDb = new DatabaseHelper(this);

        buttonCategorywise.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!validations()){
                            Toast.makeText(ActivityTrackRelative.this, "No categories found for analysis. Please add categories first.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!validationMonthly()){
                            Toast.makeText(ActivityTrackRelative.this, "No expense data found.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent = new Intent(ActivityTrackRelative.this, ActivityRelativeCategorywise.class);
                        startActivity(intent);
                    }
                }
        );
        buttonMonthly.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!validationMonthly()){
                            Toast.makeText(ActivityTrackRelative.this, "No expense data found.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent = new Intent(ActivityTrackRelative.this, ActivityRelativeMonthly.class);
                        startActivity(intent);
                    }
                }
        );
    }

    private boolean validationMonthly() {
        return myDb.viewAllExpenseData().getCount()!=0;
    }

    private boolean validations(){
        return (myDb.getCategoriesfromExpense().getCount()!=0);
    }

}
