package com.princeakash.budgetmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityAddTarget extends AppCompatActivity {

    EditText editTarget;
    Spinner spinnerMonth, spinnerYear;
    Button btnAddTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_target);
        editTarget = findViewById(R.id.editTarget);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear = findViewById(R.id.spinnerYear);
        btnAddTarget = findViewById(R.id.buttonAddTarget);
        btnAddTarget.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );
    }
}
