package com.mobilenoc.www.mobilenoctask.dao;

import com.mobilenoc.www.mobilenoctask.entity.Task;

import java.util.List;

public interface TaskDAO {
    List<Task> findAll();
    Task findById(long id);
    void save(Task task);
    void update(Task task);
    void deleteById(long id);
    boolean exists(long id);
}
