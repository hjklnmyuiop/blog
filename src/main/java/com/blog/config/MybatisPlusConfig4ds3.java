package com.blog.config;


import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Mybatis-plus ds3数据源配置
 * 多数据源配置依赖数据源配置
 * @see  DataSourceConfig
 */
@Configuration
@MapperScan(basePackages ="com.blog.dao.db3", sqlSessionTemplateRef  = "ds3SqlSessionTemplate")
public class MybatisPlusConfig4ds3 {

    //ds3数据源
    @Bean("ds3SqlSessionFactory")
    public SqlSessionFactory ds3SqlSessionFactory(@Qualifier("ds3DataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath*:mapper/ds3/*.xml"));
        sqlSessionFactory.setPlugins(new Interceptor[]{
                new PaginationInterceptor(),
                new PerformanceInterceptor()
//                        .setFormat(true),
        });
        sqlSessionFactory.setGlobalConfig(new GlobalConfig().setBanner(false));
        return sqlSessionFactory.getObject();
    }

    @Bean(name = "ds3TransactionManager")
    public DataSourceTransactionManager ds3TransactionManager(@Qualifier("ds3DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "ds3SqlSessionTemplate")
    public SqlSessionTemplate ds3SqlSessionTemplate(@Qualifier("ds2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
