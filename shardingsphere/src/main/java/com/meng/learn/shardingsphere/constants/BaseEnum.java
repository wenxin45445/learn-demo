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
        DBSuffix20("00000000000021", 20),
        DBSuffix21("00000000000022", 21),
        DBSuffix22("00000000000023", 22),
        DBSuffix23("00000000000024", 23),
        DBSuffix24("00000000000025", 24),
        DBSuffix25("00000000000026", 25),
        DBSuffix26("00000000000027", 26),
        DBSuffix27("00000000000028", 27),
        DBSuffix28("00000000000029", 28),
        DBSuffix29("00000000000030", 29),
        DBSuffix30("00000000000031", 30),
        DBSuffix31("00000000000032", 31),
        DBSuffix32("00000000002324", 32),
        DBSuffix33("00000000002325", 33),
        DBSuffix34("00000000002326", 34),
        DBSuffix35("00000000004474", 35),
        DBSuffix36("00000000004511", 36),
        DBSuffix37("00000000004515", 37),
        DBSuffix38("00000000004519", 38),
        DBSuffix39("00000000004523", 39),
        DBSuffix40("00000000004902", 40),
        DBSuffix41("00000099999999", 41),
        DBSuffix42("12045138254372", 42),
        DBSuffix43("12045192433901", 43),
        DBSuffix44("12977508116706", 44),
        DBSuffix45("80000000000000", 45),
        DBSuffix46("80000000000001", 46),
        DBSuffix47("80000000000009", 47),
        DBSuffix48("80000000000022", 48),
        DBSuffix49("80000000000027", 49),
        DBSuffix50("01000000000001", 50),
        DBSuffix51("01000000000003", 51);

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
