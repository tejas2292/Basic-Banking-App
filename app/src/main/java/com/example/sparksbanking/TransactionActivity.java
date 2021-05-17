package com.example.sparksbanking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<UploadTransaction> transactionArrayList;
    private DBTransactionHelper dbHelper;
    TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        emptyList = findViewById(R.id.empty_text);
        transactionArrayList = new ArrayList<UploadTransaction>();
        dbHelper = new DBTransactionHelper(this);
        displayDatabaseInfo();
        recyclerView = findViewById(R.id.transactions_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new ListTransactionAdapter(this, transactionArrayList);
        recyclerView.setAdapter(myAdapter);
    }

    private void displayDatabaseInfo() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DBTransactionContent.TransactionEntry.COLUMN_FROM_NAME,
                DBTransactionContent.TransactionEntry.COLUMN_TO_NAME,
                DBTransactionContent.TransactionEntry.COLUMN_AMOUNT,
                DBTransactionContent.TransactionEntry.COLUMN_STATUS
        };


        Cursor cursor = db.query(
                DBTransactionContent.TransactionEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            int fromNameColumnIndex = cursor.getColumnIndex(DBTransactionContent.TransactionEntry.COLUMN_FROM_NAME);
            int ToNameColumnIndex = cursor.getColumnIndex(DBTransactionContent.TransactionEntry.COLUMN_TO_NAME);
            int amountColumnIndex = cursor.getColumnIndex(DBTransactionContent.TransactionEntry.COLUMN_AMOUNT);
            int statusColumnIndex = cursor.getColumnIndex(DBTransactionContent.TransactionEntry.COLUMN_STATUS);

            while (cursor.moveToNext()) {
                String fromName = cursor.getString(fromNameColumnIndex);
                String ToName = cursor.getString(ToNameColumnIndex);
                int accountBalance = cursor.getInt(amountColumnIndex);
                int status = cursor.getInt(statusColumnIndex);
                transactionArrayList.add(new UploadTransaction(fromName, ToName, accountBalance, status));
            }
        } finally {
            cursor.close();
        }

        if (transactionArrayList.isEmpty()) {
            emptyList.setVisibility(View.VISIBLE);
        } else {
            emptyList.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TransactionActivity.this, MainActivity.class));
        finish();
    }
}