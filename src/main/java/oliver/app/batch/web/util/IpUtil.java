package oliver.app.batch.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * ip util
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-26 PM7:07
 */
public final class IpUtil {

    /**
     * get remote request ip
     * 
     * @author lichengwu
     * @created 2012-12-26
     * 
     * @param request
     * @return remote request ip or nulls
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
