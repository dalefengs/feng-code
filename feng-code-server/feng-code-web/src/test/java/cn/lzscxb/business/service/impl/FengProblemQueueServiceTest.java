package cn.lzscxb.business.service.impl;

import cn.lzscxb.business.mapper.FengProblemMapper;
import cn.lzscxb.common.core.redis.RedisCache;
import cn.lzscxb.domain.constant.CacheConstants;
import cn.lzscxb.domain.model.ExecuteResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: Likfees
 * @Date: 2022/12/9 15:50
 * @Description:
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class FengProblemQueueServiceTest {

    @Autowired
    private FengProblemQueueServiceImpl fengProblemQueueService;

    @Autowired
    private RedisCache redisCache;

    @Test
    public void testExcuteQueue(){

        ExecuteResult executeResult = fengProblemQueueService.excuteQueueSubmit(1, 101, 1, "java");
        System.out.println(executeResult);

    }


    @Test
    public void test() {
        boolean b = redisCache.cacheListRpush(CacheConstants.QUQUE_EXECUTE, 123);
        boolean b1 = redisCache.cacheListRpush(CacheConstants.QUQUE_EXECUTE, 124);
        boolean b2 = redisCache.cacheListRpush(CacheConstants.QUQUE_EXECUTE, 125);
//        Integer tttt = redisCache.cacheListLPop(CacheConstants.QUQUE_EXECUTE);
//        System.out.println(tttt);
    }
}
