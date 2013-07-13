package cn.lichengwu.batch.util;

import java.util.regex.Pattern;

/**
 * StringUtil
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-31 2:17 PM
 */
public final class StringUtil {
    public static final Pattern PATTERN_IP = Pattern
            .compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");

    /**
     * test if the string is a current ip address
     * 
     * @param ip
     * @return whether the string is a current ip address
     */
    public static boolean isIp(String ip) {
        if (ip == null) {
            return false;
        }
        return PATTERN_IP.matcher(ip).matches();
    }
}
