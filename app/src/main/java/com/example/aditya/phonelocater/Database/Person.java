package com.example.aditya.phonelocater.Database;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by aditya on 7/2/18.
 */

public class Person {

    public static final String TABLE_NAME = "Person";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_CONT= "contactNo";

    public static final String[] FIELDS = { COL_ID,COL_NAME, COL_CONT };

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "( "
                    + COL_ID +" INTEGER PRIMARY KEY,"
                    + COL_NAME + " TEXT NOT NULL,"
                    + COL_CONT + " TEXT NOT NULL "
                    + ")";

    public String name = "";
    public String cont = "";
    public long id = -1;

    public Person() {
    }

    /**
     * Convert information from the database into a Person object.
     */
    public Person(final Cursor cursor) {
        // Indices expected to match order in FIELDS!
        this.id= cursor.getLong(0);
        this.name= cursor.getString(1);
        this.cont = cursor.getString(2);
    }

    /**
     * Return the fields in a ContentValues object, suitable for insertion
     * into the database.
     */
    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        // Note that ID is NOT included here
        values.put(COL_NAME, name);
        values.put(COL_CONT, cont);

        return values;
    }

}
