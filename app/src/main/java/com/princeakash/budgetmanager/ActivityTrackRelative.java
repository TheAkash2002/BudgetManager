package com.princeakash.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityTrackRelative extends AppCompatActivity {

    @BindView(R.id.buttonCategory)
    Button buttonCategorywise;
    @BindView(R.id.buttonMonthly)
    Button buttonMonthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_relative);
        ButterKnife.bind(this);
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
