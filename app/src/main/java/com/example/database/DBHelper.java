package com.example.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "contacts.db";
    private static int DATABASE_VERSION = 1;

    public DBHelper(Context context) { super(context, DATABASE_NAME, null,DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCommand = "create table contact (_id integer primary key autoincrement, "
                + "contactname text not null, "
                + "streetaddress text, "
                + "city text, state text, zipcode text, "
                + "phonenumber text, cellnumber text, "
                + "email text, birthday text);";
        sqLiteDatabase.execSQL(sqlCommand);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int newVer) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contact");
        onCreate(sqLiteDatabase);

    }
}