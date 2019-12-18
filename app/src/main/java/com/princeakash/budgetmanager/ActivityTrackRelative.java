package com.princeakash.budgetmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityTrackRelative extends AppCompatActivity {

    Button buttonCategorywise, buttonMonthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_relative);
        buttonCategorywise = findViewById(R.id.buttonCategory);
        buttonMonthly = findViewById(R.id.buttonMonthly);
        buttonCategorywise.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityTrackRelative.this, ActivityRelativeCategorywise.class);
                        startActivity(intent);
                    }
                }
        );
        buttonMonthly.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityTrackRelative.this, ActivityRelativeMonthly.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
