package com.meng.learn.shardingsphere;

import com.cpic.gibms.mgt.constants.BaseDataConstants;
import com.cpic.gibms.mgt.constants.BaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 分表策略
 */
@Slf4j
public class GibmsTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    /**
     * 分表实现
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        Integer suffix = BaseEnum.DBSuffix.getTableSuffix(preciseShardingValue.getValue());
        if (suffix == null) {
            return null;
        }

        String suffixStr = BaseDataConstants.UNDERLINE + suffix;
        log.info("---------------------------------------------------:" + suffixStr + "------------------" + preciseShardingValue.getValue());
        for (String name : collection) {
            if (name.endsWith(suffixStr)) {
                return name;
            }
        }
        return null;
    }
}
