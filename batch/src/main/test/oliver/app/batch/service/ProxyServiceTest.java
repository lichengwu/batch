package oliver.app.batch.service;

import oliver.app.batch.util.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * test for proxy
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-31 1:25 PM
 */
public class ProxyServiceTest extends BaseTest {

    @Autowired
    ProxyService proxyService;

    @Test
    public void testFetch() {
        proxyService.fetchProxy();

    }

    @Test
    public void testCheck(){
        proxyService.testProxies();
    }
}
