package com.demo.springboot.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.demo.springboot.datasource.druid.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.demo.springboot.mapper"}, sqlSessionFactoryRef = "testSqlSessionFactory")
public class TestDataSourceConfig extends AbstractDruidDataSourceConfig {
    @Value("${spring.datasource.type}")
    private String type;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.username}")
    private String username;

    @Primary
    @Bean(name = "test")
    public DruidDataSource testDataSource() {
        return super.createDataSource(url, username, password, driverClassName);
    }

    @Primary
    @Bean//事务管理
    public DataSourceTransactionManager setTransactionManager(@Qualifier("test") DataSource dataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }

    @Primary
    @Bean(name = "testSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("test") DataSource dataSource) throws Exception {
        return super.sqlSessionFactory(dataSource, "classpath*:mapper/*.xml");
    }

    @Primary
    @Bean
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("testSqlSessionFactory") SqlSessionFactory factory) {
        return new SqlSessionTemplate(factory);
    }
}
