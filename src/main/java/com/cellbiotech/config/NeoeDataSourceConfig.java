package com.cellbiotech.config;

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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * 데이터베이스 neoe Datasoruce
 */
@Configuration
@MapperScan(value="com.cellbiotech.mapper.neoe", sqlSessionFactoryRef="neoeSqlSessionFactory")
@EnableTransactionManagement
public class NeoeDataSourceConfig {
    @Bean(name = "neoeDataSource")
    @ConfigurationProperties(prefix="neoe.datasource")
    public DataSource neoeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "neoeSqlSessionFactory")
    public SqlSessionFactory neoeSqlSessionFactory(@Qualifier("neoeDataSource") DataSource neoeDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(neoeDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/neoe/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "neoeSqlSessionTemplate")
    public SqlSessionTemplate neoeSqlSessionTemplate(SqlSessionFactory neoeSqlSessionFactory) throws Exception {

        return new SqlSessionTemplate(neoeSqlSessionFactory);
    }

}
