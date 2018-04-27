package com.anshishagua.configuration;

import com.anshishagua.object.Resource;
import com.anshishagua.annotations.Configuration;
import com.anshishagua.annotations.Value;
import com.anshishagua.utils.ResourceUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: lixiao
 * Date: 2018/4/26
 * Time: 上午9:25
 */

@Configuration(propertyPrefix = "mybatis.config")
public class MybatisConfig {
    private static final Map<String, Class<?>> mapperClassMap = new HashMap<>();
    private static final Map<Class<?>, Object> mapperInstanceMap = new HashMap<>();

    @Value(property = "dataSource.driverClassName")
    private String dataSourceDriverClassName;

    @Value(property = "dataSource.className")
    private String dataSourceClassName;

    @Value(property = "dataSource.jdbcUrl")
    private String dataSourceJdbcUrl;

    @Value(property = "dataSource.username")
    private String dataSourceUsername;

    @Value(property = "dataSource.password")
    private String dataSourcePassword;

    @Value(property = "mapperLocation")
    private String mapperLocation;

    public static void addMapper(String mapperName, Class<?> clazz) {
        mapperClassMap.put(mapperName, clazz);
    }

    public void config() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(dataSourceDriverClassName);
        hikariConfig.setJdbcUrl(dataSourceJdbcUrl);
        hikariConfig.setUsername(dataSourceUsername);
        hikariConfig.setPassword(dataSourcePassword);

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        DataSource dataSource = hikariDataSource;
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);

        for (Class<?> mapClass : mapperClassMap.values()) {
            configuration.addMapper(mapClass);
        }

        List<Resource> resources = ResourceUtils.loadResources(mapperLocation);

        for (Resource resource : resources) {
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(resource.getInputStream(),  configuration, resource.getName(), configuration.getSqlFragments());
            xmlMapperBuilder.parse();
        }

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        for (Map.Entry<String, Class<?>> entry : mapperClassMap.entrySet()) {
            Object object = sqlSession.getMapper(entry.getValue());

            mapperInstanceMap.put(entry.getValue(), object);

            String beanName = Character.toLowerCase(entry.getKey().charAt(0)) + entry.getKey().substring(1);

            BeanInstanceRegistry.registerBeanInstance(beanName, object);
        }
    }
}