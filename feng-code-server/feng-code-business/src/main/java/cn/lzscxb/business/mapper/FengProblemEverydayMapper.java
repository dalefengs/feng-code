package cn.lzscxb.business.mapper;

import java.util.List;
import cn.lzscxb.domain.entity.FengProblemEveryday;

/**
 * 每日一题Mapper接口
 * 
 * @author Likfees
 * @date 2022-12-29
 */
public interface FengProblemEverydayMapper 
{
    /**
     * 查询每日一题
     * 
     * @param id 每日一题主键
     * @return 每日一题
     */
    public FengProblemEveryday selectFengProblemEverydayById(Long id);

    /**
     * 查询每日一题列表
     * 
     * @param fengProblemEveryday 每日一题
     * @return 每日一题集合
     */
    public List<FengProblemEveryday> selectFengProblemEverydayList(FengProblemEveryday fengProblemEveryday);

    /**
     * 新增每日一题
     * 
     * @param fengProblemEveryday 每日一题
     * @return 结果
     */
    public int insertFengProblemEveryday(FengProblemEveryday fengProblemEveryday);

    /**
     * 修改每日一题
     * 
     * @param fengProblemEveryday 每日一题
     * @return 结果
     */
    public int updateFengProblemEveryday(FengProblemEveryday fengProblemEveryday);

    /**
     * 删除每日一题
     * 
     * @param id 每日一题主键
     * @return 结果
     */
    public int deleteFengProblemEverydayById(Long id);

    /**
     * 批量删除每日一题
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFengProblemEverydayByIds(Long[] ids);

    FengProblemEveryday selectFengProblemEverydayByDay(String date);

    List<FengProblemEveryday> selectMonthList(FengProblemEveryday fengProblemEveryday);
}
