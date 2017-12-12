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
 * 데이터베이스 exmall Datasoruce
 */
@Configuration
@MapperScan(value="com.cellbiotech.mapper.exmall", sqlSessionFactoryRef="exmallSqlSessionFactory")
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "exmallEntityManagerFactory",
        transactionManagerRef = "exmallTransactionManager",
        basePackages = { "com.cellbiotech.dao.exmall" })
public class ExmallDataSourceConfig {
    @Bean(name = "exmallDataSource")
    @ConfigurationProperties(prefix="exmall.datasource")
    public DataSource exmallDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "exmallEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean barEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("exmallDataSource") DataSource exmallDataSource) {
        return builder
                .dataSource(exmallDataSource)
                .packages("com.cellbiotech.model.exmall")
                .persistenceUnit("exmall")
                .build();
    }

    @Bean(name = "exmallSqlSessionFactory")
    public SqlSessionFactory exmallSqlSessionFactory(@Qualifier("exmallDataSource") DataSource exmallDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(exmallDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/exmall/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "exmallSqlSessionTemplate")
    public SqlSessionTemplate exmallSqlSessionTemplate(SqlSessionFactory exmallSqlSessionFactory) throws Exception {

        return new SqlSessionTemplate(exmallSqlSessionFactory);
    }

    @Bean(name = "exmallTransactionManager")
    public PlatformTransactionManager exmallTransactionManager(
            @Qualifier("exmallEntityManagerFactory") EntityManagerFactory exmallEntityManagerFactory) {
        return new JpaTransactionManager(exmallEntityManagerFactory);
    }

}
