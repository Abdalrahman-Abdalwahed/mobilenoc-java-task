package com.mobilenoc.www.mobilenoctask.entity;

import java.util.Objects;

public class Task {
    private long id;
    private String name;
    private String description;

    public Task() {
    }

    public Task(long id, String name, String description) {
        this.id = id;
        setName(name);
        this.description = description;
    }

    public Task(String name, String description) {
        setName(name);
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getId() == task.getId() &&
                getName().equals(task.getName()) &&
                getDescription().equals(task.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }
}
