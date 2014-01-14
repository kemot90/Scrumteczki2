package pl.kemot.scrum.scrumteczki2.persistence;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Tomek on 30.12.13.
 */
public class ChangesTable {
    public static final String TABLE_NAME = "changes";
    public static class ChangesColumns {
        public static final String TASK_ID = "task_id";
        public static final String NEW_ESTIMATED_TIME = "new_estimated_time";
    }
    public static void onCreate(SQLiteDatabase database) {
        StringBuilder createTableRequestBuilder = new StringBuilder();
        createTableRequestBuilder.append("CREATE TABLE " + ChangesTable.TABLE_NAME + " (");
        createTableRequestBuilder.append(ChangesColumns.TASK_ID + " INTEGER NOT NULL UNIQUE, ");
        createTableRequestBuilder.append(ChangesColumns.NEW_ESTIMATED_TIME + " TEXT NOT NULL, ");
        createTableRequestBuilder.append("FOREIGN KEY (" + ChangesColumns.TASK_ID + ") REFERENCES ");
        createTableRequestBuilder.append(TaskTable.TABLE_NAME + "(" + BaseColumns._ID + ")");
        createTableRequestBuilder.append(");");
        database.execSQL(createTableRequestBuilder.toString());
    }
    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXIST " + ChangesTable.TABLE_NAME);
        onCreate(database);
    }
}
