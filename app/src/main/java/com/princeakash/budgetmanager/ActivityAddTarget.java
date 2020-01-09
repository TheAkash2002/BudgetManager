package com.princeakash.budgetmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.widget.Toast.LENGTH_SHORT;
import static java.lang.Integer.parseInt;

public class ActivityAddTarget extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editTarget;
    Spinner spinnerMonth, spinnerYear;
    Button btnAddTarget;
    DatabaseHelper myDb;
    String targetYear;
    String targetMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_target);
        editTarget = findViewById(R.id.editTarget);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear = findViewById(R.id.spinnerYear);
        btnAddTarget = findViewById(R.id.buttonAddTarget);

        myDb = new DatabaseHelper(this);

        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this, R.array.Months, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapterMonth);

        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this, R.array.Years, android.R.layout.simple_spinner_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);

        spinnerMonth.setOnItemSelectedListener(this);
        spinnerYear.setOnItemSelectedListener(this);

        btnAddTarget.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tMonth = spinnerMonth.getSelectedItem().toString();
                        targetYear = spinnerYear.getSelectedItem().toString();
                        switch (tMonth){
                            case "January": targetMonth = "01";
                                            break;
                            case "February": targetMonth = "02";
                                            break;
                            case "March": targetMonth = "03";
                                break;
                            case "April": targetMonth = "04";
                                break;
                            case "May": targetMonth = "05";
                                break;
                            case "June": targetMonth = "06";
                                break;
                            case "July": targetMonth = "07";
                                break;
                            case "August": targetMonth = "08";
                                break;
                            case "September": targetMonth = "09";
                                break;
                            case "October": targetMonth = "10";
                                break;
                            case "November": targetMonth = "11";
                                break;
                            case "December": targetMonth = "12";
                                break;
                        }
                        //Toast.makeText(ActivityAddTarget.this, targetYear + "-" + targetMonth, LENGTH_SHORT).show();
                        if(myDb.isTargetSet(targetMonth, targetYear)==false) {
                            boolean isInserted = myDb.insertTargetData(editTarget.getText().toString(), targetMonth, targetYear);
                            if (isInserted == true) {
                                Toast.makeText(ActivityAddTarget.this, "Target successfully set for " + targetYear + "-" + targetMonth, LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(ActivityAddTarget.this, "Target already set for this month! Target data not inserted.", LENGTH_SHORT).show();

                    }
                }
        );
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
