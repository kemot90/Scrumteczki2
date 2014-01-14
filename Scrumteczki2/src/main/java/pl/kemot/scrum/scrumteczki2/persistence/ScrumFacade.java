package pl.kemot.scrum.scrumteczki2.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.List;

import pl.kemot.scrum.scrumteczki2.ScrumteczkiApp;
import pl.kemot.scrum.scrumteczki2.model.Changes;
import pl.kemot.scrum.scrumteczki2.model.EstimatedTime;
import pl.kemot.scrum.scrumteczki2.model.Sprint;
import pl.kemot.scrum.scrumteczki2.model.Task;

/**
 * Created by Tomek on 01.01.14.
 */
public class ScrumFacade {
    private SQLiteDatabase database;
    private SprintDao sprintDao;
    private TaskDao taskDao;
    private ChangesDao changesDao;
    private Context context;
    private ScrumteczkiApp application;

    /**
     * Konstruktor, który zainicjalizuje fasadę.
     * @param context kontekst aplikacji
     */
    public ScrumFacade(Context context) {
        this.context = context;
        application = (ScrumteczkiApp) context.getApplicationContext();
        SQLiteOpenHelper openHelper = new OpenHelper(context);
        database = openHelper.getWritableDatabase();
        sprintDao = new SprintDao(database);
        taskDao = new TaskDao(database);
        changesDao = new ChangesDao(database);
    }

    /**
     * Zapisze sprint podany jako argument wywołania do bazy danych.
     * @param sprint sprint do zapisania w bazie danych
     * @return identyfikator pod jakim sprint został zapisany do bazy danych
     */
    public long saveLoadedSprint(Sprint sprint) {
        sprint.setAddDate(new Date());
        long sprintId = sprintDao.save(sprint);
        sprint.setId(sprintId);
        for (Task task : sprint.getTasks()) {
            task.setSprintId(sprintId);
            long taskId = taskDao.save(task);
            task.setId(taskId);
        }
        return sprintId;
    }

    /**
     * Wczyta wszystkie sprinty z bazy danych.
     * @return lista sprintów z bazy danych
     */
    public List<Sprint> loadAllSprintsFromDataBase() {
        return sprintDao.getAll();
    }

    /**
     * Wczyta wszystkie zmiany estymowanego czasu do zakończnia zadań do daily scruma.
     * @return lista zmian estymowanych czasów do zakończenia zadania
     */
    public List<Changes> loadAllChangesFromDataBase() {
        return changesDao.getAll();
    }

    /**
     * Doda zmianę estymowanego czasu do zakończenia zadania do bazy danych i obserwowalnej listy aplikacji,
     * informując przy tym listę dla sprintów o zmianie danych.
     * @param task zadanie, którego dotyczy zmiana estymowanego czasu do jego zakończenia
     * @param estimatedTime nowy estymowany czas do zakończenia zadania
     * @return
     */
    public boolean addToDailyScrum(Task task, EstimatedTime estimatedTime) {
        Changes changes = new Changes();
        boolean isAlreadyChanged = application.getObservableChangesList().containsTask(task);
        boolean isEstimatedTimeSame = task.getEstimatedTime().equals(estimatedTime.toString());
        if (isAlreadyChanged) {
            changes = application.getObservableChangesList().getChangesByTask(task);
            if (isEstimatedTimeSame) {
                removeChanges(changes);
                return false;
            }
        } else if (isEstimatedTimeSame) {
            return false;
        }
        changes.setNewEstimatedTimeToCompleteTask(estimatedTime.toString());
        application.getObservableSprintList().notifyListeners();
        if (!isAlreadyChanged) {
            changes.setTask(task);
            application.getObservableChangesList().addChanges(changes);
            changesDao.save(changes);
        } else {
            changesDao.update(changes);
        }
        return true;
    }

    private void removeChanges(Changes changes) {
        application.getObservableChangesList().removeChanges(changes);
        changesDao.delete(changes);
        application.getObservableSprintList().notifyListeners();
    }

    public void mergeChanges() {
        List<Changes> changesList = application.getObservableChangesList().getChangesList();
        for (Changes changes : changesList) {
            Task changedTask = changes.getTask();
            changedTask.setEstimatedTime(changes.getNewEstimatedTimeToCompleteTask());
            taskDao.update(changedTask);
            changesDao.delete(changes);
        }
        application.getObservableChangesList().clear();
    }
}
