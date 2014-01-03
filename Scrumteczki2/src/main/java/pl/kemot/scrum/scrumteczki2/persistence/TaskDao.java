package pl.kemot.scrum.scrumteczki2.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import java.util.LinkedList;
import java.util.List;

import pl.kemot.scrum.scrumteczki2.model.Sprint;
import pl.kemot.scrum.scrumteczki2.model.Task;

/**
 * Created by Tomek on 01.01.14.
 */
public class TaskDao implements Dao<Task> {

    private static final String INSERT = "insert or replace into " + TaskTable.TABLE_NAME
            + " (" + TaskTable.TaskColumns.LABEL + ", " + TaskTable.TaskColumns.PRODUCT
            + ", " + TaskTable.TaskColumns.ESTIMATED_TIME + ", " + TaskTable.TaskColumns.SPRINT_ID
            + ") values (?, ?, ?, ?)";
    private SQLiteDatabase database;
    private SQLiteStatement statement;
    public TaskDao(SQLiteDatabase database) {
        if (database == null) {
            throw new NullPointerException("Obiekt reprezentujący połączenie z bazą danych nie może być null!");
        }
        this.database = database;
        statement = database.compileStatement(TaskDao.INSERT);
    }
    @Override
    public long save(Task entity) {
        statement.clearBindings();
        statement.bindString(1, entity.getLabel());
        statement.bindString(2, entity.getProduct());
        statement.bindString(3, entity.getEstimatedTime());
        statement.bindLong(4, entity.getSprintId());
        return statement.executeInsert();
    }

    @Override
    public void update(Task entity) {
        final ContentValues values = new ContentValues();
        values.put(TaskTable.TaskColumns.LABEL, entity.getLabel());
        values.put(TaskTable.TaskColumns.PRODUCT, entity.getProduct());
        values.put(TaskTable.TaskColumns.ESTIMATED_TIME, entity.getEstimatedTime());
        values.put(TaskTable.TaskColumns.SPRINT_ID, entity.getSprintId());
        database.update(
                TaskTable.TABLE_NAME,
                values,
                BaseColumns._ID + " = ?",
                new String[] {
                        String.valueOf(entity.getId())
                });
    }

    @Override
    public void delete(Task entity) {
        if (entity.getId() > 0) {
            database.delete(
                    TaskTable.TABLE_NAME,
                    BaseColumns._ID + " = ?",
                    new String[] {
                            String.valueOf(entity.getId())
                    });
        }
    }

    @Override
    public Task get(long id) {
        Task task = null;
        Cursor cursor = database.query(
                TaskTable.TABLE_NAME,
                new String[] {
                        BaseColumns._ID,
                        TaskTable.TaskColumns.LABEL,
                        TaskTable.TaskColumns.PRODUCT,
                        TaskTable.TaskColumns.ESTIMATED_TIME
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
            task = buildTaskFromCursor(cursor);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return task;
    }

    @Override
    public List<Task> getAll() {
        Cursor cursor = database.query(
                TaskTable.TABLE_NAME,
                new String[] {
                        BaseColumns._ID,
                        TaskTable.TaskColumns.LABEL,
                        TaskTable.TaskColumns.PRODUCT,
                        TaskTable.TaskColumns.ESTIMATED_TIME
                },
                null,
                null,
                null,
                null,
                TaskTable.TaskColumns.LABEL);
        return getTaskFromCursor(cursor);
    }

    public List<Task> getTaskBySprint(Sprint sprint) {
        Cursor cursor = database.query(
                TaskTable.TABLE_NAME,
                new String[] {
                        BaseColumns._ID,
                        TaskTable.TaskColumns.LABEL,
                        TaskTable.TaskColumns.PRODUCT,
                        TaskTable.TaskColumns.ESTIMATED_TIME
                },
                TaskTable.TaskColumns.SPRINT_ID + " = ?",
                new String[] {
                        String.valueOf(sprint.getId())
                },
                null,
                null,
                TaskTable.TaskColumns.LABEL);
        return getTaskFromCursor(cursor);
    }

    private List<Task> getTaskFromCursor(Cursor cursor) {
        if (cursor == null) {
            throw new NullPointerException("Kursor zapytania nie może być null!");
        }
        List<Task> taskList = new LinkedList<>();
        if (cursor.moveToFirst()) {
            do {
                Task task = buildTaskFromCursor(cursor);
                if (task != null) {
                    taskList.add(task);
                }
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return taskList;
    }

    private Task buildTaskFromCursor(Cursor cursor) {
        Task task = null;
        if (cursor != null) {
            task = new Task();
            task.setId(cursor.getLong(0));
            task.setLabel(cursor.getString(1));
            task.setProduct(cursor.getString(2));
            task.setEstimatedTime(cursor.getString(3));
        }
        return task;
    }
}
