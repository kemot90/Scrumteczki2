package pl.kemot.scrum.scrumteczki2.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.List;

import pl.kemot.scrum.scrumteczki2.model.Sprint;
import pl.kemot.scrum.scrumteczki2.model.Task;

/**
 * Created by Tomek on 01.01.14.
 */
public class ScrumFacade {
    private SQLiteDatabase database;
    private SprintDao sprintDao;
    private TaskDao taskDao;
    private Context context;

    public ScrumFacade(Context context) {
        this.context = context;
        SQLiteOpenHelper openHelper = new OpenHelper(context);
        database = openHelper.getWritableDatabase();
        sprintDao = new SprintDao(database);
        taskDao = new TaskDao(database);
    }
    public long saveLoadedSprint(Sprint sprint) {
        sprint.setAddDate(new Date());
        long sprintId = sprintDao.save(sprint);
        for (Task task : sprint.getTasks()) {
            task.setSprintId(sprintId);
            taskDao.save(task);
        }
        return sprintId;
    }
    public List<Sprint> loadAllSprintsFromDataBase() {
        return sprintDao.getAll();
    }
}
