package com.meng.learn.shardingsphere.constants;

/**
 * 枚举值基类 把所有枚举值定义在这里
 */
public interface BaseEnum {

    /**
     * 分库分表
     */
    enum DBSuffix{
        DBSuffix1("00000000000001", 1),
        DBSuffix2("00000000000002", 2),
        DBSuffix3("00000000000003", 3),
        DBSuffix4("00000000000004", 4),
        DBSuffix5("00000000000005", 5),
        DBSuffix6("00000000000006", 6),
        DBSuffix7("00000000000007", 7),
        DBSuffix8("00000000000008", 8),
        DBSuffix9("00000000000009", 9),
        DBSuffix10("00000000000010", 10),
        DBSuffix11("00000000000011", 11),
        DBSuffix12("00000000000012", 12),
        DBSuffix13("00000000000013", 13),
        DBSuffix14("00000000000014", 14),
        DBSuffix15("00000000000015", 15),
        DBSuffix16("00000000000016", 16),
        DBSuffix17("00000000000017", 17),
        DBSuffix18("00000000000018", 18),
        DBSuffix19("00000000000020", 19),
        DBSuffix20("00000000000021", 20);

        private String orgCode;

        private Integer suffix;

        DBSuffix(String orgCode, Integer suffix)
        {
            this.orgCode = orgCode;
            this.suffix = suffix;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public Integer getSuffix() {
            return suffix;
        }

        public static Integer getTableSuffix(String orgCode)
        {
            for (DBSuffix dbSuffix : DBSuffix.values())
            {
                if (dbSuffix.orgCode.equals(orgCode))
                {
                    return dbSuffix.suffix;
                }
            }
            return null;
        }

        public static String getOrgCodeBySuffix(int suffix) {
            for (DBSuffix dbSuffix : DBSuffix.values()) {
                if (dbSuffix.suffix == suffix) {
                    return dbSuffix.orgCode;
                }
            }
            return null;
        }
    }
}
