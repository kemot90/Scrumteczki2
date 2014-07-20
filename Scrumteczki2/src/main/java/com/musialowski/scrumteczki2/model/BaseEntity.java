package com.musialowski.scrumteczki2.model;

/**
 * Created by Tomek on 29.12.13.
 */
public abstract class BaseEntity {
    protected long id;

    /**
     * @return identyfikator encji
     */
    public long getId() {
        return id;
    }

    /**
     * Ustawi identyfikator encji.
     * @param id identyfikator encji
     */
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (this.id ^ (this.id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BaseEntity)) {
            return false;
        }
        BaseEntity other = (BaseEntity) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
