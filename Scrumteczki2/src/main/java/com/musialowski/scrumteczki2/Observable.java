package com.musialowski.scrumteczki2;

/**
 * Created by Tomek on 04.01.14.
 */
public interface Observable<T extends Listener> {
    public boolean addListener(T listener);
    public boolean removeListener(T listener);
    public void notifyListeners();
}
