package cn.lzscxb.business.service.impl;

import java.util.List;
import cn.lzscxb.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengProblemEverydayMapper;
import cn.lzscxb.domain.entity.FengProblemEveryday;
import cn.lzscxb.business.service.IFengProblemEverydayService;

/**
 * 每日一题Service业务层处理
 * 
 * @author Likfees
 * @date 2022-12-29
 */
@Service
public class FengProblemEverydayServiceImpl implements IFengProblemEverydayService 
{
    @Autowired
    private FengProblemEverydayMapper fengProblemEverydayMapper;

    /**
     * 查询每日一题
     * 
     * @param id 每日一题主键
     * @return 每日一题
     */
    @Override
    public FengProblemEveryday selectFengProblemEverydayById(Long id)
    {
        return fengProblemEverydayMapper.selectFengProblemEverydayById(id);
    }

    /**
     * 查询每日一题列表
     * 
     * @param fengProblemEveryday 每日一题
     * @return 每日一题
     */
    @Override
    public List<FengProblemEveryday> selectFengProblemEverydayList(FengProblemEveryday fengProblemEveryday)
    {
        return fengProblemEverydayMapper.selectFengProblemEverydayList(fengProblemEveryday);
    }

    /**
     * 新增每日一题
     * 
     * @param fengProblemEveryday 每日一题
     * @return 结果
     */
    @Override
    public int insertFengProblemEveryday(FengProblemEveryday fengProblemEveryday)
    {
        fengProblemEveryday.setCreateTime(DateUtils.getNowDate());
        return fengProblemEverydayMapper.insertFengProblemEveryday(fengProblemEveryday);
    }

    /**
     * 修改每日一题
     * 
     * @param fengProblemEveryday 每日一题
     * @return 结果
     */
    @Override
    public int updateFengProblemEveryday(FengProblemEveryday fengProblemEveryday)
    {
        fengProblemEveryday.setUpdateTime(DateUtils.getNowDate());
        return fengProblemEverydayMapper.updateFengProblemEveryday(fengProblemEveryday);
    }

    /**
     * 批量删除每日一题
     * 
     * @param ids 需要删除的每日一题主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemEverydayByIds(Long[] ids)
    {
        return fengProblemEverydayMapper.deleteFengProblemEverydayByIds(ids);
    }

    /**
     * 删除每日一题信息
     * 
     * @param id 每日一题主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemEverydayById(Long id)
    {
        return fengProblemEverydayMapper.deleteFengProblemEverydayById(id);
    }
}
