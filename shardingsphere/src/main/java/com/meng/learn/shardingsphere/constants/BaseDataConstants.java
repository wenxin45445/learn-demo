package com.meng.learn.shardingsphere.constants;

public final class BaseDataConstants {

    /** -----------------------------  分库分表 --------------------------- */
    public final static String DB_NAME_DEFAULT = "sharding-db";
    public final static String DB_NAME_SECOND = "sharding-db2";
    public static final String SHARDING_TABLE_CALCULATE = "t_yg_sharding_test";
    public static final String SHARDING_TABLE_CALCULATE_UNDERLINE = "t_yg_sharding_test_";
    public static final String SHARDING_COLUMN_DEFAULT = "org_code";
    public static final String SHARDING_COLUMN_CALCULATE = "org_code";
    public static final String SHARDING_CALCULATE_NODES = "sharding-db2.t_yg_sharding_test_${1..10},sharding-db.t_yg_calculate_${11..20}";

}
