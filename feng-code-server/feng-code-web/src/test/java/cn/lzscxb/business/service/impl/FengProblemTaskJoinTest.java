package cn.lzscxb.business.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: Likfees
 * @Date: 2023/1/1 22:56
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FengProblemTaskJoinTest {

    @Autowired
    private FengTaskJoinServiceImpl fengTaskJoinService;

    @Test
    public void testOverdueMyTask () {
        fengTaskJoinService.overdueMyTask();
    }


}
