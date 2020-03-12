package com.princeakash.budgetmanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityViewMonthlyExpensesPie extends AppCompatActivity {

    String eMonth, eYear;
    DatabaseHelper myDb;
    List<ChartEntry> chartEntries;

    @BindView(R.id.chartPie)
    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_monthly_expenses_pie);
        myDb = new DatabaseHelper(this);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        eMonth = bundle.getString("TargetMonth");
        eYear = bundle.getString("TargetYear");

        chartEntries = new ArrayList<>();
        Cursor cursor = myDb.viewMonthlyExpenseDataPie(eMonth, eYear);
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                ChartEntry chartEntry = new ChartEntry(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
                chartEntries.add(chartEntry);
            } while (cursor.moveToNext());
        }

        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(ActivityViewMonthlyExpensesPie.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data = new ArrayList<>();
        for(int i=0; i<cursor.getCount(); i++){
            data.add(new ValueDataEntry(chartEntries.get(i).getStr(), chartEntries.get(i).getAmount()));
        }

        pie.data(data);

        pie.title("Monthly expenditure in " + DateToString(eYear, eMonth));

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Categories")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }

    public String DateToString(String dateYear, String dateMonth){
        String res = "";
        switch(dateMonth){
            case "01":
                res+="January ";
                break;
            case "02":
                res+="February ";
                break;
            case "03":
                res+="March ";
                break;
            case "04":
                res+="April ";
                break;
            case "05":
                res+="May ";
                break;
            case "06":
                res+="June ";
                break;
            case "07":
                res+="July ";
                break;
            case "08":
                res+="August ";
                break;
            case "09":
                res+="September ";
                break;
            case "10":
                res+="October ";
                break;
            case "11":
                res+="November ";
                break;
            case "12":
                res+="December ";
                break;
        }
        res+=dateYear;
        return res;
    }
}
