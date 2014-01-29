package pl.kemot.scrum.scrumteczki2;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.kemot.scrum.scrumteczki2.model.Sprint;
import pl.kemot.scrum.scrumteczki2.model.Task;
import pl.kemot.scrum.scrumteczki2.service.LoadSprintsTask;

/**
 * Created by Tomek on 04.01.14.
 */
public class ObservableSprintList implements Observable<Listener> {
    private final List<Listener> adapters = new LinkedList<>();
    private final LinkedList<Sprint> sprintList = new LinkedList<>();
    private final Map<String, Task> labelToTaskMap = new HashMap<>();

    public ObservableSprintList(Context context) {
        LoadSprintsTask loadSprintsTask = new LoadSprintsTask(context);
        loadSprintsTask.execute();
    }
    private ObservableSprintList() {
        throw new UnsupportedOperationException("Użycie domyślnego konstruktora nie jest obsługiwane!");
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
        for (Listener adapter : adapters) {
            adapter.update();
        }
    }
    public List<Sprint> getSprintList() {
        return sprintList;
    }
    public synchronized void addSprint(Sprint sprint) {
        sprintList.addFirst(sprint);
        mapAllSprintTask(sprint);
        notifyListeners();
    }
    public synchronized void addAll(List<Sprint> sprints) {
        Log.d("TM", "Dodawanie grupy sprintów do obserwowalnej listy.");
        sprintList.addAll(0, sprints);
        for (Sprint sprint : sprints) {
            mapAllSprintTask(sprint);
        }
        Log.d("TM", "Poinformowanie obserwatorów o fakcie zamiany listy danych.");
        notifyListeners();
    }
    public synchronized boolean removeSprint(Sprint sprint) {
        boolean result = sprintList.remove(sprint);
        for (Task task : sprint.getTasks()) {
            labelToTaskMap.remove(task.getLabel());
        }
        notifyListeners();
        return result;
    }
    public synchronized void clear() {
        sprintList.clear();
        notifyListeners();
    }
    public Task getTaskByLabel(String label) {
        return labelToTaskMap.get(label);
    }
    private void mapAllSprintTask(Sprint sprint) {
        for (Task task : sprint.getTasks()) {
            labelToTaskMap.put(task.getLabel(), task);
        }
    }
}
