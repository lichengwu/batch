package cn.lichengwu.batch.service;

import cn.lichengwu.batch.util.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * test for {@link SubscriptionService}
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-25 PM9:41
 */
public class SubscriptionServiceTest extends BaseTest {

    @Resource
    SubscriptionService subscriptionService;

    @Test
    public void testNotifyMyHousingInfo(){

        subscriptionService.notifyMyHousingInfo();
    }

}
