package com.cellbiotech.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 데이터베이스 callNote Datasoruce
 */
@Configuration
@MapperScan(value="com.cellbiotech.mapper.callnote", sqlSessionFactoryRef="barSqlSessionFactory")
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "barEntityManagerFactory",
        transactionManagerRef = "barTransactionManager",
        basePackages = { "com.cellbiotech.dao.callnote" })
public class CallnoteDataSourceConfig {
    @Bean(name = "barDataSource")
    @ConfigurationProperties(prefix="bar.datasource")
    public DataSource barDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "barEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean barEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("barDataSource") DataSource barDataSource) {
        return builder
                .dataSource(barDataSource)
                .packages("com.cellbiotech.model.callnote")
                .persistenceUnit("bar")
                .build();
    }

    @Bean(name = "barSqlSessionFactory")
    public SqlSessionFactory barSqlSessionFactory(@Qualifier("barDataSource") DataSource barDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(barDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/callnote/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "barSqlSessionTemplate")
    public SqlSessionTemplate barSqlSessionTemplate(SqlSessionFactory barSqlSessionFactory) throws Exception {

        return new SqlSessionTemplate(barSqlSessionFactory);
    }

    @Bean(name = "barTransactionManager")
    public PlatformTransactionManager barTransactionManager(
            @Qualifier("barEntityManagerFactory") EntityManagerFactory barEntityManagerFactory) {
        return new JpaTransactionManager(barEntityManagerFactory);
    }

}
