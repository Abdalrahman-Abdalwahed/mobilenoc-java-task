package com.mobilenoc.www.mobilenoctask.service;

import com.mobilenoc.www.mobilenoctask.dao.TaskDAO;
import com.mobilenoc.www.mobilenoctask.entity.Task;
import com.mobilenoc.www.mobilenoctask.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskDAO taskDAO;
    private TaskDAO taskMirrorDAO;

    @Autowired
    public TaskService(@Qualifier("taskDAO") TaskDAO taskDAO, @Qualifier("taskMirrorDAO") TaskDAO taskMirrorDAO) {
        this.taskDAO = taskDAO;
        this.taskMirrorDAO = taskMirrorDAO;
    }

    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }

    public Task getTaskById(long id) {
        if (!taskIsExistById(id)) {
            throw new NotFoundException("Task doesn't exist");
        }
        return taskDAO.findById(id);
    }

    public void insertTask(Task task) {
        taskDAO.save(task);
        taskMirrorDAO.save(task);
    }

    public void updateTask(Task task) {
        if (!taskIsExistById(task.getId())) {
            throw new NotFoundException("Task doesn't exist");
        }
        taskDAO.update(task);
        taskMirrorDAO.update(task);
    }

    public void deleteTask(long id) {
        if (!taskIsExistById(id)) {
            throw new NotFoundException("Task doesn't exist");
        }
        taskDAO.deleteById(id);
        taskMirrorDAO.deleteById(id);
    }

    private boolean taskIsExistById(long id) {
        return taskDAO.exists(id);
    }


}
