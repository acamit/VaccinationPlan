package com.example.android.vaccinationplan;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {


    /*For Synchronization Tasks*/
    public static final String AUTHORITY = "com.example.android.vaccinationplan";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "com.example.android.vaccinationplan.account";
    // The account name
    public static final String ACCOUNT = "VaccinationPlan";
    // Instance fields
    Account mAccount;

    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 60L;
    public static final long SYNC_INTERVAL_IN_HOURS = 24L;
    public static final long SYNC_INTERVAL_IN_WEEK = 1L;
    public static final long SYNC_INTERVAL =120;
            /*SYNC_INTERVAL_IN_MINUTES *
                    SECONDS_PER_MINUTE * SYNC_INTERVAL_IN_HOURS * SYNC_INTERVAL_IN_WEEK;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAccount = CreateSyncAccount(this);
        ContentResolver.addPeriodicSync(
                mAccount,
                AUTHORITY,
                new Bundle(),
                SYNC_INTERVAL);
       ContentResolver.setSyncAutomatically(mAccount , AUTHORITY , true);



    }

    public void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }

    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }
}
