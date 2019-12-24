package com.princeakash.budgetmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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

    public DatabaseHelper(@Nullable Context context) {
        super(context, dbName, null, 33);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + expenseTable + " ("+colExpenseID+" INTEGER PRIMARY KEY, "+colExpenseAmount+" INTEGER, "+colExpenseCategory+" TEXT, "+colExpenseDate+" DATE)");
        db.execSQL("CREATE TABLE " + categoryTable + " ("+colCategoryID+" INTEGER PRIMARY KEY, "+colCategoryName+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+expenseTable);
        db.execSQL("DROP TABLE IF EXISTS "+categoryTable);
        onCreate(db);
    }
}
