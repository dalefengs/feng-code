package cn.lzscxb.domain.model;

import lombok.Data;

/**
 * @Auther: Likfees
 * @Date: 2022/12/20 19:16
 * @Description: 执行结果
 */
@Data
public class ExecuteResult {

    private int allCount;

    private int successCount;

    private boolean status; // 成功状态

    private int excuteTime; // 执行时间 ms

    private float memory; // 执行时间 kb

    private String errorMsg; // 错误信息

    private String msg; // 错误信息

    private ErrorTestCase errorTestCase;


    @Data
    static class ErrorTestCase {
        private String testCase; // 执行错误的测试用例

        private String executeResult; // 执行结果

        private String correctResult; // 正确的结果
    }

}
