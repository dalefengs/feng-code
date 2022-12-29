package cn.lzscxb.business.service.impl;

import cn.lzscxb.domain.entity.FengProblemEveryday;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: Likfees
 * @Date: 2022/12/29 15:12
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FengProblemEverydayTest {

    @Autowired
    private FengProblemEverydayServiceImpl fengProblemEverydayService;

    @Test
    public void testEverydayAddProblem() {
        fengProblemEverydayService.everydayAddProblem();
    }

}
