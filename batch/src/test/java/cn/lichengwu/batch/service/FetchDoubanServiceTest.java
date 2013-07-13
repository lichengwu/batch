package cn.lichengwu.batch.service;

import javax.annotation.Resource;

import cn.lichengwu.batch.util.BaseTest;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * test for {@link FetchDoubanService}
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-03 6:35 PM
 */
public class FetchDoubanServiceTest extends BaseTest {

    @Resource
    FetchDoubanService fetchDoubanService;

    @Test
    public void test() throws InterruptedException {
        fetchDoubanService.fetchContent("http://movie.douban.com/");

        TimeUnit.SECONDS.sleep(1000);
    }
}
