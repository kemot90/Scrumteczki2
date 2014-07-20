package com.musialowski.scrumteczki2.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import com.musialowski.scrumteczki2.model.Changes;
import com.musialowski.scrumteczki2.model.Task;

/**
 * Created by Tomek on 11.01.14.
 */
public class ChangesDao implements Dao<Changes> {
    private static final String INSERT = "insert or replace into " + ChangesTable.TABLE_NAME
            + " (" + ChangesTable.ChangesColumns.TASK_ID + ", "
            + ChangesTable.ChangesColumns.NEW_ESTIMATED_TIME + ") values (?, ?)";
    private SQLiteDatabase database;
    private SQLiteStatement statement;

    public ChangesDao(SQLiteDatabase database) {
        if (database == null) {
            throw new NullPointerException("Obiekt reprezentujący połączenie z bazą danych nie może być null!");
        }
        this.database = database;
        statement = database.compileStatement(ChangesDao.INSERT);
    }
    @Override
    public long save(Changes entity) {
        statement.clearBindings();
        statement.bindLong(1, entity.getTask().getId());
        statement.bindString(2, entity.getNewEstimatedTimeToCompleteTask());
        return statement.executeInsert();
    }

    @Override
    public void update(Changes entity) {
        final ContentValues values = new ContentValues();
        values.put(ChangesTable.ChangesColumns.TASK_ID, entity.getTask().getId());
        values.put(ChangesTable.ChangesColumns.NEW_ESTIMATED_TIME, entity.getNewEstimatedTimeToCompleteTask());
        database.update(
                ChangesTable.TABLE_NAME,
                values,
                ChangesTable.ChangesColumns.TASK_ID + " = ?",
                new String[] {
                        String.valueOf(entity.getTask().getId())
                });
    }

    @Override
    public void delete(Changes entity) {
        if (entity.getTask().getId() > 0) {
            database.delete(
                    ChangesTable.TABLE_NAME,
                    ChangesTable.ChangesColumns.TASK_ID + " = ?",
                    new String[] {
                            String.valueOf(entity.getTask().getId())
                    });
        }
    }

    @Override
    public Changes get(long id) {
        Changes changes = null;
        TaskDao taskDao = new TaskDao(database);
        Cursor cursor = database.query(
                ChangesTable.TABLE_NAME,
                new String[] {
                        ChangesTable.ChangesColumns.TASK_ID,
                        ChangesTable.ChangesColumns.NEW_ESTIMATED_TIME
                },
                ChangesTable.ChangesColumns.TASK_ID + " = ?",
                new String[] {
                        String.valueOf(id)
                },
                null,
                null,
                null,
                "1");
        if (cursor.moveToFirst()) {
            changes = buildChangesFromCursor(cursor);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return changes;
    }

    @Override
    public List<Changes> getAll() {
        List<Changes> changesList = new LinkedList<>();
        Cursor cursor = database.query(
                ChangesTable.TABLE_NAME,
                new String[] {
                        ChangesTable.ChangesColumns.TASK_ID,
                        ChangesTable.ChangesColumns.NEW_ESTIMATED_TIME
                },
                null,
                null,
                null,
                null,
                ChangesTable.ChangesColumns.TASK_ID + " DESC");
        if (cursor.moveToFirst()) {
            do {
                Changes changes = buildChangesFromCursor(cursor);
                changesList.add(changes);
            } while(cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return changesList;
    }

    private Changes buildChangesFromCursor(Cursor cursor) {
        Changes changes = new Changes();
        TaskDao taskDao = new TaskDao(database);
        long taskId = cursor.getLong(0);
        Task consideredTask = taskDao.get(taskId);
        if (consideredTask == null)
            Log.d("TM", "Wczytane zadanie jest null! Id zadania: " + taskId);
        changes.setTask(consideredTask);
        changes.setNewEstimatedTimeToCompleteTask(cursor.getString(1));
        return changes;
    }
}
