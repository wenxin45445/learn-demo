package com.meng.learn.shardingsphere;


import com.meng.learn.shardingsphere.constants.BaseDataConstants;
import com.meng.learn.shardingsphere.constants.BaseEnum;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 分表策略
 */
public class CalculateTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    /**
     * 分表实现
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        Integer suffix = BaseEnum.DBSuffix.getTableSuffix(preciseShardingValue.getValue());
        if (suffix == null) {
            return null;
        }
        String suffixStr = BaseDataConstants.SHARDING_TABLE_CALCULATE_UNDERLINE + suffix;
        if (collection.contains(suffixStr)) {
            return suffixStr;
        }
        return null;
    }
}
