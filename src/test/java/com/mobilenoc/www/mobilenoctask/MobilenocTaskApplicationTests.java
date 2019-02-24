package com.mobilenoc.www.mobilenoctask;

import com.mobilenoc.www.mobilenoctask.entity.Task;
import com.mobilenoc.www.mobilenoctask.exception.NotFoundException;
import com.mobilenoc.www.mobilenoctask.service.TaskService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MobilenocTaskApplicationTests {
    @Autowired
    private TaskService taskService;

    @Test
    public void getAllTasks() {
        Assert.assertEquals(4, taskService.getAllTasks().size());
    }

    @Test
    public void getTaskByValidId() {
        Task task = taskService.getTaskById(2);
        Assert.assertNotNull(task);
    }

    @Test(expected = NotFoundException.class)
    public void getTaskByInValidId() {
        taskService.getTaskById(10);
    }


    @Test(expected = IllegalArgumentException.class)
    public void insertTask() {
        Task task = new Task(null, "zaid description");
        taskService.insertTask(task);
    }

    @Test(expected = NotFoundException.class)
    public void deleteTaskByInValidId() {
        taskService.deleteTask(8);

    }
}
