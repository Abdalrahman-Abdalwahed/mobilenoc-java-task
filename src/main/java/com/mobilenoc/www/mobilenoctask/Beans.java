package com.mobilenoc.www.mobilenoctask;

import com.mobilenoc.www.mobilenoctask.dao.TaskDAO;
import com.mobilenoc.www.mobilenoctask.dao.TaskDAOImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class Beans {

    @Value("${datasource.tableName}")
    private String tableName;
    @Value("${datasource.mirrorTableName}")
    private String mirrorTableName;

    @Bean
    @Primary
    @Qualifier("taskDAO")
    public TaskDAO taskDAO(DataSource dataSource) {
        return new TaskDAOImpl(tableName, dataSource);
    }

    @Bean
    @Qualifier("taskMirrorDAO")
    public TaskDAO taskMirrorDAO(DataSource dataSource) {
        return new TaskDAOImpl(mirrorTableName, dataSource);
    }


}
