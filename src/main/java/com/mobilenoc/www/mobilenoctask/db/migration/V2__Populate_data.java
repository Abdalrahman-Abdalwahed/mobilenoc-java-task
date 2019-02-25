package com.mobilenoc.www.mobilenoctask.db.migration;

import com.mobilenoc.www.mobilenoctask.dao.TaskDAOImpl;
import com.mobilenoc.www.mobilenoctask.dao.TaskDAO;
import com.mobilenoc.www.mobilenoctask.entity.Task;
import com.mobilenoc.www.mobilenoctask.service.TaskService;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import javax.sql.DataSource;

public class V2__Populate_data extends BaseJavaMigration {

    private static final long UNDEFINED = Long.MIN_VALUE;

    private static final Task[] TASKS = new Task[]{
            new Task(UNDEFINED, "Aboud", "Description"),
            new Task(UNDEFINED, "Ahmad", "DescriptionAhmad"),
            new Task(UNDEFINED, "Hamza", ""),
            new Task(UNDEFINED, "Khaled", null)
    };

    @Override
    public void migrate(Context context) throws Exception {
        DataSource dataSource = context.getConfiguration().getDataSource();
        TaskDAO taskDAO = new TaskDAOImpl("Task_definition", dataSource);
        TaskDAO mirrorTaskDAO = new TaskDAOImpl("Task_definition_mirror", dataSource);
        TaskService service = new TaskService(taskDAO, mirrorTaskDAO);

        for (Task currentTask : TASKS) {
            service.insertTask(currentTask);
        }
    }
}
