package com.princeakash.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class ActivityRelativeCategorywise extends AppCompatActivity {

    @BindView(R.id.spinnerFromMonth)
    Spinner spinnerFromMonth;
    @BindView(R.id.spinnerFromYear)
    Spinner spinnerFromYear;
    @BindView(R.id.spinnerToMonth)
    Spinner spinnerToMonth;
    @BindView(R.id.spinnerToYear)
    Spinner spinnerToYear;
    @BindView(R.id.spinnerCategory)
    Spinner spinnerCategory;
    @BindView(R.id.buttonSeeData)
    Button btnSeeData;
    String fromYear, fromMonth, toYear, toMonth, selectedCategory;

    public void TextToNum(){
        selectedCategory = spinnerCategory.getSelectedItem().toString();
        String fMonth = spinnerFromMonth.getSelectedItem().toString();
        String tMonth = spinnerToMonth.getSelectedItem().toString();
        fromYear = spinnerFromYear.getSelectedItem().toString();
        toYear = spinnerToYear.getSelectedItem().toString();
        switch (fMonth){
            case "January": fromMonth = "01";
                break;
            case "February": fromMonth = "02";
                break;
            case "March": fromMonth = "03";
                break;
            case "April": fromMonth = "04";
                break;
            case "May": fromMonth = "05";
                break;
            case "June": fromMonth = "06";
                break;
            case "July": fromMonth = "07";
                break;
            case "August": fromMonth = "08";
                break;
            case "September": fromMonth = "09";
                break;
            case "October": fromMonth = "10";
                break;
            case "November": fromMonth = "11";
                break;
            case "December": fromMonth = "12";
                break;
        }
        switch (tMonth){
            case "January": toMonth = "01";
                break;
            case "February": toMonth = "02";
                break;
            case "March": toMonth = "03";
                break;
            case "April": toMonth = "04";
                break;
            case "May": toMonth = "05";
                break;
            case "June": toMonth = "06";
                break;
            case "July": toMonth = "07";
                break;
            case "August": toMonth = "08";
                break;
            case "September": toMonth = "09";
                break;
            case "October": toMonth = "10";
                break;
            case "November": toMonth = "11";
                break;
            case "December": toMonth = "12";
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_categorywise);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this, R.array.Months, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromMonth.setAdapter(adapterMonth);
        spinnerToMonth.setAdapter(adapterMonth);

        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this, R.array.Years, android.R.layout.simple_spinner_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromYear.setAdapter(adapterYear);
        spinnerToYear.setAdapter(adapterYear);

        ArrayAdapter<CharSequence> adapterCats = ArrayAdapter.createFromResource(this, R.array.Cats, android.R.layout.simple_spinner_item);
        adapterCats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCats);

        btnSeeData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextToNum();
                        Intent intent = new Intent(ActivityRelativeCategorywise.this, ActivityRelativeLineCategorywise.class);
                        intent.putExtra("SelectedCategory", selectedCategory);
                        intent.putExtra("FromMonth", fromMonth);
                        intent.putExtra("FromYear", fromYear);
                        intent.putExtra("ToMonth", toMonth);
                        intent.putExtra("ToYear", toYear);
                        startActivity(intent);
                    }
                }
        );
    }
}
