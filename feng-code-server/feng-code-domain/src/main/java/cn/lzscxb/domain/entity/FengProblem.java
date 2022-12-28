package cn.lzscxb.domain.entity;

import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 题目管理对象 feng_problem
 *
 * @author Likfees
 * @date 2022-11-21
 */
@Data
@ToString
@Slf4j
public class FengProblem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 题目标题 */
    @Excel(name = "题目标题")
    private String title;

    /** 作者id */
    private Long userId;

    /** 当前的用户id */
    private Long currentUserId;

    /** 学习计划id */
    private Long taskId;

    /** 当前用户 当前题目状态	*/
    private Integer ownness;

    /** 作者 */
    private String nickName;

    /** 题目等级(0简单 1中等 2困难) */
    @Excel(name = "题目等级(0简单 1中等 2困难)")
    private Integer level;

    /** 点赞 */
    @Excel(name = "点赞")
    private Long likeCount;

    /** 题目说明 */
    private String description;

    /** 分类id */
    private Long categoryId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

    /** 标签id */
    private Long tagId;

    /** 标签id 一个问题属于一个标签 */
    @Excel(name = "标签")
    private String tagName;

    /** 提示 */
    @Excel(name = "提示")
    private String hint;

    /** 提交次数 */
    @Excel(name = "提交次数")
    private Long submitCount;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 0自动批阅(执行测试用例) 1教师批阅 */
    @Excel(name = "0自动批阅(执行测试用例) 1教师批阅")
    private Integer isAuto;

    /** 通过次数 */
    @Excel(name = "通过次数")
    private Long successCount;

    /** 支持的语言类型 */
    private String language;

    /** 支持的语言类型 */
    @Excel(name = "支持的语言类型")
    private List<String> languageDicts;

    /** 方法名 */
    @Excel(name = "方法名")
    private String methodNames;

    /** 参数类型名称 */
    @Excel(name = "参数类型名称")
    private String paramTypes;

    /** 代码模版 */
    private String codeTemplates;

    /** 代码模版 */
    private List<String> codeTemplatesParse;

    /** 测试用例 */
    @Excel(name = "测试用例")
    private String testCase;

    /**
     * 解析方法名JSON
     */
    public List<String> parseMethodName() {
        return JSON.parseArray(this.getMethodNames(), String.class);
    }

    /**
     * 解析方法名JSON 并返回指定方法名
     */
    public String parseMethodName(int languageType) {
        return parseMethodName().get(languageType);
    }


    public List<String[]> parseParamType() {
        return JSON.parseArray(this.getParamTypes(), String[].class);
    }
    public String[] parseParamType(int languageType) {
        return parseParamType().get(languageType);
    }

    public String parseTestCase() {
        return JSON.parseObject(getTestCase(), String.class).trim();
    }

}
