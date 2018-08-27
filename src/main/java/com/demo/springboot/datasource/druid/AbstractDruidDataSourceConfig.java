package com.demo.springboot.datasource.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableConfigurationProperties(DruidProperties.class)
public abstract class AbstractDruidDataSourceConfig {
    private Logger log = LoggerFactory.getLogger(AbstractDruidDataSourceConfig.class);
    @Resource
    private DruidProperties druidProperties;

    protected DruidDataSource createDataSource(String url, String username, String password, String driverClass) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClass);

        dataSource.setInitialSize(druidProperties.getInitialSize());
        dataSource.setMinIdle(druidProperties.getMinIdle());
        dataSource.setMaxActive(druidProperties.getMaxActive());
        dataSource.setMaxWait(druidProperties.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(druidProperties.getValidationQuery());
        dataSource.setTestWhileIdle(druidProperties.isTestWhileIdle());
        dataSource.setTestOnBorrow(druidProperties.isTestOnBorrow());
        dataSource.setTestOnReturn(druidProperties.isTestOnReturn());
        try {
            dataSource.setFilters(druidProperties.getFilters());
        } catch (SQLException e) {
            log.error("druid configuration initialization filter", e);
        }
        dataSource.setConnectionProperties(druidProperties.getConnectionProperties());
        return dataSource;
    }

    protected SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        return createSqlSessionFactory(dataSource, "classpath:mapper/*.xml");
    }

    protected SqlSessionFactory sqlSessionFactory(DataSource dataSource, String mapperLocations) throws Exception {
        return createSqlSessionFactory(dataSource, mapperLocations);
    }

    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource, String mapperLocations) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));
        return sqlSessionFactoryBean.getObject();
    }
}