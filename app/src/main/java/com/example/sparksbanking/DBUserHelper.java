package com.example.sparksbanking;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBUserHelper extends SQLiteOpenHelper {

    String TABLE_NAME = DBUserContent.UserEntry.TABLE_NAME;

    private static final String DATABASE_NAME = "User.db";
    private static final int DATABASE_VERSION = 1;

    public DBUserHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USER_TABLE =  "CREATE TABLE " + DBUserContent.UserEntry.TABLE_NAME + " ("
                + DBUserContent.UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " INTEGER, "
                + DBUserContent.UserEntry.COLUMN_USER_NAME + " VARCHAR, "
                + DBUserContent.UserEntry.COLUMN_USER_EMAIL + " VARCHAR, "
                + DBUserContent.UserEntry.COLUMN_USER_IFSC_CODE + " VARCHAR, "
                + DBUserContent.UserEntry.COLUMN_USER_PHONE_NO + " VARCHAR, "
                + DBUserContent.UserEntry.COLUMN_USER_ACCOUNT_BALANCE + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_USER_TABLE);

        db.execSQL("insert into " + TABLE_NAME + " values(0001,'Aniket Shinde', 'aniket@gmail.com','1234','1735497620', 10000)");
        db.execSQL("insert into " + TABLE_NAME + " values(0002,'Smitesh Patil', 'smitesh@gmail.com','1235','9836982584', 10000)");
        db.execSQL("insert into " + TABLE_NAME + " values(0003,'Rohan Kumbhar', 'rohan@gmail.com','1235','9578340989', 10000)");
        db.execSQL("insert into " + TABLE_NAME + " values(0004,'Jittendra Pawar', 'jittendra@gmail.com','1534','6598770093', 10000)");
        db.execSQL("insert into " + TABLE_NAME + " values(0005,'Sakshi Jadhav', 'sakshi@gmail.com','1254','4499775590', 10000)");
        db.execSQL("insert into " + TABLE_NAME + " values(0006,'Rohan Nichal', 'rohan@gmail.com','1254','8955993322', 10000)");
        db.execSQL("insert into " + TABLE_NAME + " values(0007,'Yashraj Chavan', 'yashraj@gmail.com','4234','1177663355', 10000)");
        db.execSQL("insert into " + TABLE_NAME + " values(0008,'Prakash Pukale', 'prakash@gmail.com','1264','7687453423', 10000)");
        db.execSQL("insert into " + TABLE_NAME + " values(0009,'Shubham Gilbile', 'shubham@gmail.com','1334','9867453423', 10000)");
        db.execSQL("insert into " + TABLE_NAME + " values(0010,'Sanket Shiktode', 'sanket@gmail.com','1264','5634576986', 10000)");
        db.execSQL("insert into " + TABLE_NAME + " values(0012,'Siddhesh Khadake', 'siddhesh@gmail.com','6234','9823764554', 10000)");
        db.execSQL("insert into " + TABLE_NAME + " values(0013,'Rushikesh Korade', 'rushikesh@gmail.com','3234','1287340977', 10000)");
        db.execSQL("insert into " + TABLE_NAME + " values(0014,'Tejas Chumbalkar', 'tejas@gmail.com','1224','2309871234', 10000)");
        db.execSQL("insert into " + TABLE_NAME + " values(0015,'Ankit Tiwari', 'ankit@gmail.com','1134','7612345098', 10000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DBUserContent.UserEntry.TABLE_NAME);
            onCreate(db);
        }
    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBUserContent.UserEntry.TABLE_NAME, null);
        return cursor;
    }

    public Cursor readParticularData (int accountNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBUserContent.UserEntry.TABLE_NAME + " where " +
                                        DBUserContent.UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " = " + accountNo, null);
        return cursor;
    }

    public void updateAmount(int accountNo, int amount) {
        Log.d ("TAG", "update Amount");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update " + DBUserContent.UserEntry.TABLE_NAME + " set " + DBUserContent.UserEntry.COLUMN_USER_ACCOUNT_BALANCE + " = " + amount + " where " +
                DBUserContent.UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " = " + accountNo);
    }
}