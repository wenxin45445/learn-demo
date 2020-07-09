package com.meng.learn.shardingsphere;

import com.meng.learn.shardingsphere.constants.BaseDataConstants;
import com.meng.learn.shardingsphere.constants.BaseEnum;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 分库策略
 */
public class DBPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    /**
     * 分库实现
     */
    @Override
    public String doSharding(Collection<String> dbNames, PreciseShardingValue<String> preciseShardingValue) {
        Integer suffix = BaseEnum.DBSuffix.getTableSuffix(preciseShardingValue.getValue());
        if (suffix != null) {
            String suffixStr = BaseDataConstants.DB_NAME_DEFAULT;
            if (suffix > 0 && suffix < 11) {
                suffixStr += "2";
            } else if (suffix > 10 && suffix < 21) {
                suffixStr += "3";
            } else if (suffix > 20 && suffix < 31) {
                suffixStr += "4";
            }
            if (dbNames.contains(suffixStr)) {
                return suffixStr;
            } else {
                return BaseDataConstants.DB_NAME_DEFAULT;
            }
        }
        throw new IllegalArgumentException();
    }
}
