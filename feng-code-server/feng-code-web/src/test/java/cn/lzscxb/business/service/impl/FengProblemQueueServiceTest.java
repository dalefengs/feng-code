package cn.lzscxb.business.service.impl;

import cn.lzscxb.business.mapper.FengProblemMapper;
import cn.lzscxb.domain.entity.FengProblem;
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
    private FengProblemMapper fengProblemMapper;


    @Test
    public void testExcuteQueue(){

        ExecuteResult executeResult = fengProblemQueueService.excuteQueueJava(1, 101, 1);
        System.out.println(executeResult);

    }


    @Test
    public void test() {
        FengProblem fengProblem = fengProblemMapper.selectFengProblemById(1L);
        String s = fengProblem.parseTestCase();
    }
}
