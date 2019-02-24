package com.mobilenoc.www.mobilenoctask.dao;

import com.mobilenoc.www.mobilenoctask.entity.Task;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TaskDAOImpl implements TaskDAO {
    private static final String FIND_ALL_QUERY_TEMPLATE = "SELECT * FROM %s;";
    private static final String FIND_BY_ID_QUERY_TEMPLATE = "SELECT * FROM %s WHERE id = ?;";
    private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO %s (name, description) VALUES(?, ?);";
    private static final String UPDATE_QUERY_TEMPLATE = "UPDATE %s SET (name, description) VALUES(?, ?) WHERE id = ?;";
    private static final String DELETE_QUERY_TEMPLATE = "DELETE FROM %s WHERE id = ?";

    private final String tableName;
    private final DataSource datasource;

    public TaskDAOImpl(String tableName, DataSource datasource) {
        this.tableName = tableName;
        this.datasource = datasource;
    }

    @Override
    public List<Task> findAll() {
        String queryTemplate = withTableName(FIND_ALL_QUERY_TEMPLATE);
        ResultSet resultSet = executeQuery(queryTemplate, statement -> {
        });
        return toTaskList(resultSet);
    }

    @Override
    public Task findById(long id) {
        String queryTemplate = withTableName(FIND_BY_ID_QUERY_TEMPLATE);
        ResultSet resultSet = executeQuery(queryTemplate, statement -> {
            try {
                statement.setLong(1, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            if (resultSet.next()) {
                return toTask(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void save(Task task) {
        String queryTemplate = withTableName(INSERT_QUERY_TEMPLATE);
        execute(queryTemplate, statement -> {
            try {
                statement.setString(1, task.getName());
                statement.setString(2, task.getDescription());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void update(Task task) {
        String queryTemplate = withTableName(UPDATE_QUERY_TEMPLATE);
        execute(queryTemplate, statement -> {
            try {
                statement.setString(1, task.getName());
                statement.setString(2, task.getDescription());
                statement.setLong(3, task.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void deleteById(long id) {
        String queryTemplate = withTableName(DELETE_QUERY_TEMPLATE);
        execute(queryTemplate, statement -> {
            try {
                statement.setLong(1, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public boolean exists(long id) {
        return findById(id) != null ? true : false;
    }

    public void execute(String queryTemplate, Consumer<PreparedStatement> consumer) {
        try {
            Connection con = datasource.getConnection();
            PreparedStatement statement = con.prepareStatement(queryTemplate);
            consumer.accept(statement);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String queryTemplate, Consumer<PreparedStatement> consumer) {
        try {
            Connection con = datasource.getConnection();
            PreparedStatement statement = con.prepareStatement(queryTemplate);
            consumer.accept(statement);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public Task toTask(ResultSet resultSet) {
        Task task = new Task();
        try {
            task.setId(resultSet.getLong("id"));
            task.setName(resultSet.getString("name"));
            task.setDescription(resultSet.getString("description"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    public List<Task> toTaskList(ResultSet resultSet) {
        List<Task> tasks = new ArrayList<>();

        try {
            while (resultSet.next()) {
                tasks.add(toTask(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }

    private String withTableName(String queryTemplate) {
        return String.format(queryTemplate, tableName);
    }

}
