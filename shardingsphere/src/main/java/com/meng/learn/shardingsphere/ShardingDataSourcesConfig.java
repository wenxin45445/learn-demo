package com.meng.learn.shardingsphere;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.meng.learn.shardingsphere.constants.BaseDataConstants;
import groovy.util.logging.Slf4j;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 数据源配置
 */
@Configuration
@EnableTransactionManagement
@Slf4j
public class ShardingDataSourcesConfig {


    @Bean(name = "db")
    @ConfigurationProperties(prefix = "spring.datasource.db")
    DruidDataSource db() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "db2")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    DruidDataSource db2() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "db3")
    @ConfigurationProperties(prefix = "spring.datasource.db3")
    DruidDataSource db3() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "db4")
    @ConfigurationProperties(prefix = "spring.datasource.db4")
    DruidDataSource db4() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Primary
    public DataSource getDataSource() throws SQLException {
        // dataSourceMap 添加数据库
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put(BaseDataConstants.DB_NAME_DEFAULT, db());
        dataSourceMap.put(BaseDataConstants.DB_NAME_SECOND, db2());
        dataSourceMap.put(BaseDataConstants.DB_NAME_THREE, db3());
        dataSourceMap.put(BaseDataConstants.DB_NAME_FOUR, db4());

        // shardingRuleConfig
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        // 添加分片规则
        shardingRuleConfig.getTableRuleConfigs().add(getTableRuleCon());
        shardingRuleConfig.getBindingTableGroups().add(BaseDataConstants.SHARDING_TABLE_CALCULATE);

        // 没有配置分库策略的表指定默认数据库
        shardingRuleConfig.setDefaultDataSourceName(BaseDataConstants.DB_NAME_DEFAULT);
        // 设置默认的分库策略
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
                new StandardShardingStrategyConfiguration(BaseDataConstants.SHARDING_COLUMN_DEFAULT, new DBPreciseShardingAlgorithm()));

        Properties props = new Properties();
        props.put("sql.show ", true);

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, props);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(getDataSource());
        return txManager;
    }

    /**
     * 获取分表规则配置
     */
    private TableRuleConfiguration getTableRuleCon() {
        TableRuleConfiguration result = new TableRuleConfiguration(BaseDataConstants.SHARDING_TABLE_CALCULATE, BaseDataConstants.SHARDING_CALCULATE_NODES);
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration(BaseDataConstants.SHARDING_COLUMN_CALCULATE, new DBPreciseShardingAlgorithm()));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration(BaseDataConstants.SHARDING_COLUMN_CALCULATE, new CalculateTablePreciseShardingAlgorithm()));
        return result;
    }

}
