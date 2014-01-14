package pl.kemot.scrum.scrumteczki2;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.kemot.scrum.scrumteczki2.model.Changes;
import pl.kemot.scrum.scrumteczki2.model.Task;
import pl.kemot.scrum.scrumteczki2.service.LoadChangesTask;

/**
 * Created by Tomek on 12.01.14.
 */
public class ObservableChangesList implements Observable<Listener> {
    private final List<Listener> adapters = new LinkedList<>();
    private final LinkedList<Changes> changesList = new LinkedList<>();
    private final Map<Long, Changes> taskChangesMap = new HashMap<>();
    private Context context;
    private ScrumteczkiApp application;

    public ObservableChangesList(Context context) {
        this.context = context;
        application = (ScrumteczkiApp) context.getApplicationContext();
        LoadChangesTask loadChangesTask = new LoadChangesTask(context);
        loadChangesTask.execute();
    }

    @Override
    public boolean addListener(Listener listener) {
        return adapters.add(listener);
    }

    @Override
    public boolean removeListener(Listener listener) {
        return adapters.remove(listener);
    }

    @Override
    public void notifyListeners() {
        for (Listener listener : adapters) {
            listener.update();
        }
    }
    public List<Changes> getChangesList() {
        return changesList;
    }
    public synchronized void addChanges(Changes changes) {
        changesList.add(changes);
        taskChangesMap.put(changes.getTask().getId(), changes);
        notifyListeners();
    }
    public synchronized void addAll(List<Changes> changesList) {
        this.changesList.addAll(changesList);
        for (Changes changes : changesList) {
            taskChangesMap.put(changes.getTask().getId(), changes);
        }
        notifyListeners();
    }
    public synchronized boolean removeChanges(Changes changes) {
        boolean result = changesList.remove(changes);
        taskChangesMap.remove(changes.getTask().getId());
        notifyListeners();
        return result;
    }
    public synchronized void clear() {
        changesList.clear();
        taskChangesMap.clear();
        notifyListeners();
    }
    public Changes findChangesByTaskId(long id) {
        return taskChangesMap.get(id);
    }
    public boolean containsTask(Task task) {
        Log.d("TM", "Czy zmiany zwierają zadanie: " + taskChangesMap.containsKey(task.getId()));
        return taskChangesMap.containsKey(task.getId());
    }
    public Changes getChangesByTask(Task task) {
        return getChangesByTaskId(task.getId());
    }
    public Changes getChangesByTaskId(long id) {
        return taskChangesMap.get(id);
    }
}
