package cn.lzscxb.domain.model;

import java.util.List;

/**
 * @Auther: Likfees
 * @Date: 2022/11/21 10:46
 * @Description:
 */
public class ProblemCreateBody {

    // 题目标题
    private String title;

    // 题目说明
    private String description;

    // 题目提示
    private String hint;

    // 分类id
    private long categoryId;

    // 标签id
    private long tagId;

    // 难度等级
    private int level;

    // 排序
    private int sort;

    // 自动批阅
    private int isAuto;

    // 题目支持的语言列表
    private List<Integer> language;

    // 语言模版方法名列表
    private List<String> methodNames;


    // 测试用例
    private String testCase;

}
