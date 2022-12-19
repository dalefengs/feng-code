package cn.lzscxb.business.service.impl;

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


    @Test
    public void testExcuteQueue(){

        fengProblemQueueService.excuteJavaQueue(1, 100);
    }

}
