package id.ac.ukdw.project2_71200592;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ExpenseManager.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_EXPENSE = "expense";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_KETERANGAN = "keterangan";
    private static final String COLUMN_NOMINAL = "nominal";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_EXPENSE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KETERANGAN + " TEXT, " +
                COLUMN_NOMINAL + " REAL)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_EXPENSE;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public long addExpense(Expense expense) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KETERANGAN, expense.getKeterangan());
        values.put(COLUMN_NOMINAL, expense.getNominal());
        long id = db.insert(TABLE_EXPENSE, null, values);
        db.close();
        return id;
    }

    public int updateExpense(Expense expense) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KETERANGAN, expense.getKeterangan());
        values.put(COLUMN_NOMINAL, expense.getNominal());
        int rowsAffected = db.update(TABLE_EXPENSE, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(expense.getId())});
        db.close();
        return rowsAffected;
    }

    public int deleteExpense(Expense expense) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = db.delete(TABLE_EXPENSE, COLUMN_ID + " = ?",
                new String[]{String.valueOf(expense.getId())});
        db.close();
        return rowsAffected;
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EXPENSE + " ORDER BY " + COLUMN_ID + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String keterangan = cursor.getString(cursor.getColumnIndex(COLUMN_KETERANGAN));
                @SuppressLint("Range") double nominal = cursor.getDouble(cursor.getColumnIndex(COLUMN_NOMINAL));
                Expense expense = new Expense(id, keterangan, nominal);
                expenses.add(expense);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return expenses;
    }
}
