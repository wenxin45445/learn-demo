package com.meng.learn.shardingsphere;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.cpic.gibms.mgt.constants.BaseDataConstants;
import lombok.extern.slf4j.Slf4j;
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
    @Bean(name="bmsdb")
    @ConfigurationProperties(prefix = "spring.datasource.bmsdb")
    @Primary
    DataSource bmsdb(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name="bmsdb2")
    @ConfigurationProperties(prefix = "spring.datasource.bmsdb2")
    DataSource bmsdb2(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name="bmsdb3")
    @ConfigurationProperties(prefix = "spring.datasource.bmsdb3")
    DataSource bmsdb3(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name="bmsdb4")
    @ConfigurationProperties(prefix = "spring.datasource.bmsdb4")
    DataSource bmsdb4(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource getDataSource()
            throws SQLException
    {
        // dataSourceMap 添加数据库
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put(BaseDataConstants.DB_NAME_DEFAULT, bmsdb());
        dataSourceMap.put(BaseDataConstants.DB_NAME_SECOND, bmsdb2());
        dataSourceMap.put(BaseDataConstants.DB_NAME_THREE, bmsdb3());
        dataSourceMap.put(BaseDataConstants.DB_NAME_FOUR, bmsdb4());

        // shardingRuleConfig
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        // 添加分片规则
        shardingRuleConfig.getTableRuleConfigs().add(getTableRuleCon());
        shardingRuleConfig.getBindingTableGroups().add(BaseDataConstants.SHARDING_TABLE_CALCULATE);

        // 没有配置分库策略的表指定默认数据库
        shardingRuleConfig.setDefaultDataSourceName(BaseDataConstants.DB_NAME_DEFAULT);
        // 设置默认的分库策略
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
                new StandardShardingStrategyConfiguration(BaseDataConstants.SHARDING_COLUMN_DEFAULT, new GibmsDBPreciseShardingAlgorithm()));

        Properties props = new Properties();
        props.put("sql.show ", true);

        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, props);
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception
    {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(getDataSource());
        return txManager;
    }

    /**
     * 获取分表规则配置
     */
    private TableRuleConfiguration getTableRuleCon() {
        TableRuleConfiguration result = new TableRuleConfiguration(BaseDataConstants.SHARDING_TABLE_CALCULATE, BaseDataConstants.SHARDING_CALCULATE_NODES);
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration(BaseDataConstants.SHARDING_COLUMN_CALCULATE, new GibmsDBPreciseShardingAlgorithm()));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration(BaseDataConstants.SHARDING_COLUMN_CALCULATE, new GibmsTablePreciseShardingAlgorithm()));
        return result;
    }

}
