package com.example.sparksbanking;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTransactionHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "transaction.db";

    private static final int DATABASE_VERSION = 1;

    public DBTransactionHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TRANSACTION_TABLE =  "CREATE TABLE " + DBTransactionContent.TransactionEntry.TABLE_NAME + " ("
                + DBTransactionContent.TransactionEntry.COLUMN_FROM_NAME + " VARCHAR, "
                + DBTransactionContent.TransactionEntry.COLUMN_TO_NAME + " VARCHAR, "
                + DBTransactionContent.TransactionEntry.COLUMN_AMOUNT + " INTEGER, "
                + DBTransactionContent.TransactionEntry.COLUMN_STATUS + " INTEGER);";

        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DBTransactionContent.TransactionEntry.TABLE_NAME);
            onCreate(db);
        }
    }

    public boolean insertTransferData (String fromName, String toName, String amount, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBTransactionContent.TransactionEntry.COLUMN_FROM_NAME, fromName);
        contentValues.put(DBTransactionContent.TransactionEntry.COLUMN_TO_NAME, toName);
        contentValues.put(DBTransactionContent.TransactionEntry.COLUMN_AMOUNT, amount);
        contentValues.put(DBTransactionContent.TransactionEntry.COLUMN_STATUS, status);
        Long result = db.insert(DBTransactionContent.TransactionEntry.TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
}
