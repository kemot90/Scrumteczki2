package pl.kemot.scrum.scrumteczki2.persistence;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Tomek on 30.12.13.
 */
public class TaskTable {
    public static final String TABLE_NAME = "task";
    public static class TaskColumns implements BaseColumns {
        public static final String LABEL = "label";
        public static final String PRODUCT = "product";
        public static final String ESTIMATED_TIME = "estimated_time";
        public static final String SPRINT_ID = "sprint_id";
    }
    public static void onCreate(SQLiteDatabase database) {
        StringBuilder createTableRequestBuilder = new StringBuilder();
        createTableRequestBuilder.append("CREATE TABLE " + TaskTable.TABLE_NAME + " (");
        createTableRequestBuilder.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        createTableRequestBuilder.append(TaskColumns.LABEL + " TEXT UNIQUE NOT NULL, ");
        createTableRequestBuilder.append(TaskColumns.PRODUCT + " TEXT NOT NULL, ");
        createTableRequestBuilder.append(TaskColumns.ESTIMATED_TIME + " TEXT NOT NULL, ");
        createTableRequestBuilder.append(TaskColumns.SPRINT_ID + " INTEGER NOT NULL, ");
        createTableRequestBuilder.append("FOREIGN KEY (" + TaskColumns.SPRINT_ID + ") REFERENCES ");
        createTableRequestBuilder.append(SprintTable.TABLE_NAME + " (" + BaseColumns._ID + ")");
        createTableRequestBuilder.append(");");
        database.execSQL(createTableRequestBuilder.toString());
    }
    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXIST " + TaskTable.TABLE_NAME);
        TaskTable.onCreate(database);
    }
}
