package yutang.yys.util;

public class Util {

    /**
     * @param s 秒数
     * @return 小时 分 秒
     */
    public static long[] parseSeconds(long s) {
        return new long[]{s / 3600, (s % 3600) / 60, s % 60};
    }
}
