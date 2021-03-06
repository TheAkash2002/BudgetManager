package com.princeakash.budgetmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_SHORT;
import static com.princeakash.budgetmanager.DatabaseHelper.DateToString;

public class ActivityAddTarget extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @BindView(R.id.editTarget)
    EditText editTarget;
    @BindView(R.id.spinnerMonth)
    Spinner spinnerMonth;
    @BindView(R.id.spinnerYear)
    Spinner spinnerYear;
    @BindView(R.id.buttonAddTarget)
    Button btnAddTarget;

    DatabaseHelper myDb;
    String targetYear;
    String targetMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_target);
        ButterKnife.bind(this);

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
                        if(myDb.isTargetSet(targetMonth, targetYear)==false) {
                            if(!inputValidations())
                                return;
                            boolean isInserted = myDb.insertTargetData(editTarget.getText().toString(), targetMonth, targetYear);
                            if (isInserted == true) {
                                Toast.makeText(ActivityAddTarget.this, "Target successfully set for " + DateToString(targetYear, targetMonth), LENGTH_SHORT).show();
                            }
                        }
                        else {
                            if(!inputValidations())
                                return;
                            EditTarget(v);
                        }
                    }
                }
        );
    }

    private void EditTarget(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Target Already Set")
                .setMessage("A target of Rs." + myDb.getTarget(targetMonth, targetYear) + " has already been set for " +
                        DateToString(targetYear, targetMonth) +". Do you want to update this target?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isUpdated = myDb.updateTargetData(targetMonth, targetYear, editTarget.getText().toString());
                        if(isUpdated)
                            Toast.makeText(ActivityAddTarget.this, "Target successfully updated for " + DateToString(targetYear, targetMonth), LENGTH_SHORT).show();
                        else
                            Toast.makeText(ActivityAddTarget.this, "Failed to update target.", LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ActivityAddTarget.this, "Target already set for " + DateToString(targetYear, targetMonth) + ", of Rs." + myDb.getTarget(targetMonth, targetYear), LENGTH_SHORT).show();
                    }
                });
        builder.create().show();
    }

    private boolean inputValidations() {
        if(editTarget.getText().toString().equals("")||Integer.parseInt(editTarget.getText().toString())<=0){
            editTarget.setError("Please enter a positive number.");
            editTarget.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
