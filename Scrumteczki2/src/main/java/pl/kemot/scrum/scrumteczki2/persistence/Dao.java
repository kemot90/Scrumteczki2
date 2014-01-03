package pl.kemot.scrum.scrumteczki2.persistence;

import java.util.List;

/**
 * Created by Tomek on 01.01.14.
 */
public interface Dao<T> {
    public long save(T entity);
    public void update(T entity);
    public void delete(T entity);
    public T get(long id);
    public List<T> getAll();
}
