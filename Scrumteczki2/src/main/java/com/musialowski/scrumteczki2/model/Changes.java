package com.musialowski.scrumteczki2.model;

/**
 * Created by Tomek on 29.12.13.
 */
public class Changes {
    private Task task;
    private String newEstimatedTimeToCompleteTask;

    /**
     * @return zadanie, którego dotyczy zmiana estymowanego czasu do jego zakończenia
     */
    public Task getTask() {
        return task;
    }

    /**
     * Ustawi zadanie, którego dotyczy zmiana estymowanego czasu do jego zakończenia.
     * @param task zadanie, którego dotyczy zmiana estymowanego czasu do jego zakończenia
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * @return szacowany czas do zakończenia zadania
     */
    public String getNewEstimatedTimeToCompleteTask() {
        return newEstimatedTimeToCompleteTask;
    }

    /**
     * Ustawi szacowany czas do zakończenia zadania.
     * @param newEstimatedTimeToCompleteTask szacowany czas do zakończenia zadania
     */
    public void setNewEstimatedTimeToCompleteTask(String newEstimatedTimeToCompleteTask) {
        this.newEstimatedTimeToCompleteTask = newEstimatedTimeToCompleteTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Changes)) {
            return false;
        }
        Changes changes = (Changes) o;
        if (!changes.getTask().equals(getTask())) {
            return false;
        }
        return true;
    }
}
