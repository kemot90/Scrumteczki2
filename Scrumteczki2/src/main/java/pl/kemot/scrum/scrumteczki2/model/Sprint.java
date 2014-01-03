package pl.kemot.scrum.scrumteczki2.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Tomek on 29.12.13.
 */
public class Sprint extends BaseEntity {
    private String name;
    private Date addDate;
    private List<Task> tasks;

    public Sprint() {
        tasks = new LinkedList<>();
    }

    /**
     * Doda zadanie do zbioru zadań w sprincie.
     * @param task zadanie do zbioru zadań w sprincie
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * @return zbiór zadań w sprincie
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * @return nazwa sprintu
     */
    public String getName() {
        return name;
    }

    /**
     * Ustawi nazwę sprintu.
     * @param name nazwa sprintu
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return data dodania sprintu
     */
    public Date getAddDate() {
        return new Date(addDate.getTime());
    }

    /**
     * Ustawi datę dodania sprintu.
     * @param addDate data dodania sprintu
     */
    public void setAddDate(Date addDate) {
        this.addDate = new Date(addDate.getTime());
    }
}
