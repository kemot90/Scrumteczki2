package com.musialowski.scrumteczki2.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tomek on 30.12.13.
 */
public class OpenHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "dailyscrum.db";
    private static final int DATABASE_VERSION = 1;
    public OpenHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        TaskTable.onCreate(sqLiteDatabase);
        SprintTable.onCreate(sqLiteDatabase);
        ChangesTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, final int oldVersion, final int newVersion) {
        TaskTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        SprintTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        ChangesTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
