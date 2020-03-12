package com.princeakash.budgetmanager;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.util.ArrayList;
import java.util.List;

public class ActivityRelativeLineMonthly extends AppCompatActivity {
    String fromYear, fromMonth, toYear, toMonth;
    DatabaseHelper myDb;
    List<ChartEntry> chartEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_line);
        myDb = new DatabaseHelper(this);
        chartEntries = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        fromMonth = bundle.getString("FromMonth");
        fromYear = bundle.getString("FromYear");
        toMonth = bundle.getString("ToMonth");
        toYear = bundle.getString("ToYear");

        Monthly();

        AnyChartView anyChartView = findViewById(R.id.chartLine);

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Variation in monthly expenses from "+fromYear+"-"+fromMonth+" to "+toYear+"-"+toMonth);

        cartesian.yAxis(0).title("Money");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        for(int i=0; i<chartEntries.size(); i++){
            seriesData.add(new ValueDataEntry(chartEntries.get(i).getStr(), chartEntries.get(i).getAmount()));
        }

        /*seriesData.add(new ValueDataEntry("1986", 3.6));
        seriesData.add(new ValueDataEntry("1987", 6.6));
        seriesData.add(new ValueDataEntry("1988", 0.6));
        seriesData.add(new ValueDataEntry("1989", 12.6)); */

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Monthly");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    public String MonthConvert(int i){
        String conv="01";
        switch(i){
            case 1:
                conv = "01";
                break;
            case 2:
                conv = "02";
                break;
            case 3:
                conv = "03";
                break;
            case 4:
                conv = "04";
                break;
            case 5:
                conv = "05";
                break;
            case 6:
                conv = "06";
                break;
            case 7:
                conv = "07";
                break;
            case 8:
                conv = "08";
                break;
            case 9:
                conv = "09";
                break;
            case 10:
                conv = "10";
                break;
            case 11:
                conv = "11";
                break;
            case 12:
                conv = "12";
                break;
        }
        return conv;
    }

    public void Monthly(){
        Cursor cursor;
        String items;
        int firstNum = Integer.parseInt(fromYear+fromMonth);
        int lastNum = Integer.parseInt(toYear+toMonth);
        int currentNum;
        int val;
        for(int i = Integer.parseInt(fromYear); i<=Integer.parseInt(toYear); i++){
            Log.d("Hi", "Tag1");
            for(int j = 1; j<=12; j++){
                currentNum = Integer.parseInt(Integer.toString(i)+MonthConvert(j));
                if(currentNum == lastNum){
                    cursor = myDb.viewRelativeDataMonthly(toMonth, toYear);
                    items = Integer.toString(i) + "-" + MonthConvert(j);
                    if(cursor.getCount()!=0) {
                        Log.d("E", "FIRE");
                        cursor.moveToFirst();
                        val = cursor.getInt(0);
                        ChartEntry chartEntry = new ChartEntry(val, items);
                        chartEntries.add(chartEntry);
                    }
                    break;
                }
                else if (currentNum>=firstNum && currentNum<lastNum){
                    cursor = myDb.viewRelativeDataMonthly(MonthConvert(j), Integer.toString(i));
                    items = Integer.toString(i) + "-" + MonthConvert(j);
                    if(cursor.getCount()!=0) {
                        cursor.moveToFirst();
                        ChartEntry chartEntry = new ChartEntry(cursor.getInt(0), items);
                        chartEntries.add(chartEntry);
                    }
                }
            }
        }
    }

}
