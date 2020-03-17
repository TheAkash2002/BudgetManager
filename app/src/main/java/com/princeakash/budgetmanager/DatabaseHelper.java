package com.princeakash.budgetmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    @SuppressWarnings("UnusedAssignment")
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
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT * FROM " + categoryTable + " WHERE " + colCategoryName + " = ?", new String[]{category});
        if(!(cursor.getCount()==0))
            return false;
        ContentValues contentValues = new ContentValues();
        contentValues.put(colCategoryName, category);
        long insert = db.insert(categoryTable, colCategoryID, contentValues);
        return !(insert==-1);
    }

    public Cursor viewAllCategoryData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + categoryTable + " WHERE " + colCategoryName + "<> ?", new String[]{"Target"});
        return res;
    }

    public boolean updateCategory(String categoryOld, String categoryNew){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colCategoryName, categoryNew);
        long update = db.update(categoryTable, contentValues, colCategoryName + " = ?", new String[]{categoryOld});
        return !(update==-1);
    }

    public boolean deleteCategory(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        return !(db.delete(categoryTable, colCategoryName + " = ?", new String[]{category})==-1);
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

    public Boolean isTargetSet(String month, String year){
        SQLiteDatabase db = this.getReadableDatabase();
        String startDate = year + "-" + month + "-00";
        String endDate = year + "-" + month + "-31";
        Cursor res = db.rawQuery("SELECT * FROM " + expenseTable + " WHERE " + colExpenseCategory + " = ? AND "+ colExpenseDate + " BETWEEN ? AND ?", new String[] {"Target", startDate, endDate});
        res.moveToFirst();
        return res.getCount() != 0;
    }

    public int getTarget(String month, String year){
        SQLiteDatabase db = this.getReadableDatabase();
        String startDate = year + "-" + month + "-00";
        String endDate = year + "-" + month + "-31";
        Cursor res = db.rawQuery("SELECT SUM(" + colExpenseAmount + ") FROM " + expenseTable + " WHERE " + colExpenseCategory + " = ? AND "+ colExpenseDate + " BETWEEN ? AND ?", new String[] {"Target", startDate, endDate});
        res.moveToFirst();
        return res.getInt(0);
    }

    public Cursor getAllTargets(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + expenseTable + " WHERE " + colExpenseCategory + " = ?", new String[] {"Target"});
        return res;
    }

    public Boolean updateTargetData(String id, String newTarget, String category, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colExpenseAmount, newTarget);
        contentValues.put(colExpenseCategory, category);
        contentValues.put(colExpenseDate, date);
        long result = db.update(expenseTable, contentValues, colExpenseID + " = ?", new String[]{id});
        return !(result==-1);
    }

    public Boolean deleteTarget(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(expenseTable, colExpenseID + " = ?", new String[]{id});
        return !(result==-1);
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

    public boolean updateExpenseData(String id, String newCategory, String newAmount, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colExpenseDate, date);
        contentValues.put(colExpenseCategory, newCategory);
        contentValues.put(colExpenseAmount, newAmount);
        long update = db.update(expenseTable, contentValues, colExpenseID + " = ?", new String[]{id});
        return !(update==-1);
    }

    public Cursor viewAllExpenseData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + expenseTable + " WHERE " + colExpenseCategory + " <> ? ORDER BY " + colExpenseDate + " DESC", new String[] {"Target"});
        return res;
    }

    public Cursor viewMonthlyExpenseData(String month, String year){
        SQLiteDatabase db = this.getReadableDatabase();
        String startDate = year + "-" + month + "-00";
        String endDate = year + "-" + month + "-31";
        Cursor res = db.rawQuery("SELECT * FROM " + expenseTable + " WHERE " + colExpenseCategory + " <> ? AND " + colExpenseDate + " BETWEEN ? AND ?", new String[] {"Target", startDate, endDate});
        return res;
    }

    public Cursor viewMonthlyExpenseDataPie(String month, String year){
        SQLiteDatabase db = this.getReadableDatabase();
        String startDate = year + "-" + month + "-00";
        String endDate = year + "-" + month + "-31";
        Cursor res = db.rawQuery("SELECT SUM(" + colExpenseAmount + "), " + colExpenseCategory + " FROM " + expenseTable + " WHERE " + colExpenseCategory + " <> ? AND " + colExpenseDate + " BETWEEN ? AND ? GROUP BY " + colExpenseCategory, new String[] {"Target", startDate, endDate});
        return res;
    }

    public Cursor viewRelativeDataMonthly(String month, String year){
        SQLiteDatabase db = this.getReadableDatabase();
        String startDate = year + "-" + month + "-00";
        String endDate = year + "-" + month + "-31";
        Cursor res = db.rawQuery("SELECT SUM(" + colExpenseAmount + ") FROM " + expenseTable + " WHERE " + colExpenseCategory + " <> ? AND " + colExpenseDate + " BETWEEN ? AND ?", new String[] {"Target", startDate, endDate});
        return res;
    }

    public Cursor viewRelativeDataCategoryWise(String month, String year, String category){
        SQLiteDatabase db = this.getReadableDatabase();
        String startDate = year + "-" + month + "-00";
        String endDate = year + "-" + month + "-31";
        Cursor res = db.rawQuery("SELECT SUM(" + colExpenseAmount + ") FROM " + expenseTable + " WHERE " + colExpenseCategory + " = ? AND "+ colExpenseDate + " BETWEEN ? AND ?", new String[] {category, startDate, endDate});
        return res;
    }


    public int getTotalExpensesTillNow(String month, String year){
        SQLiteDatabase db = this.getReadableDatabase();
        String startDate = year + "-" + month + "-00";
        String endDate = year + "-" + month + "-31";
        Cursor res = db.rawQuery("SELECT SUM(" + colExpenseAmount + ") FROM " + expenseTable + " WHERE " + colExpenseCategory + " <> ? AND "+ colExpenseDate + " BETWEEN ? AND ?", new String[] {"Target", startDate, endDate});
        res.moveToFirst();
        return res.getInt(0);
    }

    public boolean deleteExpenseData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long delete = db.delete(expenseTable, colExpenseID + " = ?", new String[]{id});
        return !(delete==-1);
    }

    public static String DateToString(String dateYear, String dateMonth){
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
