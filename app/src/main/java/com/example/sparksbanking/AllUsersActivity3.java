package com.example.sparksbanking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AllUsersActivity3 extends AppCompatActivity implements TransferToUserAdapter.OnUserListener {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<UploadUser> userArrayList;

    private DBUserHelper dbHelper;

    String date=null, time=null;
    int fromUserAccountNo, toUserAccountNo, toUserAccountBalance;
    String fromUserAccountName, fromUserAccountBalance, transferAmount, toUserAccountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users3);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");
        String date_and_time = simpleDateFormat.format(calendar.getTime());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fromUserAccountName = bundle.getString("FROM_USER_NAME");
            fromUserAccountNo = bundle.getInt("FROM_USER_ACCOUNT_NO");
            fromUserAccountBalance = bundle.getString("FROM_USER_ACCOUNT_BALANCE");
            transferAmount = bundle.getString("TRANSFER_AMOUNT");
        }

        userArrayList = new ArrayList<UploadUser>();
        dbHelper = new DBUserHelper(this);
        recyclerView = findViewById(R.id.send_to_user_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new TransferToUserAdapter(userArrayList, this);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public void onUserClick(int position) {
        // Insert data into transactions table
        toUserAccountNo = userArrayList.get(position).getAccountNumber();
        toUserAccountName = userArrayList.get(position).getName();
        toUserAccountBalance = userArrayList.get(position).getBalance();

        calculateAmount();

        new DBTransactionHelper(this).insertTransferData(fromUserAccountName, toUserAccountName, transferAmount, 1);
        Toast.makeText(this, "Transaction Successful!!", Toast.LENGTH_LONG).show();

        startActivity(new Intent(AllUsersActivity3.this, MainActivity.class));
        finish();
    }

    private void calculateAmount() {
        Integer currentAmount = Integer.parseInt(fromUserAccountBalance);
        Integer transferAmountInt = Integer.parseInt(transferAmount);
        Integer remainingAmount = currentAmount - transferAmountInt;
        Integer increasedAmount = transferAmountInt + toUserAccountBalance;

        new DBUserHelper(this).updateAmount(fromUserAccountNo, remainingAmount);
        new DBUserHelper(this).updateAmount(toUserAccountNo, increasedAmount);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder_exitButton = new AlertDialog.Builder(AllUsersActivity3.this);
        builder_exitButton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton ("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        DBTransactionHelper dbHelper = new DBTransactionHelper(getApplicationContext());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        values.put(DBTransactionContent.TransactionEntry.COLUMN_FROM_NAME, fromUserAccountName);
                        values.put(DBTransactionContent.TransactionEntry.COLUMN_TO_NAME, toUserAccountName);
                        values.put(DBTransactionContent.TransactionEntry.COLUMN_STATUS, 0);
                        values.put(DBTransactionContent.TransactionEntry.COLUMN_AMOUNT, transferAmount);

                        db.insert(DBTransactionContent.TransactionEntry.TABLE_NAME, null, values);

                        Toast.makeText(AllUsersActivity3.this, "Transaction Failed!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AllUsersActivity3.this, AllUsersActivity2.class));
                        finish();
                    }
                }).setNegativeButton("No", null);
        AlertDialog alertExit = builder_exitButton.create();
        alertExit.show();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBUserContent.UserEntry.COLUMN_USER_NAME,
                DBUserContent.UserEntry.COLUMN_USER_ACCOUNT_BALANCE,
                DBUserContent.UserEntry.COLUMN_USER_ACCOUNT_NUMBER,
                DBUserContent.UserEntry.COLUMN_USER_PHONE_NO,
                DBUserContent.UserEntry.COLUMN_USER_EMAIL,
                DBUserContent.UserEntry.COLUMN_USER_IFSC_CODE,
        };

        Cursor cursor = db.query(
                DBUserContent.UserEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            int phoneNoColumnIndex = cursor.getColumnIndex(DBUserContent.UserEntry.COLUMN_USER_PHONE_NO);
            int emailColumnIndex = cursor.getColumnIndex(DBUserContent.UserEntry.COLUMN_USER_EMAIL);
            int ifscCodeColumnIndex = cursor.getColumnIndex(DBUserContent.UserEntry.COLUMN_USER_IFSC_CODE);
            int accountNumberColumnIndex = cursor.getColumnIndex(DBUserContent.UserEntry.COLUMN_USER_ACCOUNT_NUMBER);
            int nameColumnIndex = cursor.getColumnIndex(DBUserContent.UserEntry.COLUMN_USER_NAME);
            int accountBalanceColumnIndex = cursor.getColumnIndex(DBUserContent.UserEntry.COLUMN_USER_ACCOUNT_BALANCE);

            while (cursor.moveToNext()) {
                String currentName = cursor.getString(nameColumnIndex);
                int accountNumber = cursor.getInt(accountNumberColumnIndex);
                String email = cursor.getString(emailColumnIndex);
                String phoneNumber = cursor.getString(phoneNoColumnIndex);
                String ifscCode = cursor.getString(ifscCodeColumnIndex);
                int accountBalance = cursor.getInt(accountBalanceColumnIndex);

                userArrayList.add(new UploadUser(currentName, accountNumber, phoneNumber, ifscCode, accountBalance, email));
            }
        } finally {
            cursor.close();
        }
    }
}