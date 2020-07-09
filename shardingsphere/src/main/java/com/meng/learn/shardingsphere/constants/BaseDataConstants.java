package com.meng.learn.shardingsphere.constants;

public final class BaseDataConstants {

    /** -----------------------------  分库分表 --------------------------- */
    public final static String DB_NAME_DEFAULT = "db";
    public final static String DB_NAME_SECOND = "db2";
    public final static String DB_NAME_THREE = "db3";
    public final static String DB_NAME_FOUR = "db4";
    public static final String SHARDING_TABLE_CALCULATE = "t_yg_calculate";
    public static final String SHARDING_TABLE_CALCULATE_UNDERLINE = "t_yg_calculate_";
    public static final String SHARDING_COLUMN_DEFAULT = "org_code";
    public static final String SHARDING_COLUMN_CALCULATE = "org_code";
    public static final String SHARDING_CALCULATE_NODES = "db2.t_yg_calculate_${1..10},db3.t_yg_calculate_${11..20},db4.t_yg_calculate_${21..30},db.t_yg_calculate_${31..51}";

}
