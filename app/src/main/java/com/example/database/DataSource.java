package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public DataSource(Context context) {dbHelper = new DBHelper(context);
    }
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();

    }
    public void close() {dbHelper.close(); }

    public boolean insertContact(Contact c){
        boolean didSucceed = false;
        try {
            ContentValues values = new ContentValues();
            values.put("contactname", c.getName());
            values.put("streetaddress", c.getAddress());
            values.put("city", c.getCity());
            values.put("state", c.getState());
            values.put("zipcode", c.getZipCode());
            values.put("phonenumber", c.getPhoneNumber());
            values.put("cellnumber", c.getCellNumber());
            values.put("email", c.getEmail());
            values.put("birthday", String.valueOf(c.getBirthday().getTimeInMillis()));

            didSucceed = database.insert("contact", null, values) >0;
        } catch (Exception e) {

        }
        return didSucceed;
    }
    public boolean updateContact(Contact c){
        boolean didSucceed = false;
        try {
            ContentValues values = new ContentValues();
            values.put("contactname", c.getName());
            values.put("streetaddress", c.getAddress());
            values.put("city", c.getCity());
            values.put("state", c.getState());
            values.put("zipcode", c.getZipCode());
            values.put("phonenumber", c.getPhoneNumber());
            values.put("cellnumber", c.getCellNumber());
            values.put("email", c.getEmail());
            values.put("birthday", String.valueOf(c.getBirthday().getTimeInMillis()));
            Long id = (long) c.getContactID();
            didSucceed = database.update("contact", values, "_id="+id, null) > 0;
        } catch (Exception e) {

        }
        return didSucceed;
    }


    public int getLastContactID() {
        int lastID = -1;
        try {
            String query = "Select MAX(_id) from contact";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastID = cursor.getInt(0);
            cursor.close();
        } catch (Exception e) {

        }
        return lastID;
    }
    public ArrayList<String> getContactNames() {
        ArrayList<String> names = new ArrayList<>();
        try {
            String query = "Select contactname from contact";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                names.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {

        }
        return names;
    }

    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            String query = "Select * from contact";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Contact c = new Contact();
                c.setName(cursor.getString(1));
                c.setAddress(cursor.getString(2));
                c.setCity((cursor.getString(3)));
                contacts.add(c);
                cursor.moveToNext();

            }
            cursor.close();
        } catch (Exception e) {

        }
        return contacts;
    }

    public Contact getContact(int id) {
        Contact c = new Contact();
        try {
            String query = "Select * from contact where _id="+id;
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            c.setContactID(id);
            c.setName(cursor.getString(1));
            c.setAddress(cursor.getString(2));
            c.setCity(cursor.getString(3));
            cursor.close();
        } catch (Exception e) {

        }
        return c;
    }




}
