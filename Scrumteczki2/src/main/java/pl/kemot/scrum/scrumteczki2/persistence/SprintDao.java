package pl.kemot.scrum.scrumteczki2.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import pl.kemot.scrum.scrumteczki2.model.Sprint;
import pl.kemot.scrum.scrumteczki2.model.Task;

/**
 * Created by Tomek on 01.01.14.
 */
public class SprintDao implements Dao<Sprint> {
    private static final String INSERT = "insert or replace into " + SprintTable.TABLE_NAME
            + " (" + SprintTable.SprintColumns.NAME + ", " + SprintTable.SprintColumns.ADD_DATE + ") values (?, ?)";
    private SQLiteDatabase database;
    private SQLiteStatement statement;

    public SprintDao(SQLiteDatabase database) {
        if (database == null) {
            throw new NullPointerException("Obiekt reprezentujący połączenie z bazą danych nie może być null!");
        }
        this.database = database;
        statement = database.compileStatement(SprintDao.INSERT);
    }
    @Override
    public long save(Sprint entity) {
        statement.clearBindings();
        statement.bindString(1, entity.getName());
        statement.bindLong(2, entity.getAddDate().getTime());
        return statement.executeInsert();
    }

    @Override
    public void update(Sprint entity) {
        final ContentValues values = new ContentValues();
        values.put(SprintTable.SprintColumns.NAME, entity.getName());
        values.put(SprintTable.SprintColumns.ADD_DATE, entity.getAddDate().getTime());
        database.update(
                SprintTable.TABLE_NAME,
                values,
                BaseColumns._ID + " = ?",
                new String[] {
                        String.valueOf(entity.getId())
                });
    }

    @Override
    public void delete(Sprint entity) {
        if (entity.getId() > 0) {
            database.delete(
                    SprintTable.TABLE_NAME,
                    BaseColumns._ID + " = ?",
                    new String[] {
                            String.valueOf(entity.getId())
                    });
        }
    }

    @Override
    public Sprint get(long id) {
        Sprint sprint = null;
        TaskDao taskDao = new TaskDao(database);
        Cursor cursor = database.query(
                SprintTable.TABLE_NAME,
                new String[] {
                        BaseColumns._ID,
                        SprintTable.SprintColumns.NAME,
                        SprintTable.SprintColumns.ADD_DATE
                },
                BaseColumns._ID + " = ?",
                new String[] {
                        String.valueOf(id)
                },
                null,
                null,
                null,
                "1");
        if (cursor.moveToFirst()) {
            sprint = buildSprintFromCursor(cursor);
            List<Task> taskList = taskDao.getTaskBySprint(sprint);
            for (Task task : taskList) {
                sprint.addTask(task);
            }
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return sprint;
    }

    private Sprint buildSprintFromCursor(Cursor cursor) {
        Sprint sprint = null;
        if (cursor != null) {
            sprint = new Sprint();
            sprint.setId(cursor.getLong(0));
            sprint.setName(cursor.getString(1));
            sprint.setAddDate(new Date(cursor.getLong(2)));
        }
        return sprint;
    }

    @Override
    public List<Sprint> getAll() {
        List<Sprint> sprintList = new LinkedList<>();
        TaskDao taskDao = new TaskDao(database);
        Cursor cursor = database.query(
                SprintTable.TABLE_NAME,
                new String[] {
                        BaseColumns._ID,
                        SprintTable.SprintColumns.NAME,
                        SprintTable.SprintColumns.ADD_DATE
                },
                null,
                null,
                null,
                null,
                SprintTable.SprintColumns.ADD_DATE + " DESC");
        if (cursor.moveToFirst()) {
            do {
                Sprint sprint = buildSprintFromCursor(cursor);
                List<Task> taskList = taskDao.getTaskBySprint(sprint);
                for (Task task : taskList) {
                    sprint.addTask(task);
                }
                sprintList.add(sprint);
            } while(cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return sprintList;
    }
}
