package cn.lichengwu.batch.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lichengwu.batch.util.ThreadCache;
import cn.lichengwu.batch.web.util.IpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * access log interceptor
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-26 PM5:21
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        ThreadCache.setUri(request.getRequestURI());
        ThreadCache.setStartTime(System.currentTimeMillis());
        ThreadCache.setIp(IpUtil.getRemoteIp(request));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception {

        log.info(
                "IP={},URI={},Times={}ms",
                new Object[] { ThreadCache.getIp(), ThreadCache.getUri(),
                        (System.currentTimeMillis() - ThreadCache.getStartTime()) });
    }
}
