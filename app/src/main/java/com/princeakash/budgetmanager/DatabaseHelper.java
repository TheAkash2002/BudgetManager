package com.princeakash.budgetmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String dbName = "budgetDB";

    public static final String expenseTable = "expense_table";
    public static final String colExpenseID = "ExpenseID";
    public static final String colExpenseAmount = "Amount";
    public static final String colExpenseCategory = "Category";
    public static final String colExpenseDate = "Date";

    public static final String categoryTable = "category_table";
    public static final String colCategoryID = "CategoryID";
    public static final String colCategoryName = "Name";

    Calendar calendar;

    public DatabaseHelper(@Nullable Context context) {
        super(context, dbName, null, 33);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + expenseTable + " ("
                +colExpenseID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +colExpenseAmount+" INTEGER, "
                +colExpenseCategory+" TEXT, "
                +colExpenseDate+" DATE)"
        );
        db.execSQL("CREATE TABLE " + categoryTable + " ("
                +colCategoryID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +colCategoryName+" TEXT)"
        );

        ContentValues contentValues = new ContentValues();
        long insert;

        if(db.rawQuery("SELECT * FROM " + categoryTable + " WHERE " + colCategoryName + " = ?", new String[] {"Books And Stationery"}).getCount()==0){
            Log.d("B&S", "Added B&S");
            contentValues.clear();
            contentValues.put(colCategoryName, "Books And Stationery");
            insert = db.insert(categoryTable, colCategoryID, contentValues);
        }
        if(db.rawQuery("SELECT * FROM " + categoryTable + " WHERE " + colCategoryName + " = ?", new String[] {"Food"}).getCount()==0){
            contentValues.clear();
            contentValues.put(colCategoryName, "Food");
            insert = db.insert(categoryTable, colCategoryID, contentValues);
        }
        if(db.rawQuery("SELECT * FROM " + categoryTable + " WHERE " + colCategoryName + " = ?", new String[] {"Cloth Wash"}).getCount()==0){
            contentValues.clear();
            contentValues.put(colCategoryName, "Cloth Wash");
            insert = db.insert(categoryTable, colCategoryID, contentValues);
        }
        if(db.rawQuery("SELECT * FROM " + categoryTable + " WHERE " + colCategoryName + " = ?", new String[] {"Recharge"}).getCount()==0){
            contentValues.clear();
            contentValues.put(colCategoryName, "Recharge");
            insert = db.insert(categoryTable, colCategoryID, contentValues);
        }
        if(db.rawQuery("SELECT * FROM " + categoryTable + " WHERE " + colCategoryName + " = ?", new String[] {"Party"}).getCount()==0){
            contentValues.clear();
            contentValues.put(colCategoryName, "Party");
            insert = db.insert(categoryTable, colCategoryID, contentValues);
        }
        if(db.rawQuery("SELECT * FROM " + categoryTable + " WHERE " + colCategoryName + " = ?", new String[] {"Party"}).getCount()==0){
            contentValues.clear();
            contentValues.put(colCategoryName, "Target");
            insert = db.insert(categoryTable, colCategoryID, contentValues);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+expenseTable);
        db.execSQL("DROP TABLE IF EXISTS "+categoryTable);
        onCreate(db);
    }

    public boolean insertCategory(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colCategoryName, category);
        long insert = db.insert(categoryTable, colCategoryID, contentValues);
        return !(insert==-1);
    }

    public boolean insertExpenseData(String category, String amount){
        SQLiteDatabase db = this.getWritableDatabase();

        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(calendar.getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(colExpenseAmount, amount);
        contentValues.put(colExpenseCategory, category);
        contentValues.put(colExpenseDate, date);
        long insert = db.insert(expenseTable, colExpenseID, contentValues);
        if(insert == -1)
            return false;
        else
            return true;
    }

    /*
    public Integer deleteExpenseData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer id2 = id;
        return db.delete(expenseTable, "ID = ?", new String[] {id2.toString()});
    }
    */

    public void updateExpenseData(){

    }

    public Cursor viewAllExpenseData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + expenseTable + " ORDER BY " + colExpenseDate + " DESC", null);
        return res;
    }

    public Cursor viewMonthlyExpenseData(String month, String year){
        SQLiteDatabase db = this.getReadableDatabase();
        String startDate = year + "-" + month + "-00";
        String endDate = year + "-" + month + "-31";
        Cursor res = db.rawQuery("SELECT * FROM " + expenseTable + " WHERE " + colExpenseDate + " BETWEEN ? AND ?", new String[] {startDate, endDate});
        return res;
    }

    public Cursor viewMonthlyExpenseDataPie(String month, String year){
        SQLiteDatabase db = this.getReadableDatabase();
        String startDate = year + "-" + month + "-00";
        String endDate = year + "-" + month + "-31";
        Cursor res = db.rawQuery("SELECT SUM(" + colExpenseAmount + "), " + colExpenseCategory + " FROM " + expenseTable + " WHERE " + colExpenseDate + " BETWEEN ? AND ? GROUP BY " + colExpenseCategory, new String[] {startDate, endDate});
        return res;
    }

    public boolean insertTargetData(String amount, String month, String year){
        SQLiteDatabase db = this.getWritableDatabase();

        String date = year + "-" + month + "-01";

        ContentValues contentValues = new ContentValues();
        contentValues.put(colExpenseAmount, amount);
        contentValues.put(colExpenseCategory, "Target");
        contentValues.put(colExpenseDate, date);
        long insert = db.insert(expenseTable, colExpenseID, contentValues);
        if(insert == -1)
            return false;
        else
            return true;
    }

    public Cursor viewAllCategoryData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + categoryTable, null);
        return res;
    }

    public Cursor viewRelativeDataMonthly(String startMonth, String startYear, String endMonth, String endYear){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + expenseTable + " ORDER BY " + colExpenseDate + " DESC", null);
        return res;
    }


}
