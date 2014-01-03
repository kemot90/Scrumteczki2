package pl.kemot.scrum.scrumteczki2.persistence;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import pl.kemot.scrum.scrumteczki2.model.Sprint;

/**
 * Created by Tomek on 30.12.13.
 */
public class SprintTable {
    public static final String TABLE_NAME = "sprint";
    public static class SprintColumns implements BaseColumns {
        public static final String NAME = "name";
        public static final String ADD_DATE = "add_date";
    }
    public static void onCreate(SQLiteDatabase database) {
        StringBuilder createTableRequestBuilder = new StringBuilder();
        createTableRequestBuilder.append("CREATE TABLE " + SprintTable.TABLE_NAME + " (");
        createTableRequestBuilder.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        createTableRequestBuilder.append(SprintColumns.NAME + " TEXT NOT NULL UNIQUE, ");
        createTableRequestBuilder.append(SprintColumns.ADD_DATE + " INTEGER NOT NULL");
        createTableRequestBuilder.append(");");
        database.execSQL(createTableRequestBuilder.toString());
    }
    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXIST " + SprintTable.TABLE_NAME);
        SprintTable.onCreate(database);
    }
}
