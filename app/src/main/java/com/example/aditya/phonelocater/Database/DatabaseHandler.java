package com.example.aditya.phonelocater.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aditya on 7/2/18.
 */



public class DatabaseHandler extends SQLiteOpenHelper {


    private static DatabaseHandler singleton;

    public static DatabaseHandler getInstance(final Context context) {
        if (singleton == null) {
            singleton = new DatabaseHandler(context);
        }
        return singleton;
    }

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "providerExample";

    private final Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Good idea to have the context that doesn't die with the window
        this.context = context.getApplicationContext();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Person.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean isEmpty(){

        final SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + Person.TABLE_NAME, null);

        if (mCursor.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            return false;

        } else
        {
            // I AM EMPTY
            return true;
        }
    }



    public synchronized Person getPerson(final long id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(Person.TABLE_NAME, Person.FIELDS,
                Person.COL_ID + " IS ?", new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        Person item = null;
        if (cursor.moveToFirst()) {
            item = new Person(cursor);
        }
        cursor.close();
        return item;
    }

    public synchronized Person getPerson(final String phone) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(Person.TABLE_NAME, Person.FIELDS,
                Person.COL_CONT + " IS ?", new String[] { phone },
                null, null, null, null);
        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        Person item = null;
        if (cursor.moveToFirst()) {
            item = new Person(cursor);
        }
        cursor.close();
        return item;
    }

    public synchronized Person getPerson() {
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(Person.TABLE_NAME, Person.FIELDS,
                null,
                null, null, null, null);
        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        Person item = null;
        if (cursor.moveToFirst()) {
            item = new Person(cursor);
        }
        cursor.close();
        return item;
    }

    public synchronized boolean putPerson(final Person person) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();

        int res = db.delete(Person.TABLE_NAME,
                null,null);


        if (person.id > -1) {
            result += db.update(Person.TABLE_NAME, person.getContent(),
                    Person.COL_ID + " IS ?",
                    new String[] { String.valueOf(person.id) });
        }

        if (result > 0) {
            success = true;
        } else {
            // Update failed or wasn't possible, insert instead
            final long id = db.insert(Person.TABLE_NAME, null,
                    person.getContent());

            if (id > -1) {
                person.id = id;
                success = true;
            }
        }

        if (success) {
            notifyProviderOnPersonChange();
        }

        return success;
    }

    public synchronized int removePerson(final Person person) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final int result = db.delete(Person.TABLE_NAME,
                Person.COL_ID + " IS ?",
                new String[] { Long.toString(person.id) });

        if (result > 0) {
            notifyProviderOnPersonChange();
        }
        return result;
    }

    private void notifyProviderOnPersonChange() {
        context.getContentResolver().notifyChange(
                PersonProvider.URI_PERSONS, null, false);
    }

}
