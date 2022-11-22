package cn.lzscxb.domain.model;

import lombok.Data;

import java.util.List;

/**
 * @Auther: Likfees
 * @Date: 2022/11/21 10:46
 * @Description:
 */
@Data
public class ProblemCreateBody {

    // id
    private long id;

    // 用户id 用于修改
    private long userId;

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
    private long sort;

    // 自动批阅
    private int isAuto;

    // 题目支持的语言列表
    private List<String> language;

    // 语言模版方法名列表
    private List<String> methodNames;

    // 代码模版
    private List<String> codeTemplates;

    // 语言参数类型
    private List<List<String>> paramTypes;

    // 测试用例
    private String testCase;


}
