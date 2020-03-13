package com.princeakash.budgetmanager;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.princeakash.budgetmanager.DatabaseHelper.DateToString;

public class ActivityViewMonthlyExpensesBar extends AppCompatActivity {

    String eMonth, eYear;
    DatabaseHelper myDb;
    List<ChartEntry> chartEntries;

    @BindView(R.id.chartBar)
    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_monthly_expenses_bar);
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

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        for(int i=0; i<cursor.getCount(); i++){
            data.add(new ValueDataEntry(chartEntries.get(i).getStr(), chartEntries.get(i).getAmount()));
        }

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("Rs.{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Monthly expenditure in " + DateToString(eYear, eMonth));

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("Rs.{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Categories");
        cartesian.yAxis(0).title("Money");

        anyChartView.setChart(cartesian);
    }
}
